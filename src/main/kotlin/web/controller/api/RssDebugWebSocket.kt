package web.controller.api

import book.WBook.Debugger
import book.model.RssSource
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.net.annotation.ServerEndpoint
import org.noear.solon.net.websocket.WebSocket
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import web.response.JsonResponse
import web.service.RssSourceService
import web.service.UserRssSourceService
import java.io.IOException

@Controller
@ServerEndpoint("$routepath/rssdebug")
class RssDebugWebSocket : BaseDebug() {


    @Inject
    lateinit var rssSourceService: RssSourceService


    @Inject
    lateinit var userRssSourceService: UserRssSourceService

    override  val logger:Logger= LoggerFactory.getLogger(RssDebugWebSocket::class.java)



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
        val msg= Gson().fromJson(text, DebugMsg::class.java)
        if (msg.url == null || msg.url!!.isBlank()){
            socket.send("event: error\n")
            socket.send(Gson().toJson(JsonResponse(false,"订阅源连接不存在")) + "\n\n")
            socket.close()
            return@runBlocking
        }
        val rss=if(user.source == 2){
            userRssSourceService.getRssSource(msg.url?:"",user.id!!)?.toBaseSource()
        }else{
            rssSourceService.getRssSource(msg.url?:"")?.toBaseSource()
        }
        if (rss == null){
            socket.send("event: error\n")
            socket.send(Gson().toJson(JsonResponse(false,"未配置订阅源")) + "\n\n")
            socket.close()
            return@runBlocking
        }
        val debugger = Debugger { msg1 ->
            socket.send( Gson().toJson(mapOf("msg" to msg1)) + "\n\n")
            logger.info( Gson().toJson(mapOf("msg" to msg1)) + "\n\n")
        }
        runCatching {
            val rssSource= RssSource.fromJson(rss.json?:"")
            rssSource.userid=user.id
            rssSource.usertocken=accessToken
            debugger.startDebug(rssSource)
        }
        socket.close()
    }


}