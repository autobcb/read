package web.Controller.Admin

import com.google.gson.Gson
import io.legado.app.model.Debugger
import io.legado.app.model.webBook.WebBook
import jakarta.servlet.http.HttpServletResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import web.Mapper.BookSourceMapper
import web.Model.BookSource
import web.Response.JsonResponse
import web.Util.LogFactory
import web.Util.json.tojson
import web.Util.model.Booksource

@AdminApiController
class AdminDebugBookSource: LogFactory() {


    @Autowired
    lateinit var bookSourcemapper: BookSourceMapper


    @RequestMapping(value = ["/saveBookSources"], produces = ["application/json;charset=UTF-8"])
    fun saveBookSources(@RequestBody bookSourcelist: List<Booksource>)=run{
        var insert = 0
        var update = 0
        bookSourcelist.forEach {
            if(it.bookSourceUrl.isNotBlank() && it.bookSourceType == 0){
                addorupdate(it).let {  (ins,ups)->
                    insert=insert+ins
                    update=update+ups
                }
            }
        }
        JsonResponse(true,"新增${insert}条书源，更新${update}条书源")
    }

    @RequestMapping(value = ["/saveBookSource"], produces = ["application/json;charset=UTF-8"])
    fun saveBookSource(@RequestBody booksource:Booksource)=run{
        var insert = 0
        var update = 0
        addorupdate(booksource).let {  (ins,ups)->
            insert=insert+ins
            update=update+ups
        }
        JsonResponse(true,"新增${insert}条书源，更新${update}条书源")
    }

    @RequestMapping(value = ["/deleteBookSources"], produces = ["application/json;charset=UTF-8"])
    fun deleteBookSources(@RequestBody bookSourcelist: List<Booksource>) = run{
        bookSourcelist.forEach {
            if(it.bookSourceUrl.isNotBlank() && it.bookSourceType == 0){
                bookSourcemapper.deleteById(it.bookSourceUrl)
            }
        }
        JsonResponse(true,"success")
    }

    @RequestMapping(value = ["/bookSourceDebugSSE"], produces=["text/event-stream;charset=UTF-8"])
    fun bookSourceDebugSSE(@RequestParam("bookSourceUrl") bookSourceUrl:String?, @RequestParam("keyword") keyword:String?, response: HttpServletResponse)= runBlocking{
        response.setContentType("text/event-stream")
        response.setCharacterEncoding("UTF-8")
        response.setStatus(200)
        if (bookSourceUrl == null || bookSourceUrl.isBlank()){
            response.getWriter().write("event: error\n")
            response.getWriter().write("data: " + Gson().toJson(JsonResponse(false,"书源连接不存在")) + "\n\n")
            response.getWriter().flush();
            return@runBlocking
        }
        if (keyword == null || keyword.isBlank()){
            response.getWriter().write("event: error\n")
            response.getWriter().write("data: " + Gson().toJson(JsonResponse(false,"请输入搜索关键词")) + "\n\n")
            response.getWriter().flush();
            return@runBlocking
        }
        var bookSource=bookSourcemapper.getBookSource(bookSourceUrl!!)
        if (bookSource == null){
            response.getWriter().write("event: error\n")
            response.getWriter().write("data: " + Gson().toJson(JsonResponse(false,"未配置书源")) + "\n\n")
            response.getWriter().flush();
            return@runBlocking
        }

        val bookSourceString=bookSource.json?:""

        //logger.info("bookSourceDebugSSE bookSource: {} keyword: {}", bookSourceString, keyword)

        val debugger = Debugger { msg ->
            response.getWriter().write("data: " + Gson().toJson(mapOf("msg" to msg)) + "\n\n")
            logger.info("data: " + Gson().toJson(mapOf("msg" to msg)) + "\n\n")
            response.getWriter().flush();
        }

        val webBook = WebBook(bookSourceString)
        debugger.startDebug(webBook, keyword)

        response.getWriter().write("event: end\n")
        response.getWriter().write("data: " + Gson().toJson(mapOf("end" to true)) + "\n\n")
        response.getWriter().flush()
    }

    fun addorupdate(bookSource: Booksource) = run{
        var insert = 0
        var update = 0
        var source=BookSource().jsontomodel(bookSource)
        bookSourcemapper.getBookSource(bookSource.bookSourceUrl).let {
            if (it != null){
                source.enabled=it.enabled
                update=insert+bookSourcemapper.updateById(source)
            }else{
                bookSource.enabled=true
                insert=update+bookSourcemapper.insert(source)
            }
        }
        Pair(insert, update)
    }
}