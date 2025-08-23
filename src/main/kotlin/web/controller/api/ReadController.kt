package web.controller.api

import book.app.App
import book.model.Book
import book.model.BookChapter
import book.model.BookSource
import book.util.*
import book.webBook.WBook
import book.webBook.analyzeRule.AnalyzeRule
import book.webBook.analyzeRule.AnalyzeUrl
import book.webBook.exception.RegexTimeoutException
import book.webBook.localBook.LocalBook
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.noear.solon.annotation.*
import org.noear.solon.core.handle.Context
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.CacheRemove
import org.noear.solon.data.cache.CacheService
import org.noear.solon.web.cors.annotation.CrossOrigin
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import web.mapper.BookCacheMapper
import web.mapper.BooklistMapper
import web.mapper.ReplaceRuleMapper
import web.model.BaseSource
import web.model.ReplaceRule
import web.model.Users
import web.notification.Read
import web.response.*
import web.util.BigDataHelp
import web.util.SslUtils
import web.util.read.BookContent
import web.util.read.BookInfo
import web.util.read.getlist
import web.util.svg.svg2PNG
import java.net.HttpURLConnection
import java.net.URI
import kotlin.coroutines.cancellation.CancellationException



@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class ReadController : BaseController() {

    
    @Inject
    lateinit var booklistMapper: BooklistMapper

    @Inject
    lateinit var bookCacheMapper: BookCacheMapper

    @Inject
    lateinit var cacheService: CacheService

   
    @Inject
    lateinit var replaceRuleMapper: ReplaceRuleMapper


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(BaseController::class.java)

        fun getChapterListbycache(url: String,userid:String): Pair<List<BookChapter>?, Boolean> {
            val re: List<BookChapter>? = BigDataHelp.getChapterList(url,userid)
            var istimeout=false
            if (!re.isNullOrEmpty()) {
                logger.info("检测到目录缓存：${url}")
                val lastCheckTime= re[0].lastCheckTime?:0
                if(System.currentTimeMillis() - lastCheckTime > 24*60*60*1000){
                    logger.info("目录缓存过期：${url}")
                    istimeout=true
                }
            }
            return Pair(re,istimeout)
        }

        fun removeChapterListbycache(url: String,userid:String) {
            BigDataHelp.putChapterList(url,userid,null)
        }

        fun setChapterListbycache(url: String, re: List<BookChapter>,userid:String) {
            if (re.isNotEmpty()) {
                re[0].lastCheckTime= System.currentTimeMillis()
                BigDataHelp.putChapterList(url,userid,re)
            }
        }

        fun getBookContentbycache(url: String, index: Int,userid:String): String? {
            return BigDataHelp.getBookContent(url,userid,index)
        }

        fun setBookContentbycache(url: String, re: String, index: Int,userid:String) {
            val key = "getBookContent:${url},index:${index}"
            if (re.length > 50) {
                logger.info("添加缓存${key}")
                BigDataHelp.putBookContent(url,userid,index,re)
            }
        }

        fun removeBookContentbycache(url: String, index: Int,userid:String) {
            val key = "getBookContent:${url},index:${index}"
            logger.info("删除缓存${key}")
            BigDataHelp.putBookContent(url,userid,index,null)
        }

        fun removeallBookContentbycache(url: String,userid:String) {
            BigDataHelp.removeAllBookContent(url,userid)
        }

        fun removeBookcache(url: String,userid:String) {
            BigDataHelp.putBookInfo(url,userid,null)
        }


        fun getBookbycache(url: String,userid:String): Book? {
            val re: Book? = BigDataHelp.getBookInfo(url,userid)
            if (re != null) {
                logger.info("检测到书本缓存：${url}")
            }
            return re
        }

        fun setBookbycache(url: String, book: Book,userid:String) {
            BigDataHelp.putBookInfo(url,userid,book)
        }
    }

    private  fun getChapterList(accessToken: String?, bookSourceUrl: String?, url: String,user: Users) = runBlocking {
        val (old,istimeout)=getChapterListbycache(url,user.id!!)
        if(!istimeout && !old.isNullOrEmpty()){
            logger.info("目录缓存使用成功")
            return@runBlocking old
        }
        logger.info("书本：${url}，查询目录")
        var chapters:List<BookChapter>?=null
        runCatching {
            when {
                bookSourceUrl == "loc_book" -> getlist(url).let {
                    setChapterListbycache(url, it,user.id!!)
                    chapters=it
                }

                else -> {
                    val source = getsource(user ,bookSourceUrl)
                    getlist(url, source, user, accessToken ?: "").let {
                        runCatching {
                            val book = booklistMapper.getbook(user.id!!, url)
                            if(book != null) {
                                val lastCheckTime=System.currentTimeMillis()
                                val lastCheckCount=it.size
                                if (it.size != book.totalChapterNum ){
                                    val totalChapterNum=it.size
                                    val latestChapterTitle=it[it.size-1].title
                                    val latestChapterTime=System.currentTimeMillis()
                                    booklistMapper.updatetime(book.id!!,latestChapterTitle,latestChapterTime,lastCheckTime,lastCheckCount, totalChapterNum )
                                    bookCacheMapper.getCache(book.userid!!,book.id!!).let {it1->
                                        if(it1!=null){
                                            bookCacheMapper.updatetime(it1.id!!,totalChapterNum)
                                        }
                                    }
                                }else{
                                    booklistMapper.updatetimefail(book.id!!,lastCheckTime,lastCheckCount)
                                }
                            }
                        }
                        chapters=it
                    }
                }
            }
        }.getOrElse {
            App.log("目录加载出错:"+it.message,accessToken!!)
            if(!old.isNullOrEmpty()){
                return@runBlocking old
            }
            throw DataThrowable().data(JsonResponse(false, it.message?:"目录加载出错"))
        }
        chapters
    }

    @Mapping("/getChapterList")
    fun getChapterList(accessToken: String?, bookSourceUrl: String?, url: String?) = runBlocking {
        if (url == null) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        val user = getuserbytocken(accessToken)
        JsonResponse(true).Data(getChapterList(accessToken,bookSourceUrl,url,user))
    }

    private   fun getBookContent(
        accessToken: String?, bookSourceUrl: String?, url: String, index: Int?, type: Int?,user: Users
    ) = runBlocking {
        if (type != 1) {
            val txt = getBookContentbycache(url, index ?: 0,user.id!!)
            if (!txt.isNullOrEmpty()) {
                logger.info("正文缓存使用成功")
                return@runBlocking txt
            }
        }
        logger.info("书本：${url}，查询：${index}")
        when {
            bookSourceUrl == "loc_book" -> {
                var (chapterlist,_) = getChapterListbycache(url,user.id!!)
                if (chapterlist == null) {
                    chapterlist = getlist(url).also {
                        setChapterListbycache(url, it,user.id!!)
                    }
                }
                val book = Book.initLocalBook(url, url, "")
                LocalBook.getContent(book, chapterlist[index ?: 0]).toString().let {
                    setBookContentbycache(url, it, index ?: 0,user.id!!)
                    it
                }
            }

            else -> {
                val source = getsource(accessToken, bookSourceUrl)
                val re = BookContent.getbookcontent(accessToken ?: "", user, source, url, index ?: 0, type ?: 0)
                re
            }
        }
    }


    @Mapping("/getBookContent")
    fun getBookContent(
        accessToken: String?, bookSourceUrl: String?, url: String?, index: Int?, type: Int?
    ) = runBlocking {
        if (url == null) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        val user = getuserbytocken(accessToken)
        JsonResponse(true).Data(getBookContent(accessToken,bookSourceUrl,url,index,type,user))
    }

    @Mapping("/getBookContentNew")
    fun getBookContentNew(
        accessToken: String?, bookSourceUrl: String?, url: String?, index: Int?, type: Int?, bookname: String?,useReplaceRule:Int?
    ) = runBlocking {
        if (url == null) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        val user = getuserbytocken(accessToken)
        var re=getBookContent(accessToken,bookSourceUrl,url,index,type,user)
        val  effectiveReplaceRules:MutableList<ReplaceRule> = mutableListOf()
        if(type == 0 && !bookname.isNullOrBlank() && useReplaceRule == 1 ){
            val rules=replaceRuleMapper.getrulebybookname(user.id!!,"%$bookname%",bookSourceUrl?:"").filter {
                it.scopeContent && (it.excludeScope == null || it.excludeScope == "" || (!it.excludeScope!!.contains(bookname) &&  !it.excludeScope!!.contains(bookSourceUrl?:"111")))
            }
            logger.info("获取到${rules.size}条规则")
            re = re.lines().joinToString("\n") { it.trim() }
            rules.forEach {item ->
                if (item.pattern.isEmpty()) {
                    return@forEach
                }
                try {
                    val tmp = if (item.isRegex) {
                        re.replace(
                            item.pattern,
                            item.replacement,
                            item.getValidTimeoutMillisecond()
                        )
                    } else {
                        re.replace(item.pattern, item.replacement)
                    }
                    if (re != tmp) {
                        effectiveReplaceRules.add(item)
                        re = tmp
                    }
                } catch (e: RegexTimeoutException) {
                    replaceRuleMapper.changeEnabled(item.id!!,false)
                    logger.info(e.message)
                    App.log("替换净化:"+e.message,accessToken!!)
                } catch (_: CancellationException) {
                    logger.info("取消了")
                } catch (e: Exception) {
                    App.log("替换净化: 规则 ${item.name}替换出错.",accessToken!!)
                    logger.info("替换净化: 规则 ${item.name}替换出错.\n", e)
                }
            }
            logger.info("生效${effectiveReplaceRules.size}条规则")
        }
        JsonResponse(true).Data(mapOf("rules" to effectiveReplaceRules,"text" to re))
    }

    @Mapping("/getChapterListNew")
    fun getChapterListNew(accessToken: String?, bookSourceUrl: String?, url: String?, bookname: String?,useReplaceRule:Int?,needRefresh:Int?) = runBlocking {
        if (url == null) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        val user = getuserbytocken(accessToken)
        if (needRefresh == 1) {
            removeChapterListbycache(url,user.id!!)
        }
        val chapters=getChapterList(accessToken,bookSourceUrl,url,user)
        if(!bookname.isNullOrBlank() && useReplaceRule == 1){
            val rules=replaceRuleMapper.getrulebybookname(user.id!!,"%$bookname%",bookSourceUrl?:"").filter {
                it.scopeTitle && (it.excludeScope == null || it.excludeScope == "" || (!it.excludeScope!!.contains(bookname) &&  !it.excludeScope!!.contains(bookSourceUrl?:"111")))
            }
            if(rules.isNotEmpty()){
                chapters?.forEach{
                    rules.forEach {item ->
                        if (item.pattern.isNotEmpty()) {
                            try {
                                val tmp = if (item.isRegex) {
                                    it.title.replace(
                                        item.pattern,
                                        item.replacement,
                                        item.getValidTimeoutMillisecond()
                                    )
                                } else {
                                    it.title.replace(item.pattern, item.replacement)
                                }
                                if (it.title != tmp) {
                                    it.title = tmp
                                }
                            } catch (e: RegexTimeoutException) {
                                replaceRuleMapper.changeEnabled(item.id!!,false)
                                logger.info(e.message)
                                App.log("替换净化:"+e.message,accessToken!!)
                            } catch (_: CancellationException) {
                                logger.info("取消了")
                            } catch (e: Exception) {
                                App.log("替换净化: 规则 ${item.name}替换出错.",accessToken!!)
                                logger.info("替换净化: 规则 ${item.name}替换出错.\n", e)
                            }
                        }
                    }
                }
            }
        }
        JsonResponse(true).Data(chapters)
    }

    @Mapping("/fetchBookContent")
    fun fetchBookContent(accessToken: String?,url: String?, index: Int?) = runBlocking {
        if (url == null) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        val user = getuserbytocken(accessToken)
        removeBookContentbycache(url, index ?: 0,user.id!!)
       // removeChapterListbycache(url, user.id!!)
        JsonResponse(true)
    }

    @Mapping("/fetchBook")
    fun fetchBook(accessToken: String?,url: String?) = runBlocking {
        if (url == null) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        val user = getuserbytocken(accessToken)

        removeBookcache(url,user.id!!)
        removeChapterListbycache(url,user.id!!)
        removeallBookContentbycache(url,user.id!!)
        val booktolist=booklistMapper.getbook(user.id!!,url)
        if(booktolist != null){
            bookCacheMapper.getCache(user.id!!,booktolist.id!!).also {
                if(it != null){
                    bookCacheMapper.deleteById(it.id)
                }
            }
        }
        JsonResponse(true)
    }


    @Mapping("/saveBookProgress")
    open fun saveBookProgress(accessToken: String?, pos: Double?, url: String?, title: String?, index: Int?) = runBlocking {
        val user = getuserbytocken(accessToken)
        val book = booklistMapper.getbook(user.id!!, url?:throw DataThrowable().data(JsonResponse(false, NOT_BANK))).also {
            if (it == null) {
                //println("添加阅读进度到内存${url}")
                cacheService.store("indexuerid:${user.id},bookurl:${url}",index,10*30)
                throw DataThrowable().data(JsonResponse(true))
            }
        }!!
        var read = book.readchapter ?: ""
        val s = read.split(",").toMutableSet()
        s.add((index ?: 0).toString())
        read = s.joinToString(",")
        if (book.origin == "loc_book") {
            val list: List<BookChapter> = getChapterListbycache(url!!,user.id!!).let {
                if (it.first == null){
                    getlist(url)
                }else{
                    it.first!!
                }
            }
            booklistMapper.updatepos(
                book.id!!,
                list[index ?: 0].title,
                index ?: 0,
                pos ?: 0.0,
                System.currentTimeMillis(),
                read
            )
        } else {
            val source = getsource(book.origin!!,user)
            var t=title
            if(t.isNullOrBlank()){
                val list: List<BookChapter> = getChapterListbycache(url!!,user.id!!).let {
                    if (it.first == null){
                        getlist(url, source!!, user, accessToken ?: "")
                    } else {
                        it.first!!
                    }
                }
                t=list[index ?: 0].title
            }
            booklistMapper.updatepos(
                book.id!!,
                t,
                index ?: 0,
                pos ?: 0.0,
                System.currentTimeMillis(),
                read
            )
        }
       // Companion.logger.info("read push")
        Read.sendNotification(user,accessToken!!,url)
        JsonResponse(true)
    }


    @Mapping("/getBookread")
    fun getBookread(accessToken: String?, url: String?) = runBlocking {
        val user = getuserbytocken(accessToken)
        val book = booklistMapper.getbook(user.id!!, url.also {
            if (it == null) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }!!).also {
            if (it == null) throw DataThrowable().data(JsonResponse(false, NO_BOOK))
        }!!
        val read = book.readchapter ?: ""
        val s = read.split(",").toMutableSet()
        val list: MutableSet<Any> = mutableSetOf()
        s.forEach {
            if (it != "") {
                list.add(it)
            }
        }
        JsonResponse(true).Data(list.joinToString(","))
    }


    @Mapping("/setBookSource")
    open fun setBookSource(
        accessToken: String?, bookUrl: String?, newUrl: String?, bookSourceUrl: String?
    ) = runBlocking {
        if (newUrl.isNullOrBlank()) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        val user = getuserbytocken(accessToken)
        val book = booklistMapper.getbook(user.id!!, bookUrl.also {
            if (it == null) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }!!).also {
            if (it == null) throw DataThrowable().data(JsonResponse(false, NO_BOOK))
        }!!
        val source = getsource(bookSourceUrl?:"",user)?: throw DataThrowable().data(JsonResponse(false, NOT_SOURCE))
        var new: Book? = null
        runCatching {
            new = BookInfo.getbookinfo(accessToken!!,user,source,newUrl)
        }.onFailure {
            val webBook = WBook(source.json , user.id!!, accessToken, false)
            webBook.searchBook(book.name ?: " ", 1).forEach {
                if (it.bookUrl == newUrl) {
                    new = it.toBook()
                }
            }
            if (new != null) {
                booklistMapper.updateById(book.bookto(new,false))
            } else {
                throw DataThrowable().data(JsonResponse(false, NO_BOOK))
            }
        }.onSuccess {
            booklistMapper.updateById(book.bookto(new!!,false))
        }
        web.notification.Book.sendNotification(user)
        bookCacheMapper.getCache(user.id!!,book.id!!).also {
            if(it != null){
                bookCacheMapper.deleteById(it.id)
            }
        }
        JsonResponse(true).Data(book)
    }

   // @Cache(key = "getBookshelf:\${accessToken}", tags = "getBookshelf", seconds = 20)
    @Mapping("/getBookshelf")
    open fun getBookshelf(accessToken: String?,version:String?,@Path v:Int) = run {
       if(v < apiversion){
           throw DataThrowable().data(JsonResponse(false,NEED_LOGIN))
       }else  if(v > apiversion){
           throw DataThrowable().data(JsonResponse(false,NEED_LOGIN))
       }
        val user = getuserbytocken(accessToken)
        val book = booklistMapper.getbooklistbyuserid(user.id!!)
        book?.forEach {
            if (it.customCoverUrl != null && it.customCoverUrl!!.isNotBlank()) {
                it.coverUrl = it.customCoverUrl
            }
            if (it.customIntro != null && it.customIntro!!.isNotBlank()) {
                it.intro = it.customIntro
            }
            if (it.customIntro != null && it.customIntro!!.isNotBlank()) {
                it.intro = it.customIntro
            }
            if (it.durChapterPos == null) {
                it.durChapterPos = 0.0
            }
            if (it.durChapterPos!! > 2 || it.durChapterPos!! < 0) {
                it.durChapterPos = 0.0
            }
        }
       JsonResponse(true,if (appversion ==version) "ok" else appversion).Data(book)
    }

    @Mapping("/getSourcesloginui")
    fun  getSourcesloginui(accessToken: String?, url: String) = run {
        val user = getuserbytocken(accessToken)
        val source :BaseSource =if(user.source == 2){
            user.id?.let {  userBookSourceMapper.getBookSource(url,it) }?.toBaseSource()
        }else{
            bookSourceMapper.getBookSource(url)?.toBaseSource()
        }?: throw DataThrowable().data(JsonResponse(false, NOT_SOURCE))
        val s=BookSource.fromJson(source.json).getOrNull()
        s?.usertocken=accessToken
        s?.userid=user.id
        var loginUi=s?.loginUi
        if(!loginUi.isNullOrEmpty()){
            runCatching {
                val r=GSON.fromJsonArray<Any>(loginUi).getOrNull()
                loginUi= GSON.toJson(r)
            }
        }
        JsonResponse(true).Data(loginUi)
    }

    //@Cache(key = "getBookSources", tags = "getBookSources", seconds = 600)
    @Mapping("/getBookSources")
    open fun getBookSources(accessToken: String?,isall: String?,@Path v:Int ) = run {
        if(v < apiversion){
            throw DataThrowable().data(JsonResponse(false,NEED_LOGIN))
        }else  if(v > apiversion){
            throw DataThrowable().data(JsonResponse(false,NEED_LOGIN))
        }
        val user = getuserbytocken(accessToken)
        val source: List<BaseSource> = if(isall != null && isall == "1" && user.source != 0){
            getallBookSourcelist(user)
        }else{
            getBookSourcelist(true,user)
        }
        val list: MutableList<Map<String, Any?>> = mutableListOf()
        source.forEach {
            val s=BookSource.fromJson(it.json).getOrNull()
            s?.usertocken=accessToken
            s?.userid=user.id
            var loginUi=s?.loginUi
            if(!loginUi.isNullOrEmpty()){
                runCatching {
                    val r=GSON.fromJsonArray<Any>(loginUi).getOrNull()
                    loginUi= GSON.toJson(r)
                }
            }

            list.add(
                mapOf(
                    "checkKeyWord" to s?.ruleSearch?.checkKeyWord,
                    "variableComment" to s?.variableComment,
                    "bookSourceGroup" to it.bookSourceGroup,
                    "loginUrl" to s?.loginUrl,
                    "loginUi" to loginUi,
                    "bookSourceName" to it.bookSourceName,
                    "bookSourceUrl" to it.bookSourceUrl,
                    "enabledExplore" to it.enabledExplore,
                    "enabled" to it.enabled
                )
            )
        }
        JsonResponse(true,(if(user.source == 0) "no" else "ok")).Data(list)
    }


    @Mapping("/getBookSourcesExploreUrl")
    open fun getBookSourcesExploreUrl(accessToken: String?, bookSourceUrl: String?,need: String?) = runBlocking {
        val (user,source)=getsourceuser(accessToken,bookSourceUrl)
        val booksource = BookSource.fromJson(source.json ).getOrNull()
        booksource?.userid=user.id
        booksource?.usertocken=accessToken
        JsonResponse(true).Data(mapOf("checkKeyWord" to booksource?.ruleSearch?.checkKeyWord,"found" to booksource?.exploreKinds((need == "1")), "loginUrl" to booksource?.loginUrl, "loginUi" to booksource?.loginUi))
    }

    @Mapping("/getopenurl")
    fun  getopenurl(accessToken: String?, bookSourceUrl: String?, url: String?) = run{
        val (user,source)=getsourceuser(accessToken,bookSourceUrl)
        val s= BookSource.fromJson(source.json).getOrNull()!!
        s.usertocken=accessToken
        s.userid=user.id
        val analyzeUrl = AnalyzeUrl(
            url?:"", source = s,
            debugLog = null
        )
        JsonResponse(true).Data(analyzeUrl.url)
    }

    @Mapping("/svgtopng")
    open fun svgtopng(ctx: Context, accessToken: String?, svg: String?){
        getuserbytocken(accessToken)
        if (svg.isNullOrBlank()) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        svg2PNG(svg,ctx.outputStream())
        ctx.close()
    }


    @Mapping("/imageDecode")
    open fun imageDecode(ctx: Context, accessToken: String?, bookSourceUrl: String?, @Param("book")  ibook: String?, url: String?, header: String?) = runBlocking {
        logger.info("imageDecode:$url")
        val (user,source)=getsourceuser(accessToken,bookSourceUrl)
        if(user.AllowImg != true){
            App.toast("没有权限进行图片解密",accessToken?:"")
            throw DataThrowable().data(JsonResponse(false, CAN_NOT))
        }
        if (url.isNullOrBlank()) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        val geturl = URI(url).toURL()
        SslUtils.ignoreSsl()
        val connection = geturl.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        runCatching {
            val json= Gson().fromJson<Map<String, String>>(header, Map::class.java)
            json.forEach{(k,v)->
                connection.setRequestProperty(k,v)
            }
        }
        connection.connectTimeout = 20*1000
        connection.readTimeout = 20*1000
        val responseCode = connection.responseCode
        //  读取响应
        if (responseCode == HttpURLConnection.HTTP_OK) { // 200表示请求成功
            ctx.contentType(connection.getHeaderField("Content-Type"))
            val s= BookSource.fromJson(source.json).getOrNull()!!
            s.usertocken=accessToken
            s.userid=user.id
            if(s.hasimageDecode()){
                s.userid = user.id
                s.usertocken = accessToken
                var book:Book?=null
                if(!ibook.isNullOrBlank()){
                   runCatching { book= GSON.fromJson(ibook, Book::class.java) }.onFailure {
                       App.log("格式化book失败:${it.message}",accessToken!!)
                   }
                }
               runCatching {
                    val bytes = s.DeimageDecode(src = url, inputStream = connection.inputStream,book=book)
                    ctx.outputStream().write(bytes)
                    ctx.flush()
                }.onFailure {
                    it.printStackTrace()
                    App.log("图片解密失败:${it.message}",accessToken!!)
                    runCatching {
                        connection.inputStream.use { i->
                            val b = ByteArray(4096)
                            var len: Int
                            while ((i.read(b).also { it -> len = it }) != -1) {
                                ctx.outputStream().write(b, 0, len)
                            }
                        }
                    }.onFailure {
                        JsonResponse(isSuccess = false,errorMsg ="解密失败")
                    }
                }
            } else {
                connection.inputStream.use { i->
                    val b = ByteArray(4096)
                    var len: Int
                    while ((i.read(b).also { len = it }) != -1) {
                        ctx.outputStream().write(b, 0, len)
                    }
                }
            }

        } else {
            logger.info("GET请求失败")
            JsonResponse(isSuccess = false,errorMsg ="GET请求失败")
        }

    }

    @Mapping("/getLoginInfo")
    open fun getLoginInfo(accessToken: String?, bookSourceUrl: String?) = run {
        val (user,source)=getsourceuser(accessToken,bookSourceUrl)
        val bookSource = BookSource.fromJson(source.json ).getOrNull()!!
        bookSource.userid = user.id
        bookSource.usertocken = accessToken
        var info = bookSource.getLoginInfo()
        if (info.isNullOrBlank()) {
            info = "{}"
        }
        JsonResponse(true).Data(info)
    }

    @Mapping("/getVariable")
    open fun getVariable(accessToken: String?, bookSourceUrl: String?) = run {
        val (user,source)=getsourceuser(accessToken,bookSourceUrl)
        val bookSource = BookSource.fromJson(source.json ).getOrNull()!!
        bookSource.userid = user.id
        bookSource.usertocken = accessToken
        val info = bookSource.getVariable()
        JsonResponse(true).Data(info)
    }

     @CacheRemove(tags = "search\${accessToken}")
    @Mapping("/setVariable")
    open fun setVariable(accessToken: String?, bookSourceUrl: String?, info: String?) = run {
        val (user,source)=getsourceuser(accessToken,bookSourceUrl)
        val bookSource = BookSource.fromJson(source.json ).getOrNull()!!
        bookSource.userid = user.id
        bookSource.usertocken = accessToken
        bookSource.setVariable(info)
        JsonResponse(true)
    }

    @Mapping("/getbookVariable")
    open fun getbookVariable(accessToken: String?, bookurl: String?) = run {
        val user = getuserbytocken(accessToken)
        val book =Book(bookUrl  =bookurl?:"")
        book.userid = user.id?:""
        val info = book.getCustomVariable()
        JsonResponse(true).Data(info)
    }

    @CacheRemove(tags = "search\${accessToken}")
    @Mapping("/setbookVariable")
    open fun setbookVariable(accessToken: String?, bookurl: String?, info: String?) = run {
        val user = getuserbytocken(accessToken)
        val book =Book(bookUrl  =bookurl?:"")
        book.userid = user.id?:""
        book.putCustomVariable(info?:"")
        JsonResponse(true)
    }

     @CacheRemove(tags = "search\${accessToken}")
    @Mapping("/putLoginInfo")
    open fun putLoginInfo(accessToken: String?, bookSourceUrl: String?, info: String?) = run {
        val (user,source)=getsourceuser(accessToken,bookSourceUrl)
        val bookSource = BookSource.fromJson(source.json ).getOrNull()!!
        bookSource.userid = user.id
        bookSource.usertocken = accessToken
        bookSource.putLoginInfo(info ?: "{}")
         runCatching { bookSource.login() }
        JsonResponse(true)
    }

    @CacheRemove(tags = "search\${accessToken}")
    @Mapping("/action")
    open fun action(accessToken: String?, bookSourceUrl: String?, action: String?) = runBlocking {
        val (user,source)=getsourceuser(accessToken,bookSourceUrl)
        if(action == null) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        val bookSource = BookSource.fromJson(source.json).getOrNull()!!
        bookSource.userid = user.id
        bookSource.usertocken = accessToken
        runCatching {
            bookSource.runaction(action)
        }.onFailure { e ->
           logger.info("$action JavaScript error", e)
        }
        JsonResponse(true)
    }

    @Mapping("/payAction")
    open fun  payAction(accessToken: String?, url: String?, index: Int) = runBlocking {
        val user = getuserbytocken(accessToken)
        val book=booklistMapper.getbook(user.id!!,url?:throw DataThrowable().data(JsonResponse(false, NOT_BANK)))?:
        throw DataThrowable().data(JsonResponse(false, NO_BOOK))
        if(book.origin ==  "loc_book") return@runBlocking JsonResponse(true)
        val source=getsource(book.origin!!,user)?:throw DataThrowable().data(JsonResponse(false, NOT_SOURCE))
        val bookSource = BookSource.fromJson(source.json).getOrNull()!!
        bookSource.userid = user.id
        bookSource.usertocken = accessToken
        val payAction = bookSource.getContentRule().payAction
        if (payAction.isNullOrBlank()) {
            throw DataThrowable().data(JsonResponse(false, NO_PAY))
        }
        val chapters=getChapterList(accessToken,book.origin,book.bookUrl!!,user)!!
        val b= getBookbycache(url,user.id!!)?: BookInfo.getbookinfo(accessToken!!,user,source,url)!!
        val analyzeRule = AnalyzeRule(
            ruleData = b, source = bookSource,
            debugLog = null
        )
        analyzeRule.setBaseUrl(chapters[index].url)
        analyzeRule.chapter = chapters[index]
        val re=analyzeRule.evalJS(payAction).toString()
        if (re.isAbsUrl()) {
            analyzeRule.startBrowser(re,"购买")
        }
        JsonResponse(true)
    }


    @Mapping("/changebooktype")
    open fun changebooktype(accessToken: String?, bookUrl: String?, type: Int?) = runBlocking {
        val user = getuserbytocken(accessToken)
        val book = booklistMapper.getbook(user.id!!, bookUrl.also {
            if (it == null) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }!!).also {
            if (it == null) throw DataThrowable().data(JsonResponse(false, NO_BOOK))
        }!!
        var type1: Int = type ?: 0
        if (type1 != 0 && type1 != 1 && type1 != 2) {
            type1 = 0
        }
        booklistMapper.changetype(book.id!!,type1)
        web.notification.Book.sendNotification(user)
        JsonResponse(true)
    }

}