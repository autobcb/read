package web.Controller.Api

import com.google.gson.Gson
import io.legado.app.data.entities.Book
import io.legado.app.model.webBook.WebBook
import kotlinx.coroutines.runBlocking
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import web.Response.JsonResponse
import io.legado.app.data.entities.BookChapter
import io.legado.app.model.localBook.LocalBook
import web.Mapper.BooklistMapper
import web.Util.model.ChapterList
import web.Util.cache.getcache
import web.Util.cache.getcachez
import web.Util.cache.setcache
import web.Util.cache.setcachez
import web.Util.model.ExploreUrl
import web.Util.read.getlist

@ApiController
class ReadController(private val booklistMapper: BooklistMapper) :BaseController() {

    @RequestMapping(value = ["/getChapterList"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getChapterList(@RequestParam("accessToken") accessToken:String?, @RequestParam("bookSourceUrl") bookSourceUrl:String?,
                      @RequestParam("url") url:String? )= runBlocking{
        if (url == null) return@runBlocking JsonResponse(false,NOT_BANK)
        if (bookSourceUrl == "loc_book"){
            val re=getlist(url)
            return@runBlocking JsonResponse(true,SUCCESS).Data(re)
        }
        val (source,jp)=getsource(accessToken,bookSourceUrl)
        if(jp != null){
            return@runBlocking jp!!
        }
        var re:List<BookChapter> =getlist(url,source!!)
        JsonResponse(true,SUCCESS).Data(re)
    }

    @RequestMapping(value = ["/getBookContent"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getBookContent(@RequestParam("accessToken") accessToken:String?, @RequestParam("bookSourceUrl") bookSourceUrl:String?
                       , @RequestParam("url") url:String?,@RequestParam("index")index:Int?)=runBlocking{
        if (url == null) return@runBlocking JsonResponse(false,NOT_BANK)
        var txt= getcachez(url,(index?:0).toString())
        if(txt != null){
            return@runBlocking JsonResponse(true,SUCCESS).Data(txt)
        }
        var cachetxt= getcache(url)
        if(cachetxt != null){
            logger.info ("getBookContent")
            var cache=Gson().fromJson(cachetxt, ChapterList::class.java)
            var re=""
            if (cache.source == null){
                re=LocalBook.getContent(cache.book!!,cache.bookChapters[index?:0]).toString()
                if(re.length > 500){
                    setcachez(url,(index?:0).toString(),re)
                }
            }else{
                var source=bookSourcemapper.getBookSource(cache.source!!.bookSourceUrl)
                var webBook = WebBook(source!!.json?:"", false)
                re=webBook.getBookContent(cache.book!!,cache.bookChapters[index?:0])
                if(re.length > 500){
                    setcachez(url,(index?:0).toString(),re)
                }
            }
            JsonResponse(true,SUCCESS).Data(re)
        }else if(bookSourceUrl != null){
            var re=""
            if (bookSourceUrl == "loc_book"){
                val book = Book.initLocalBook(url, url, "")
                val chapters = LocalBook.getChapterList(book)
                var cache= ChapterList(book =book, bookChapters=chapters,source=null)
                val json= Gson().toJson(cache)
                setcache(url,json)
                re=LocalBook.getContent(cache.book!!,cache.bookChapters[index?:0]).toString()
                if(re.length > 500){
                    setcachez(url,(index?:0).toString(),re)
                }
            }else{
                var source=getsource(bookSourceUrl)
                var webBook = WebBook(source!!.json?:"", false)
                var book=webBook.getBookInfo(url,canReName = true)
                var list=webBook.getChapterList(book!!)
                var cache= ChapterList(book =book, bookChapters=list,source=source)
                val json= Gson().toJson(cache)
                setcache(url,json)
                re=webBook.getBookContent(book,list[index?:0])
                if(re.length > 500){
                    setcachez(url,(index?:0).toString(),re)
                }
            }
            JsonResponse(true,SUCCESS).Data(re)
        }else {
            JsonResponse(false,NO_BOOK)
        }
    }

    @RequestMapping(value = ["/saveBookProgress"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun saveBookProgress(@RequestParam("accessToken") accessToken:String?, @RequestParam("pos") pos:Double?
                         , @RequestParam("url") url:String?,@RequestParam("index")index:Int?)= runBlocking{
        var user=getuserbytocken(accessToken).also {
            if (it == null) return@runBlocking JsonResponse(false,NEED_LOGIN)
        }!!
        var book=booklistMapper.getbook(user.id,url.also {
            if (it == null) return@runBlocking JsonResponse(false,NOT_BANK)
        }!!).also {
            if (it == null) return@runBlocking JsonResponse(false,NO_BOOK)
        }!!
        var read=book.readchapter?:""
        var s=read.split(",").toMutableSet()
        s.add((index?:0).toString())
        read=s.joinToString(",")
        if (book.origin == "loc_book"){
            var list:List<BookChapter> =getlist(url!!)
            booklistMapper.updatepos(book.id,list[index?:0].title,index?:0,pos?:0.0,System.currentTimeMillis() ,read)
        }else{
            var source=bookSourcemapper.getBookSource(book.origin)
            var list:List<BookChapter> =getlist(url!!,source!!)
            booklistMapper.updatepos(book.id,list[index?:0].title,index?:0,pos?:0.0,System.currentTimeMillis() ,read)
        }
        JsonResponse(true,SUCCESS)
    }

    @RequestMapping(value = ["/getBookread"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getBookread(@RequestParam("accessToken") accessToken:String?,
                    @RequestParam("url") url:String?)= runBlocking{
        var user=getuserbytocken(accessToken).also {
            if (it == null) return@runBlocking JsonResponse(false,NEED_LOGIN)
        }!!
        var book=booklistMapper.getbook(user.id,url.also {
            if (it == null) return@runBlocking JsonResponse(false,NOT_BANK)
        }!!).also {
            if (it == null) return@runBlocking JsonResponse(false,NO_BOOK)
        }!!
        var read=book.readchapter?:""
        var s=read.split(",").toMutableSet()
        var list:MutableSet<Any> = mutableSetOf()
        s.forEach{
            if(it != ""){
                list.add(it)
            }
        }
        JsonResponse(true,SUCCESS).Data(list.joinToString(","))
    }

    @RequestMapping(value = ["/setBookSource"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun setBookSource(@RequestParam("accessToken") accessToken:String?, @RequestParam("bookUrl") bookUrl:String?
                      , @RequestParam("newUrl") newUrl:String?,@RequestParam("bookSourceUrl")bookSourceUrl:String?)=runBlocking{
        val user=getuserbytocken(accessToken).also {
            if (it == null) return@runBlocking JsonResponse(false,NEED_LOGIN)
        }!!
        var book=booklistMapper.getbook(user.id,bookUrl.also {
            if (it == null) return@runBlocking JsonResponse(false,NOT_BANK)
        }!!).also {
            if (it == null) return@runBlocking JsonResponse(false,NO_BOOK)
        }!!
        val source=getsource(bookSourceUrl)
        var webBook = WebBook(source!!.json?:"", false)
        var new=webBook.getBookInfo(newUrl?:"",canReName = true)
        //println(Gson().toJson(new))
        booklistMapper.updateById(book.bookto(new))
        JsonResponse(true,SUCCESS).Data(book)
    }

    @RequestMapping(value = ["/getBookshelf"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getBookshelf(@RequestParam("accessToken") accessToken:String?)=run{
        val user=getuserbytocken(accessToken).also {
            if (it == null) return@run JsonResponse(false,NEED_LOGIN)
        }!!
        var book=booklistMapper.getbooklistbyuserid(user.id)
        book?.forEach{
            if (it.customCoverUrl != null && it.customCoverUrl!!.isNotBlank()) {
                it.coverUrl = it.customCoverUrl
            }

            if (it.customIntro != null && it.customIntro!!.isNotBlank()) {
                it.intro = it.customIntro
            }
            if (it.customIntro != null && it.customIntro!!.isNotBlank()) {
                it.intro = it.customIntro
            }
        }
        JsonResponse(true,SUCCESS).Data(book)
    }

    @RequestMapping(value = ["/getBookSources"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getBookSources(@RequestParam("accessToken") accessToken:String?)=run{
        getuserbytocken(accessToken).also {
            if (it == null) return@run JsonResponse(false,NEED_LOGIN)
        }!!
        var source=bookSourcemapper.getBookSourcelist(true)
        var list:MutableList<Map<String,Any>> = mutableListOf()
        source?.forEach {
            list.add(mapOf("bookSourceName" to it.bookSourceName ,"bookSourceUrl" to it.bookSourceUrl ,"enabledExplore" to it.enabledExplore))
        }
        JsonResponse(true,SUCCESS).Data(list)
    }

    @RequestMapping(value = ["/getBookSourcesExploreUrl"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getBookSourcesExploreUrl(@RequestParam("accessToken") accessToken:String?,@RequestParam("bookSourceUrl")bookSourceUrl:String?)= runBlocking{
        val (source,jp)=getsource(accessToken,bookSourceUrl)
        if(jp != null){
            return@runBlocking jp!!
        }
        if ((source!!.exploreUrl?:"").contains("<js>") || (source!!.exploreUrl?:"").contains("@js")  ){
            var gson=Gson()
            var ca=getcache(source.bookSourceUrl)
            if(ca != null){
                var cache= gson.fromJson(ca,ExploreUrl::class.java)
                if (cache.exploreUrl.equals(source.exploreUrl)){
                    logger.info("缓存中获取发现："+source.bookSourceUrl)
                    return@runBlocking JsonResponse(true,SUCCESS).Data(cache.cachetxt)
                }
            }
            var webBook = WebBook(source!!.json?:"", false)
            var cache= ExploreUrl()
            with(cache){
                exploreUrl=source.exploreUrl?:""
                cachetxt=webBook.getexploreUrl()
            }
            setcache(source.bookSourceUrl,gson.toJson(cache))
            return@runBlocking JsonResponse(true,SUCCESS).Data(cache.cachetxt)
        }
        JsonResponse(true,SUCCESS).Data(source!!.exploreUrl)
    }

}