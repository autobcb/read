package web.controller.api

import book.app.ToastMessage
import com.google.gson.Gson
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeoutOrNull
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.net.annotation.ServerEndpoint
import org.noear.solon.net.websocket.WebSocket
import org.noear.solon.net.websocket.listener.SimpleWebSocketListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import web.mapper.UsersMapper
import web.mapper.UsertockenMapper
import web.util.ResponseManager
import web.util.hash.Md5
import java.io.IOException
import kotlin.concurrent.thread


@Controller
@ServerEndpoint("$routepath/ws")
class ApiWebSocket : SimpleWebSocketListener() {

    companion object{
        val logger: Logger = LoggerFactory.getLogger(ApiWebSocket::class.java)
        private var ma:MutableMap<String,WebSocketMap> = mutableMapOf()
        private val mutex = Mutex()

        suspend fun  WaitForResponse(correlationId :String,timeout: Long = 120000):String?= coroutineScope{
            val deferredResult = ResponseManager.registerRequest(correlationId)
            try {
                logger.info("Wait for $correlationId")
                withTimeoutOrNull(timeout) {
                    deferredResult.await()
                }.also { result ->
                    if (result == null) {
                        logger.info("Timeout reached for $correlationId")
                    }
                }
            }catch (e:Exception){
                logger.info("Error for $correlationId",e)
                null
            }.also {
                ResponseManager.cleanupExpiredRequest(correlationId)
            }
        }


        suspend fun add(key:String, value:WebSocket,userid:String){
            mutex.withLock {
                logger.info("WebSocket Adding $key")
                ma[key]=WebSocketMap(value,userid)
            }
        }
        suspend fun remove(key:String){
            mutex.withLock {
                ma.remove( key)
            }
        }
        fun get(key:String):WebSocket?{
            return ma[key]?.ws
        }

        suspend fun getByuserids(userids: List<String>):List<WebSocket>{
            val list = mutableListOf<WebSocket>()
            mutex.withLock {
                for (m in ma){
                    if(userids.contains(m.value.userid)){
                        list.add(m.value.ws)
                    }
                }
            }
            return list
        }

        suspend fun getByuserid(userid:String):List<WebSocket>{
            val list = mutableListOf<WebSocket>()
            mutex.withLock {
                for (m in ma){
                    if(m.value.userid == userid){
                        list.add(m.value.ws)
                    }
                }
            }
            return list
        }


        suspend fun colseByuserid(userid:String){
            mutex.withLock {
                for (m in ma){
                    if(m.value.userid == userid){
                        thread {
                            runCatching {
                                m.value.ws.close()
                            }
                        }
                    }
                }
            }
        }


        suspend fun getByuserid(userid:String,tocken: String):List<WebSocket>{
            val list = mutableListOf<WebSocket>()
            mutex.withLock {
                for (m in ma){
                    if(m.key != tocken && m.value.userid == userid){
                        list.add(m.value.ws)
                    }
                }
            }
            return list
        }
    }

    @Inject
    lateinit var usersMapper: UsersMapper

    @Inject
    lateinit var usertockenMapper: UsertockenMapper


    override fun onOpen(socket: WebSocket) {
        val accessToken: String = socket.param("id")
        logger.info("websocket Open $accessToken")
        if (accessToken.isBlank()) {
            socket.close()
            return
        }

        val tocken=usertockenMapper.getUsertocken(accessToken)
        if (tocken == null) {
            logger.info("websocket tocken is null")
            socket.send(Gson().toJson(ToastMessage(msg = "logout", str="logout" )))
            socket.close()
            return
        }

        val user= tocken.userid?.let { usersMapper.getUser(it) }
        if (user == null) {
            logger.info("websocket user is null")
            socket.send(Gson().toJson(ToastMessage(msg = "logout", str="logout" )))
            socket.close()
            return
        }
        if((user.sourcemd5?:"").isEmpty() && user.source != 2){
            user.sourcemd5=Md5(System.currentTimeMillis().toString())
        }
        if((user.rssmd5?:"").isEmpty() && user.source != 2){
            user.rssmd5=Md5(System.currentTimeMillis().toString())
        }
        runBlocking {add(accessToken,socket,user.id!!)}
        runCatching {
            socket.send(Gson().toJson(Md5Message(
                msg = "init",
                md5 = "",
            )))
            socket.send(Gson().toJson(Md5Message(
                msg = "source",
                md5 = user.source.let { if(it == 2 || it == 1) "1" else "0" },
            )))
            //将md5发送至客户端
            socket.send(Gson().toJson(Md5Message(
                msg = "bookmd5",
                md5 =user.bookmd5?:"",
            )))
            socket.send(Gson().toJson(Md5Message(
                msg = "sourcemd5",
                md5 =(user.sourcemd5?:"")+"${user.source}" ,
            )))
            socket.send(Gson().toJson(Md5Message(
                msg = "rssmd5",
                md5 =(user.rssmd5?:"")+"${user.source}" ,
            )))
            socket.send(Gson().toJson(Md5Message(
                msg = "tssmd5",
                md5 =user.tssmd5?:"",
            )))
            socket.send(Gson().toJson(Md5Message(
                msg = "replacemd5",
                md5 =user.replacemd5?:"",
            )))
            socket.send(Gson().toJson(Md5Message(
                msg = "groundmd5",
                md5 =user.groundmd5?:"",
            )))
        }

    }

    @Throws(IOException::class)
    override fun onMessage(socket: WebSocket, text: String) {
        //logger.info("我收到了：$text")
        //socket.send("我收到了：$text")
    }

    override  fun onClose(socket: WebSocket?){
        super.onClose(socket)
         runBlocking {
             remove(socket!!.param("id"))
        }
    }
}

class WebSocketMap(
    var ws:WebSocket,
    var userid:String,
)

class Md5Message(
    var msg: String,
    var md5: String,
    var bookurl: String? = null
)