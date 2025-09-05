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
import kotlinx.coroutines.sync.withLock
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.net.annotation.ServerEndpoint
import org.noear.solon.net.websocket.WebSocket
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import web.mapper.BookSourceMapper
import web.mapper.UserBookSourceMapper
import web.model.BaseSource
import web.model.Users
import java.io.IOException

@Controller
@ServerEndpoint("$routepath/checkdebug")
class SourceCheckDebug: BaseDebug() {

    @Inject
    lateinit var bookSourceMapper: BookSourceMapper


    @Inject
    lateinit var userBookSourceMapper: UserBookSourceMapper


    override val logger: Logger = LoggerFactory.getLogger(DebugWebSocket::class.java)

    private val  searchkey="系统"

    companion object {
        private var ma:MutableMap<String,WebSocket> = mutableMapOf()
        private val mutex = Mutex()
    }


    override fun onOpen(socket: WebSocket) =runBlocking{
        val accessToken: String = socket.param("id")
        if (accessToken.isBlank()) {
            socket.close()
            return@runBlocking
        }
        val checkid: String = socket.param("checkid")
        if (checkid.isBlank()) {
            socket.close()
            return@runBlocking
        }
        mutex.withLock {
            ma[checkid] = socket
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
        if(user.Allowcheck != true){
            socket.send(Gson().toJson(ErrorMsg().apply {
                url="无权限"
                msg="close"
            }))
            socket.close()
            return@runBlocking
        }
        val checkid: String = socket.param("checkid")
        if (checkid.isBlank()) {
            socket.close()
            return@runBlocking
        }
        mutex.withLock {
            ma[checkid] = socket
        }
        val ids:List<String> = GSON.fromJsonArray<String>(text).getOrNull()?: listOf()
        val semaphore = Semaphore(10)
        val jobs = mutableListOf<Job>()
        var num=0
        for (id in ids){
            if(!isopen(checkid)) break
            launch{
                semaphore.acquire()
                kotlin.runCatching { check(checkid,user,accessToken,searchkey,id) }
                semaphore.release()
                if(isopen(checkid)) {
                    mutex.withLock {
                        num++
                        runCatching {
                            getsocket(checkid).send(Gson().toJson(ErrorMsg().apply {
                                url="已检验完成:$num,还剩:${ids.size - num}"
                                msg="msg"
                            }))
                        }
                    }
                }
            }.let {
                jobs.add(it)
            }
        }
        jobs.joinAll()
        logger.info("书源检验结束:$accessToken")
        getsocket(checkid).send(Gson().toJson(ErrorMsg().apply {
            url=""
            msg="close"
        }))
    }

    private  fun isopen(checkid: String) = runBlocking{
        var z=false
        mutex.withLock {
            if(ma[checkid]  != null){
                z=true
            }
        }
        z
    }

    private fun  getsocket(checkid: String): WebSocket{
        return  ma[checkid]!!
    }


    override fun onClose(socket: WebSocket?): Unit = runBlocking{
        val checkid: String = socket!!.param("checkid")
        mutex.withLock {
            ma.remove(checkid)
        }
    }

    fun check(checkid:String,user:Users,accessToken:String,key:String,id:String) = runBlocking {
        if (user.source == 0) return@runBlocking
        if(!isopen(checkid)) return@runBlocking
        val source: BaseSource? = if(user.source == 2){
            userBookSourceMapper.getBookSource(id,user.id!!)?.toBaseSource()
        }else{
            bookSourceMapper.getBookSource(id)?.toBaseSource()
        }
        if(source != null){
            kotlin.runCatching {
                if(!isopen(checkid)) return@runBlocking
                val webBook = WBook(source.json,user.id!!,accessToken, false)
                var list:List<SearchBook> = listOf()
                kotlin.runCatching {
                    val s= BookSource.fromJson(source.json).getOrNull() ?: BookSource()
                    list = if(s.ruleSearch == null || s.ruleSearch!!.checkKeyWord.isNullOrEmpty()){
                        webBook.searchBook(key)
                    }else{
                        webBook.searchBook(s.ruleSearch!!.checkKeyWord!!)
                    }
                }.onFailure {
                    getsocket(checkid).send(Gson().toJson(ErrorMsg().apply {
                        url=id
                        msg="搜索失败:${it.message}"
                    }))
                    return@runBlocking
                }
                if(list.isEmpty()){
                    getsocket(checkid).send(Gson().toJson(ErrorMsg().apply {
                        url=id
                        msg="搜索结果为空"
                    }))
                    return@runBlocking
                }
                if(!isopen(checkid)) return@runBlocking
                var book: Book?=null
                kotlin.runCatching {
                    book=webBook.getBookInfo(list[0].bookUrl)
                }
                if(!isopen(checkid)) return@runBlocking
                var chapters:List<BookChapter> = listOf()
                kotlin.runCatching {
                    chapters = if(book!=null){
                        webBook.getChapterList(book)
                    }else{
                        webBook.getChapterList(list[0].toBook())
                    }
                }.onFailure {
                    getsocket(checkid).send(Gson().toJson(ErrorMsg().apply {
                        url=id
                        msg="目录获取失败:${it.message}"
                    }))
                    return@runBlocking
                }

                if(chapters.isEmpty()){
                    getsocket(checkid).send(Gson().toJson(ErrorMsg().apply {
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
                    if(!isopen(checkid)) return@runBlocking
                    kotlin.runCatching {
                        if(book!=null){
                            webBook.getBookContent(book,chapter,nexturl)
                        }else{
                            webBook.getBookContent(list[0].toBook(),chapter,nexturl)
                        }
                    }.onFailure {
                        getsocket(checkid).send(Gson().toJson(ErrorMsg().apply {
                            url=id
                            msg="正文获取失败:${it.message}"
                        }))
                        return@runBlocking
                    }
                }
            }.onFailure {
                getsocket(checkid).send(Gson().toJson(ErrorMsg().apply {
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