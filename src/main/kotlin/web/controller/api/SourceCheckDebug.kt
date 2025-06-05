package web.controller.api



import book.model.Book
import book.model.BookChapter
import book.model.BookSource
import book.model.SearchBook
import book.util.GSON
import book.util.fromJsonArray
import book.webBook.WBook
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.net.annotation.ServerEndpoint
import org.noear.solon.net.websocket.WebSocket
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import web.model.BaseSource
import web.model.Users
import web.service.BookSourceService
import web.service.UserBookSourceService
import java.io.IOException

@Controller
@ServerEndpoint("$routepath/checkdebug")
class SourceCheckDebug: BaseDebug() {

    @Inject
    lateinit var bookSourceService: BookSourceService


    @Inject
    lateinit var userBookSourceService: UserBookSourceService


    override val logger: Logger = LoggerFactory.getLogger(DebugWebSocket::class.java)

    private val  searchkey="系统"

    companion object {
        private var ma:MutableMap<String,WebSocket> = mutableMapOf()
        private val mutex = Mutex()
    }


    override fun onOpen(socket: WebSocket) {
        val accessToken: String = socket.param("id")
        ApiWebSocket.logger.info("websocket Open $accessToken")
        if (accessToken.isBlank()) {
            socket.close()
            return
        }
        mutex.apply {
            ma[accessToken] = socket
        }
    }

    @Throws(IOException::class)
    override fun onMessage(socket: WebSocket, text: String): Unit = runBlocking{
        val accessToken: String = socket.param("id")
        val user=getuserbytocken(accessToken)
        if (user == null){
            socket.close()
            return@runBlocking
        }
        mutex.apply {
            ma[accessToken] = socket
        }
        val ids:List<String> = GSON.fromJsonArray<String>(text).getOrNull()?: listOf()
        val semaphore = Semaphore(5)
        val jobs = mutableListOf<Job>()
        for (id in ids){
            if(!isopen(accessToken)) break
            launch{
                semaphore.acquire()
                kotlin.runCatching { check(user,accessToken,searchkey,id) }
                semaphore.release()
            }.let {
                jobs.add(it)
            }
        }
        jobs.joinAll()
        logger.info("书源检验结束:$accessToken")
        getsocket(accessToken).send(Gson().toJson(ErrorMsg().apply {
            url=""
            msg="close"
        }))
    }

    private  fun isopen(accessToken: String): Boolean{
        var z=false
        mutex.apply {
            if(ma[accessToken]  != null){
                z=true
            }
        }
        return z
    }

    private fun  getsocket(accessToken: String): WebSocket{
        return  ma[accessToken]!!
    }


    override fun onClose(socket: WebSocket?) {
        val accessToken: String = socket!!.param("id")
        mutex.apply {
            ma.remove(accessToken)
        }
    }

    fun check(user:Users,accessToken:String,key:String,id:String) = runBlocking {
        if (user.source == 0) return@runBlocking
        if(!isopen(accessToken)) return@runBlocking
        val source: BaseSource? = if(user.source == 2){
            userBookSourceService.getBookSource(id,user.id!!)?.toBaseSource()
        }else{
            bookSourceService.getBookSource(id)?.toBaseSource()
        }
        if(source != null){
            kotlin.runCatching {
                if(!isopen(accessToken)) return@runBlocking
                val webBook = WBook(source.json,user.id!!,accessToken, false)
                var list:List<SearchBook> = listOf()
                kotlin.runCatching {
                    val s= BookSource.fromJson(source.json).getOrNull() ?: BookSource()
                    if(s.ruleSearch == null || s.ruleSearch!!.checkKeyWord.isNullOrEmpty()){
                        list=webBook.searchBook(key)
                    }else{
                        list=webBook.searchBook(s.ruleSearch!!.checkKeyWord!!)
                    }
                }.onFailure {
                    getsocket(accessToken).send(Gson().toJson(ErrorMsg().apply {
                        url=id
                        msg="搜索失败:${it.message}"
                    }))
                    return@runBlocking
                }
                if(list.isEmpty()){
                    getsocket(accessToken).send(Gson().toJson(ErrorMsg().apply {
                        url=id
                        msg="搜索结果为空"
                    }))
                    return@runBlocking
                }
                if(!isopen(accessToken)) return@runBlocking
                var book: Book?=null
                kotlin.runCatching {
                    book=webBook.getBookInfo(list[0].bookUrl)
                }
                if(!isopen(accessToken)) return@runBlocking
                var chapters:List<BookChapter> = listOf()
                kotlin.runCatching {
                    chapters = if(book!=null){
                        webBook.getChapterList(book!!)
                    }else{
                        webBook.getChapterList(list[0].toBook())
                    }
                }.onFailure {
                    getsocket(accessToken).send(Gson().toJson(ErrorMsg().apply {
                        url=id
                        msg="目录获取失败:${it.message}"
                    }))
                    return@runBlocking
                }

                if(chapters.isEmpty()){
                    getsocket(accessToken).send(Gson().toJson(ErrorMsg().apply {
                        url=id
                        msg="目录为空"
                    }))
                    return@runBlocking
                }
                var chapter:BookChapter?=null
                var nexturl=""
                for(c in chapters){
                    if(chapter == null){
                        if(!c.isVolume){
                            chapter=c
                        }
                    }else{
                        if(!c.isVolume){
                            nexturl=c.url
                            break
                        }
                    }

                }
                if(chapter!=null){
                    if(!isopen(accessToken)) return@runBlocking
                    kotlin.runCatching {
                        if(book!=null){
                            webBook.getBookContent(book!!,chapter,nexturl)
                        }else{
                            webBook.getBookContent(list[0].toBook(),chapter,nexturl)
                        }
                    }.onFailure {
                        getsocket(accessToken).send(Gson().toJson(ErrorMsg().apply {
                            url=id
                            msg="正文获取失败:${it.message}"
                        }))
                        return@runBlocking
                    }
                }
            }.onFailure {
                getsocket(accessToken).send(Gson().toJson(ErrorMsg().apply {
                    url=id
                    msg=it.message ?: "error"
                }))
                return@runBlocking
            }
        }

    }

    class ErrorMsg{
        var url:String?=null
        var msg:String?=null
    }
}