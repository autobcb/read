package web.controller.api

import book.WBook.Debugger
import book.webBook.WBook
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.net.annotation.ServerEndpoint
import org.noear.solon.net.websocket.WebSocket
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import web.model.BaseSource
import web.response.JsonResponse
import web.service.BookSourceService
import web.service.UserBookSourceService
import java.io.IOException

@Controller
@ServerEndpoint("$routepath/debug")
open  class DebugWebSocket : BaseDebug() {



    @Inject
    lateinit var bookSourceService: BookSourceService


    @Inject
    lateinit var userBookSourceService: UserBookSourceService


    override val logger: Logger = LoggerFactory.getLogger(DebugWebSocket::class.java)

    @Throws(IOException::class)
    override fun onMessage(socket: WebSocket, text: String): Unit = runBlocking{
        val accessToken: String = socket.param("id")
        val user=getuserbytocken(accessToken)
        if (user == null){
            socket.send("event: error\n")
            socket.send(Gson().toJson(JsonResponse(false,"user不存在")) + "\n\n")
            socket.close()
            return@runBlocking
        }
        val msg=Gson().fromJson(text, DebugMsg::class.java)
        if (msg.url == null || msg.url!!.isBlank()){
            socket.send("event: error\n")
            socket.send(Gson().toJson(JsonResponse(false,"书源连接不存在")) + "\n\n")
            socket.close()
            return@runBlocking
        }
        if (msg.key == null || msg.key!!.isBlank()){
            socket.send("event: error\n")
            socket.send(  Gson().toJson(JsonResponse(false,"请输入搜索关键词")) + "\n\n")
            socket.close()
            return@runBlocking
        }
        val bookSource: BaseSource?= if(user.source == 2){
            userBookSourceService.getBookSource(msg.url!!,user.id!!)?.toBaseSource()
        }else{
            bookSourceService.getBookSource(msg.url!!)?.toBaseSource()
        }
        if (bookSource == null){
            socket.send("event: error\n")
            socket.send(Gson().toJson(JsonResponse(false,"未配置书源")) + "\n\n")
            socket.close()
            return@runBlocking
        }
        val debugger = Debugger { msg1 ->
            socket.send( Gson().toJson(mapOf("msg" to msg1)) + "\n\n")
            logger.info( Gson().toJson(mapOf("msg" to msg1)) + "\n\n")
        }
        runCatching {
            val webBook = WBook(bookSource.json , user.id!!, accessToken, true)
            debugger.startDebug(webBook, msg.key!!)
        }
        //socket.send("event: end\n")
        //socket.send( Gson().toJson(mapOf("end" to true)) + "\n\n")
        socket.close()
    }


}

