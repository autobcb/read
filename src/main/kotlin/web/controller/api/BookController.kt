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
import org.noear.solon.annotation.*
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.Cache
import org.noear.solon.data.annotation.CacheRemove
import org.noear.solon.data.cache.CacheService
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.controller.api.ReadController.Companion.getBookbycache
import web.mapper.BookCacheMapper
import web.mapper.BooklistMapper
import web.model.BookCache
import web.model.Booklist
import web.model.Users
import web.notification.Source
import web.response.*
import web.util.ResponseManager
import web.util.hash.EncryptUtils
import web.util.hash.Md5
import web.util.read.BookInfo
import web.util.read.BookType
import web.util.read.Bookcache
import web.util.read.getlist
import web.util.read.updatebook
import java.io.File
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.URLEncoder
import kotlin.concurrent.thread

@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class BookController:BaseController() {

    @Inject
    lateinit var booklistMapper: BooklistMapper



    @Inject
    lateinit var bookCacheMapper: BookCacheMapper

    @Inject
    private lateinit var cache: CacheService

    @Inject(value = "\${admin.cron:true}", autoRefreshed=true)
    var cron:Boolean=true

    @Inject(value = "\${user.timeout:0}", autoRefreshed=true)
    var timeout: Int= 0

    @Inject(value = "\${user.proxypng:false}", autoRefreshed=true)
    var proxypng:Boolean=false



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
                        userBookSourceMapper.changeEnabled(source.bookSourceUrl,false)
                        Source.sendNotification(user)
                        App.log("搜索超时次是过多，当前书源已被禁用${source.bookSourceName}",accessToken!!)
                    }else{
                        bookSourceMapper.changeEnabled(source.bookSourceUrl,false)
                        Source.sendNotification()
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

    //@Cache(key = "exploreBook:\${accessToken},\${bookSourceUrl},\${page},\${ruleFindUrl}", tags = "search\${accessToken}", seconds = 60)
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
        val mybook=booklistMapper.getbook(user.id!!,book.bookUrl) ?: throw DataThrowable().data(JsonResponse(false,NO_BOOK))
        if(proxypng){
            if (!book.coverUrl.isNullOrEmpty() && !book.coverUrl!!.contains("baseurl/proxypng?url=")){
                book.coverUrl = "baseurl/proxypng?url=${URLEncoder.encode(book.coverUrl,"UTF-8")}"
            }
        }
        booklistMapper.upbookinfo(mybook.id?:"",book.name,book.author,book.coverUrl?:"",book.intro?:"")
        web.notification.Book.sendNotification(user)
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
                    //web.notification.Book.sendNotification(user)
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
                            //web.notification.Book.sendNotification(user)
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
        if (booklistMapper.getbook(user.id!!,booktolist.bookUrl!!) != null){
            return@runBlocking JsonResponse(false,BOOKIS)
        }
        new!!.type=book.type


        booklistMapper.insert(booktolist.bookto(new,false).apply {
            if(!book.downloadUrls.isNullOrEmpty()){
                this.downloadUrls = book.downloadUrls
            }
            this.origin=source.bookSourceUrl
            this.originName=source.bookSourceName
            this.useReplaceRule=(useReplaceRule == 1)
            val s= BookSource.fromJson(source.json).getOrNull()
            this.needimageDecode(s)
            if(proxypng){
                if (!this.coverUrl.isNullOrEmpty() && !this.coverUrl!!.contains("baseurl/proxypng?url=")){
                    this.coverUrl = "baseurl/proxypng?url=${URLEncoder.encode(this.coverUrl,"UTF-8")}"
                }
            }
        })
        web.notification.Book.sendNotification(user)
        thread {
            updatebook(booktolist, source,user)
            web.notification.Book.sendNotification(user)
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
        runCatching {
            for (book in books){
                if(book.origin == "loc_book"){
                    continue
                }
                if(book.origin == "phone_book"){
                    continue
                }
                if (book.bookUrl.isBlank() || book.name.isBlank()){
                    continue
                }
                if (booklistMapper.getbook(user.id!!,book.bookUrl) != null){
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
                if (booklistMapper.getbooklistbynametype(user.id!!,book.name,book.author,type).isNotEmpty()){
                    continue
                }
                val booktolist=Booklist.tobooklist(book,user.id!!)
                num+=booklistMapper.insert(booktolist.bookto(book, canchangeindex = true).apply {
                    if(proxypng){
                        if (!this.coverUrl.isNullOrEmpty() && !this.coverUrl!!.contains("baseurl/proxypng?url=")){
                            this.coverUrl = "baseurl/proxypng?url=${URLEncoder.encode(this.coverUrl,"UTF-8")}"
                        }
                    }
                })
            }
        }.onFailure {
            it.printStackTrace()
        }
        web.notification.Book.sendNotification(user)
        JsonResponse(true, errorMsg = "共添加${num}本书")
    }

    @Mapping("/refreshBook")
    open fun refreshBook( accessToken:String?,bookurl: String?) = runBlocking{
        val user=getuserbytocken(accessToken)
        if (bookurl.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val book=booklistMapper.getbook(user.id!!,bookurl)?:throw DataThrowable().data(JsonResponse(false,NO_BOOK))
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
            if(proxypng){
                if (!book.coverUrl.isNullOrEmpty() && !book.coverUrl!!.contains("baseurl/proxypng?url=")){
                    book.coverUrl = "baseurl/proxypng?url=${URLEncoder.encode(book.coverUrl,"UTF-8")}"
                }
                if (!book.customCoverUrl.isNullOrEmpty() && !book.customCoverUrl!!.contains("baseurl/proxypng?url=")){
                    book.customCoverUrl = "baseurl/proxypng?url=${URLEncoder.encode(book.customCoverUrl,"UTF-8")}"
                }
            }
            booklistMapper.updateById(book)
            web.notification.Book.sendNotification(user)
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
        val booktolist=booklistMapper.getbook(user.id!!,book.bookUrl).also {
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
        booklistMapper.deleteById(booktolist.id!!)
        web.notification.Book.sendNotification(user)
        bookCacheMapper.getCache(user.id!!,booktolist.id!!).also {
            if(it != null){
                bookCacheMapper.deleteById(it.id)
            }
        }
       JsonResponse(true,SUCCESS)
    }

    @Mapping("/updateuseReplaceRule")
    fun  updateuseReplaceRule( accessToken:String?, url: String ,useReplaceRule:Int?){
        val user=getuserbytocken(accessToken)
        val book=booklistMapper.getbook(user.id!!,url).also {
            if(it == null){
                throw DataThrowable().data(JsonResponse(true))
            }
        }!!
        if (useReplaceRule == 1){
            booklistMapper.uprule(book.id!!,true)
        }else{
            booklistMapper.uprule(book.id!!, false)
        }
        web.notification.Book.sendNotification(user)
        JsonResponse(true,SUCCESS)
    }

    @Mapping("/getcancache")
    open fun getcancache( accessToken:String?,  url: String)=run{
        val (user,booktolist)=getbook(accessToken,url)
        val cache=bookCacheMapper.getCache(user.id!!,booktolist.id!!)
        if(cache != null){
            throw DataThrowable().data(JsonResponse(false))
        }
        JsonResponse(true)
    }

    @Mapping("/getcancachelist")
    open fun getcancachelist( accessToken:String?)=run{
        val user=getuserbytocken(accessToken)
        JsonResponse(true).Data(bookCacheMapper.getlistbyuserid(user.id!!))
    }

    @Mapping("/addCache")
    open fun addCache( accessToken:String?, url: String)=run{
        val (user,booktolist)=getbook(accessToken,url)
        bookCacheMapper.getlistbyuserid(user.id!!).also { if(it.size >=  5) throw DataThrowable().data(JsonResponse(false,CACHE_ERROR))}
        if (booktolist.origin == "loc_book"){
            val cache= BookCache().create(user.id!!,booktolist)
            cache.num=booktolist.totalChapterNum
            val list:MutableSet<String> = mutableSetOf()
            for(i in 0..<(booktolist.totalChapterNum?:1)){
                list.add(i.toString())
            }
            cache.cacheindex= list.joinToString(",")
            bookCacheMapper.insert(cache)
            JsonResponse(true)
        }else{
            val source = getsource(booktolist.origin?:"",user)
            if(source == null){
                throw DataThrowable().data(JsonResponse(false,NOT_SOURCE))
            }
            if((source.json).lowercase().contains("webview".lowercase())){
                throw DataThrowable().data(JsonResponse(false,IS_WEBVIEW))
            }
            val cache=bookCacheMapper.getCache(user.id!!,booktolist.id!!).let {
                if(it == null){
                    BookCache().create(user.id!!,booktolist)
                }else{
                    throw DataThrowable().data(JsonResponse(false,CacheIS))
                }
            }
            bookCacheMapper.insert(cache)
            if(cron)  Bookcache.addcache(cache)
            JsonResponse(true,SUCCESS)
        }
    }

    @Mapping("/delCache")
    open fun delCache( accessToken:String?, id: String)=run{
        val user=getuserbytocken(accessToken)
        val cache=bookCacheMapper.selectById(id)
        if (cache == null || cache.userid != user.id){
            throw DataThrowable().data(JsonResponse(false,NOT_IS))
        }
        bookCacheMapper.deleteById(cache.id!!)
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



    private fun  getbook(accessToken:String?, url: String):Pair<Users,Booklist>{
        val user=getuserbytocken(accessToken)
        if(user.AllowCache != true){
            throw DataThrowable().data(JsonResponse(false,CAN_NOT))
        }
        val booktolist=booklistMapper.getbook(user.id!!,url)?: throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        return Pair(user,booktolist)
    }

}