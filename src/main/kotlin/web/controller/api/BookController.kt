package web.controller.api


import book.app.App
import book.model.Book
import book.model.BookSource
import book.model.SearchBook
import book.util.*
import book.util.help.CacheManager
import book.util.help.CookieStore
import book.webBook.WBook
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.*
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.Cache
import org.noear.solon.data.annotation.CacheRemove
import org.noear.solon.data.annotation.Tran
import org.noear.solon.data.cache.CacheService
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.controller.api.ReadController.Companion.getBookbycache
import web.mapper.BookmarkMapper
import web.mapper.ItemMapper
import web.model.BookCache
import web.model.BookGroup
import web.model.Booklist
import web.model.Bookmark
import web.model.Item
import web.model.Users
import web.response.*
import web.service.BookCacheService
import web.service.BookGroupService
import web.service.BooklistService
import web.util.ResponseManager
import web.util.hash.EncryptUtils
import web.util.hash.Md5
import web.util.read.BookCatalog
import web.util.read.BookInfo
import web.util.read.BookType
import web.util.read.Bookcache
import web.util.read.getlist
import web.util.read.updatebook
import java.io.File
import java.net.SocketException
import java.net.SocketTimeoutException
import kotlin.concurrent.thread

@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class BookController:BaseController() {

    @Inject
    lateinit var booklistService: BooklistService

    @Inject
    lateinit var bookGroupService: BookGroupService

    @Inject
    lateinit var bookCacheService: BookCacheService

    @Inject
    private lateinit var cache: CacheService

    @Inject(value = "\${admin.cron:true}", autoRefreshed=true)
    var cron:Boolean=true

    @Inject(value = "\${user.timeout:0}", autoRefreshed=true)
    var timeout: Int= 0

    @Db("db")
    @Inject
    lateinit var itemMapper: ItemMapper

    @Db("db")
    @Inject
    lateinit var bookmarkMapper: BookmarkMapper

    @Mapping("/addbookmark")
    fun  addbookmark(accessToken:String?, url:String ,name:String, index: Int,pos : Double) = run{
        if (url.isBlank()) {
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val user=getuserbytocken(accessToken)
        val book = booklistService.getbook(user.id!!,url)?:throw DataThrowable().data(JsonResponse(false,NO_BOOK))
        val marks = bookmarkMapper.getbybook(user.id!!,book.bookUrl!!)
        for ( mark in marks) {
            if (mark.cindex == index && mark.cpos == pos){
                throw DataThrowable().data(JsonResponse(false,MARK_IS))
            }
        }
        val bookmark= Bookmark().create(user.id!!,book).apply {
            cindex=index
            cpos =pos
            cname =name
        }
        bookmarkMapper.insert(bookmark)
        JsonResponse(true)
    }

    @Mapping("/getbookmark")
    fun  getbookmark(accessToken:String?, url:String) = run{
        if (url.isBlank()) {
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val user=getuserbytocken(accessToken)
        val book = booklistService.getbook(user.id!!,url)?:throw DataThrowable().data(JsonResponse(true).Data(listOf<String>()))
        val marks = bookmarkMapper.getbybook(user.id!!,book.bookUrl!!)
        JsonResponse(true).Data(marks)
    }

    @Mapping("/delbookmark")
    fun  delbookmark(accessToken:String?, id:String) = run{
        if (id.isBlank()) {
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val user=getuserbytocken(accessToken)
        val mark = bookmarkMapper.selectById(id)
        if(mark == null || mark.userid != user.id) {
            throw DataThrowable().data(JsonResponse(false,NOT_IS))
        }
        bookmarkMapper.deleteById(id)
        JsonResponse(true)
    }

    @Mapping("/getitem")
    fun  getitem(accessToken:String?, name:String?) = run{
        if (name.isNullOrBlank()) {
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val user=getuserbytocken(accessToken)
        val item = itemMapper.getbyname(user.id!!,name)
        JsonResponse(true).Data(item?.value)
    }

    @Mapping("/setitem")
    fun  setitem(accessToken:String?, name:String?, value:String?) = run{
        if (name.isNullOrBlank() || value == null) {
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val user=getuserbytocken(accessToken)
        val item = Item().create(user.id!!,name)
        item.value = value
        itemMapper.insertOrUpdate(item)
        JsonResponse(true)
    }

    fun search(accessToken:String?, bookSourceUrl:String?, page:Int?, key:String?,type:Int)= runBlocking{
        val (user,source)=getsourceuser(accessToken,bookSourceUrl)
        if(!source.enabled)  throw DataThrowable().data(JsonResponse(false,"source error"))
        val webBook = WBook(source.json,user.id!!,accessToken, false)
        var re:List<SearchBook>  = arrayListOf()
        runCatching {
            when(type){
                1->{
                    logger.info ("searchBook")
                    re=webBook.searchBook(key?:"", page?:0)
                }
                2->{
                    logger.info ("exploreBook")
                    re=webBook.exploreBook(key?:"", page?:0)
                }
                else -> throw Exception("search type not supported")
            }
        }.onFailure {
            it.printStackTrace()
            App.log("搜索出错:"+it.message,accessToken!!)
            if (timeout > 0 && type ==1 &&( it is SocketTimeoutException  || it is SocketException || (it.message?.contains("timeout"))?:false )) {
                val md5=Md5(source.json)
                var num=  cache.getOrStore(md5, Int::class.java,600) {
                    0
                }
                if(num > timeout){
                    if(user.source == 2){
                        userBookSourceService.changeEnabled(source.bookSourceUrl,user.id!!,false)
                    }else{
                        bookSourceService.changeEnabled(source.bookSourceUrl,false);
                    }
                }else{
                    num++
                    cache.store(md5,num,600)
                }
            }
            throw DataThrowable().data(JsonResponse(false, it.message?:"搜索出错"))
        }
        if(re.isEmpty() &&( page == 1 || page == 0)){
            throw DataThrowable().data(JsonResponse(false,"search is empty"))
        }
        val s= BookSource.fromJson(source.json).getOrNull()
        s?.usertocken=accessToken
        s?.userid=user.id
        if(s != null && s.hasimageDecode() ){
            re.forEach{
                it.imageDecode=true
            }
        }
        Gson().toJson(JsonResponse(true).Data(re))
    }!!

    @Cache(key = "searchBook:\${accessToken},\${bookSourceUrl},\${page},\${key}", tags = "search\${accessToken}", seconds = 600)
    @Mapping("/searchBook")
    open fun searchBook(accessToken:String?, bookSourceUrl:String?, page:Int?, key:String? )= search(accessToken,bookSourceUrl,page, key,1)

    @Cache(key = "exploreBook:\${accessToken},\${bookSourceUrl},\${page},\${ruleFindUrl}", tags = "search\${accessToken}", seconds = 60)
    @Mapping("/exploreBook")
    open fun exploreBook( accessToken:String?,bookSourceUrl:String?, page:Int?, ruleFindUrl:String? ) = search(accessToken,bookSourceUrl,page, ruleFindUrl,2)


    @Mapping("/saveBookInfo")
    open fun saveBookInfo( accessToken:String?,book: SearchBook) = runBlocking{
        val user=getuserbytocken(accessToken)
        with(book){
            if (bookUrl.isBlank() || name.isBlank() ){
                throw DataThrowable().data(JsonResponse(false,NOT_BANK))
            }
        }
        val mybook=booklistService.getbook(user.id!!,book.bookUrl) ?: throw DataThrowable().data(JsonResponse(false,NO_BOOK))
        booklistService.upbookinfo(mybook.id?:"",user.id,book.name,book.author,book.coverUrl?:"",book.intro?:"")
        JsonResponse(true,SUCCESS)
    }


    @Mapping("/urlsaveBook")
    open fun urlsaveBook( accessToken:String?,url: String) = runBlocking{
        if(url.isBlank() || accessToken.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val user=getuserbytocken(accessToken)
        val domain=NetworkUtils.getSubDomain(url)
        val sources = getBookSourcelist(true,user)
        var z=false
        var msg=""
        sources.forEach {
            if(it.bookSourceUrl.contains(domain)){
                z=true
                runCatching{
                    val book= BookInfo.getbookinfo(accessToken,user,it,url)
                    return@runBlocking  JsonResponse(true).Data(book)
                }.onFailure {
                    msg=it.message?:""
                }
            }else {
                val s=BookSource.fromJson(it.json).getOrNull() ?: BookSource()
                if(!s.bookUrlPattern.isNullOrBlank()){
                    if(s.bookUrlPattern!!.toRegex().matches(url)){
                        z=true
                        runCatching{
                            val book= BookInfo.getbookinfo(accessToken,user,it,url)
                            return@runBlocking  JsonResponse(true).Data(book)
                        }.onFailure {
                            msg=it.message?:""
                        }
                    }
                }
            }
        }
        if (z){
            return@runBlocking  JsonResponse(false,"获取失败:$msg")
        }else{
            return@runBlocking  JsonResponse(false,"未查询到符合条件都书源")

        }
    }


    @Mapping("/saveBook")
    open fun saveBook( accessToken:String?,book: SearchBook,useReplaceRule: Int) = runBlocking{
        with(book){
            if (bookUrl.isBlank() || name.isBlank()){
                throw DataThrowable().data(JsonResponse(false,NOT_BANK))
            }
        }
        val (user,source)=getsourceuser(accessToken,book.origin)
        val booktolist=Booklist.tobooklist(book,user.id!!)
        var new: Book? = null
        runCatching {
            new = getBookbycache(book.bookUrl,user.id!!)?:(BookInfo.getbookinfo(accessToken!!,user,source,book.bookUrl))!!
        }.onFailure {
            return@runBlocking JsonResponse(false,BOOKSEARCHERROR)
        }
        if (booklistService.getbook(user.id!!,booktolist.bookUrl!!) != null){
            return@runBlocking JsonResponse(false,BOOKIS)
        }
        new!!.type=book.type

        booklistService.booklistMapper.insert(booktolist.bookto(new!!,false).apply {
            this.origin=source.bookSourceUrl
            this.originName=source.bookSourceName
            this.useReplaceRule=(useReplaceRule == 1)
            val s= BookSource.fromJson(source.json).getOrNull()
            this.needimageDecode(s)
        })
        booklistService.cleancache(user.id)
        thread {
            updatebook(booktolist, source,user)
        }
        JsonResponse(true,SUCCESS)
    }

    @Mapping("/saveBooks")
    open fun saveBooks( accessToken:String?, @Body content:String) = runBlocking{
        val user=getuserbytocken(accessToken)
        if(content.isEmpty()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val books= GSON.fromJsonArray<Book>(content).getOrNull()?:listOf()

        var num=0
        for (book in books){
            if(book.origin == "loc_book"){
                continue
            }
            if (book.bookUrl.isBlank() || book.name.isBlank()){
                continue
            }
            if (booklistService.getbook(user.id!!,book.bookUrl) != null){
                continue
            }
            val type = when(book.type){
                32,1 ->{
                    1
                }

                64,2 ->{
                    2
                }

                else ->{
                    0
                }
            }
            if (booklistService.getbooklistbynametype(user.id!!,book.name,book.author,type).isNotEmpty()){
                continue
            }
            val booktolist=Booklist.tobooklist(book,user.id!!)
            num+=booklistService.booklistMapper.insert(booktolist.bookto(book, canchangeindex = true))
            booklistService.cleancache(user.id)
        }
        JsonResponse(true, errorMsg = "共添加${num}本书")
    }

    @Mapping("/refreshBook")
    open fun refreshBook( accessToken:String?,bookurl: String?) = runBlocking{
        val user=getuserbytocken(accessToken)
        if (bookurl.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val book=booklistService.getbook(user.id!!,bookurl)?:throw DataThrowable().data(JsonResponse(false,NO_BOOK))
        if(book.origin == "loc_book" ){
           throw DataThrowable().data(JsonResponse(true,SUCCESS).Data(book))
        }
        val source = getsource(book.origin?:"",user)?: throw DataThrowable().data(JsonResponse(false,NOT_SOURCE))
        var new: Book? = null
        runCatching {
            new = BookInfo.getbookinfo(accessToken!!,user,source,book.bookUrl!!)!!
        }.onFailure {
            throw DataThrowable().data(JsonResponse(false,BOOKSEARCHERROR))
        }
        runCatching {
            val chapters=getlist(book.bookUrl!!, source, user, accessToken ?: "")
            if(book.totalChapterNum != chapters.size){
                book.totalChapterNum = chapters.size
                book.latestChapterTitle=chapters[chapters.size-1].title
                book.latestChapterTime=System.currentTimeMillis()
                book.lastCheckCount=chapters.size
            }
            book.lastCheckTime=System.currentTimeMillis()
            book.bookto(new!!,false)
            val s= BookSource.fromJson(source.json).getOrNull()
            s?.usertocken=accessToken
            s?.userid=user.id
            book.needimageDecode(s)
            booklistService.booklistMapper.updateById(book)
            booklistService.cleancache(user.id)
            JsonResponse(true,SUCCESS).Data(book)
        }.onFailure {
           JsonResponse(false,it.message?:BOOKSEARCHERROR)
        }
    }

    @Cache(key = "getBookinfo:\${accessToken},\${book.bookUrl},\${book.name},\${book.author}", tags = "search\${accessToken}", seconds = 30)
    @Mapping("/getBookinfo")
    open fun getBookinfo( accessToken:String?, book: SearchBook?) = runBlocking{
        val user=getuserbytocken(accessToken)
        if (book == null || book.bookUrl.isBlank() || book.origin.isBlank()){
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val source = getsource(book.origin,user)?: throw DataThrowable().data(JsonResponse(false,NOT_SOURCE))
        runCatching {
            val   new = BookInfo.getbookinfo(accessToken!!,user,source,book.bookUrl)!!
            val mybook=Booklist.tobooklist(book,user.id!!)
            mybook.bookto(new,false)
            val s= BookSource.fromJson(source.json).getOrNull()
            s?.usertocken=accessToken
            s?.userid=user.id
            mybook.needimageDecode(s)

            thread {
                BookType.UpdateBookInfo(accessToken,user,source,mybook)
            }

            JsonResponse(true,SUCCESS).Data( mybook)
        }.onFailure {
            it.printStackTrace()
           JsonResponse(false,BOOKSEARCHERROR)
        }
    }

    @Mapping("/getBookinfo2")
    open fun getBookinfo2( accessToken:String?, url: String?) = runBlocking{
        if(url.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val user=getuserbytocken(accessToken)
        JsonResponse(true,SUCCESS).Data( BookType.getBookInfo(url,user))
    }




    @Mapping("/deleteBook")
    open fun deleteBook( accessToken:String?, book: SearchBook)= runBlocking{
        val user=getuserbytocken(accessToken)
        with(book){
            if (bookUrl.isBlank()){
                throw DataThrowable().data(JsonResponse(false,NOT_BANK))
            }
        }
        val booktolist=booklistService.getbook(user.id!!,book.bookUrl).also {
            if(it == null){
                throw DataThrowable().data(JsonResponse(false,NOT_BANK))
            }
        }!!
        if (booktolist.origin == "loc_book"){
            val file= File(booktolist.bookUrl!!)
            if (file.exists()){
                file.delete()
            }
        }
        booklistService.booklistMapper.deleteById(booktolist.id!!)
        booklistService.cleancache(user.id)
        bookCacheService.getCache(user.id!!,booktolist.id!!).also {
            if(it != null){
                bookCacheService.bookCacheMapper.deleteById(it.id)
                bookCacheService.cleancache(user.id)
            }
        }
       JsonResponse(true,SUCCESS)
    }

    @Mapping("/updateuseReplaceRule")
    fun  updateuseReplaceRule( accessToken:String?, url: String ,useReplaceRule:Int?){
        val user=getuserbytocken(accessToken)
        val book=booklistService.getbook(user.id!!,url).also {
            if(it == null){
                throw DataThrowable().data(JsonResponse(true))
            }
        }!!
        if (useReplaceRule == 1){
            booklistService.uprule(book.id!!,user.id,true)
        }else{
            booklistService.uprule(book.id!!,user.id,false)
        }
        JsonResponse(true,SUCCESS)
    }

    @Mapping("/getcancache")
    open fun getcancache( accessToken:String?,  url: String)=run{
        val (user,booktolist)=getbook(accessToken,url)
        val cache=bookCacheService.getCache(user.id!!,booktolist.id!!)
        if(cache != null){
            throw DataThrowable().data(JsonResponse(false))
        }
        JsonResponse(true)
    }

    @Mapping("/getcancachelist")
    open fun getcancachelist( accessToken:String?)=run{
        val user=getuserbytocken(accessToken)
        JsonResponse(true).Data(bookCacheService.getlistbyuserid(user.id!!))
    }

    @Mapping("/addCache")
    open fun addCache( accessToken:String?, url: String)=run{
        val (user,booktolist)=getbook(accessToken,url)
        bookCacheService.getlistbyuserid(user.id!!).also { if(it.size >=  5) throw DataThrowable().data(JsonResponse(false,CACHE_ERROR))}
        if (booktolist.origin == "loc_book"){
            val cache= BookCache().create(user.id!!,booktolist)
            cache.num=booktolist.totalChapterNum
            val list:MutableSet<String> = mutableSetOf()
            for(i in 0..<(booktolist.totalChapterNum?:1)){
                list.add(i.toString())
            }
            cache.cacheindex= list.joinToString(",")
            bookCacheService.bookCacheMapper.insert(cache)
            bookCacheService.cleancache(user.id!!)
            JsonResponse(true)
        }else{
            val source = getsource(booktolist.origin?:"",user)
            if(source == null){
                throw DataThrowable().data(JsonResponse(false,NOT_SOURCE))
            }
            if((source.json).lowercase().contains("webview".lowercase())){
                throw DataThrowable().data(JsonResponse(false,IS_WEBVIEW))
            }
            val cache=bookCacheService.getCache(user.id!!,booktolist.id!!).let {
                if(it == null){
                    BookCache().create(user.id!!,booktolist)
                }else{
                    throw DataThrowable().data(JsonResponse(false,CacheIS))
                }
            }
            bookCacheService.bookCacheMapper.insert(cache)
            bookCacheService.cleancache(user.id)
            if(cron)  Bookcache.addcache(cache)
            JsonResponse(true,SUCCESS)
        }
    }

    @Mapping("/delCache")
    open fun delCache( accessToken:String?, id: String)=run{
        val user=getuserbytocken(accessToken)
        val cache=bookCacheService.bookCacheMapper.selectById(id)
        if (cache == null || cache.userid != user.id){
            throw DataThrowable().data(JsonResponse(false,NOT_IS))
        }
        bookCacheService.bookCacheMapper.deleteById(cache.id!!)
        bookCacheService.cleancache(user.id)
        JsonResponse(true,SUCCESS)
    }

    @CacheRemove(tags = "search\${accessToken}")
    @Mapping("/saveCookies")
    open fun saveCookies( accessToken:String?, url: String,cookie:String, html: String?, id: String?)=run{
        val user=getuserbytocken(accessToken)
        if (url.isBlank()){
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        runCatching {
            var cookie1 = cookie
            if(cookie1.isNotEmpty()){
                cookie1= EncryptUtils.aesDecrypted(cookie)
                logger.info("cookie:解密后长度${cookie.length}")
            }
            CookieStore(user.id!!).setCookie(url,cookie1)
        }.onFailure {
            it.printStackTrace()
        }
        if(!id.isNullOrBlank()){
            runBlocking {
                logger.info("webview:$id,加载完成")
                ResponseManager.completeRequest(id,html?:"")
            }
        }
        JsonResponse(true)
    }

    @Mapping("/getCookies")
    open fun getCookies( accessToken:String?, url: String?)=run{
        val user=getuserbytocken(accessToken)
        if (url.isNullOrEmpty()){
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val cookie=CookieStore(user.id!!).getCookie(url)
        logger.info("cookie加密后:$cookie")
        JsonResponse(true).Data(EncryptUtils.aesEncode(cookie))
    }

    //@CacheRemove(tags = "search\${accessToken}")
    @Mapping("/savehtml")
    open fun savehtml( accessToken:String?, html: String?, id: String?)=run{
        if(!id.isNullOrBlank()){
            runBlocking {
                logger.info("webview:$id,加载完成,htm:$html")
                ResponseManager.completeRequest(id,html?:"")
            }
        }
        JsonResponse(true)
    }


    @CacheRemove(tags = "search\${accessToken}")
    @Mapping("/cleancookies")
    open fun cleancookies( accessToken:String?)=run{
        val user=getuserbytocken(accessToken)
        CookieStore(user.id!!).clear()
        JsonResponse(true)
    }

    @CacheRemove(tags = "search\${accessToken}")
    @Mapping("/cleancaches")
    open fun cleancaches( accessToken:String?)=run{
        val user=getuserbytocken(accessToken)
        CacheManager(user.id!!).clear()
        JsonResponse(true)
    }


    @Mapping("/noCookies")
    open fun noCookies( accessToken:String?,id:String?)=run{
       if(id.isNullOrBlank()) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        runBlocking {
            ResponseManager.completeRequest(id,"")
        }
        JsonResponse(true)
    }

    @Mapping("/getgroup")
    open fun getgroup( accessToken:String?)=run{
        val user=getuserbytocken(accessToken)
        JsonResponse(true).Data(bookGroupService.getGroupbyuserid(user.id!!))
    }

    @Tran
    @Mapping("/addgroup")
    open fun addgroup( accessToken:String?,name:String?)=run{
        val user=getuserbytocken(accessToken)
        if(name == null){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        if(name == "全部"  || bookGroupService.getGroupbyName(user.id!!,name) != null) {
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = GROUPIS))
        }
        if(name == "未分组"){
            bookGroupService.bookGroupMapper.insert(BookGroup().create(user.id!!,""))
        }else{
            bookGroupService.bookGroupMapper.insert(BookGroup().create(user.id!!,name))
        }
        bookGroupService.cleancache(user.id)
        JsonResponse(true)
    }

    @Tran
    @Mapping("/delgroup")
    open fun delgroup( accessToken:String?,name:String?)=run{
        val user=getuserbytocken(accessToken)
        if(name == null){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val group=bookGroupService.getGroupbyName(user.id!!,name).also {
            if(it == null){
                throw DataThrowable().data(JsonResponse(false, NOT_IS))
            }
        }!!
        bookGroupService.bookGroupMapper.deleteById(group.id!!)
        bookGroupService.cleancache(user.id)
        booklistService.delbookgroup(user.id!!,user.id,group.bookgroup!!)
        JsonResponse(true)
    }

    @Tran
    @Mapping("/editgroup")
    open fun editgroup( accessToken:String?,oldname:String?,newname:String?)=run{
        val user=getuserbytocken(accessToken)
        if(oldname == null || newname == null){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val group=bookGroupService.getGroupbyName(user.id!!,oldname).also {
            if(it == null){
                throw DataThrowable().data(JsonResponse(false, NOT_IS))
            }
        }!!
        group.bookgroup=newname
        bookGroupService.bookGroupMapper.updateById(group)
        bookGroupService.cleancache(user.id)
        booklistService.upbookgroup(user.id!!,oldname,newname)
        JsonResponse(true)
    }

    @Tran
    @Mapping("/setgroup")
    open fun setgroup( accessToken:String?,name:String?, url: String?)=run{
        val user=getuserbytocken(accessToken)
        if(url == null){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        if(name != null && name != "全部"){
            bookGroupService.getGroupbyName(user.id!!,name).also {
                if(it == null){
                    throw DataThrowable().data(JsonResponse(false, NOT_IS))
                }
            }!!
        }
        val book=booklistService.getbook(user.id!!,url).also {
            if(it == null){
                throw DataThrowable().data(JsonResponse(false, NOT_IS))
            }
        }!!
        booklistService.changebookgroup(book.id!!,user.id,name?:"")
        JsonResponse(true)
    }

    private fun  getbook(accessToken:String?, url: String):Pair<Users,Booklist>{
        val user=getuserbytocken(accessToken)
        if(user.AllowCache != true){
            throw DataThrowable().data(JsonResponse(false,CAN_NOT))
        }
        val booktolist=booklistService.getbook(user.id!!,url)?: throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        return Pair(user,booktolist)
    }

}