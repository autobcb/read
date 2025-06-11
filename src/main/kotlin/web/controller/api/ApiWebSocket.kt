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
import web.service.UsersService
import web.service.UsertockenService
import web.util.ResponseManager
import java.io.IOException


@Controller
@ServerEndpoint("$routepath/ws")
class ApiWebSocket : SimpleWebSocketListener() {

    companion object{
        val logger: Logger = LoggerFactory.getLogger(ApiWebSocket::class.java)
        private var ma:MutableMap<String,WebSocket> = mutableMapOf()
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


        suspend fun add(key:String, value:WebSocket){
            mutex.withLock {
                logger.info("WebSocket Adding $key")
                ma[key]=value
            }
        }
        suspend fun remove(key:String){
            mutex.withLock {
                ma.remove( key)
            }
        }
        fun get(key:String):WebSocket?{
            return ma[key]
        }
    }

    @Inject
    lateinit var usersService: UsersService

    @Inject
    lateinit var usertockenService: UsertockenService


    override fun onOpen(socket: WebSocket) {
        val accessToken: String = socket.param("id")
        logger.info("websocket Open $accessToken")
        if (accessToken.isBlank()) {
            socket.close()
            return
        }

        val tocken=usertockenService.getUsertocken(accessToken)
        if (tocken == null) {
            logger.info("websocket tocken is null")
            socket.send(Gson().toJson(ToastMessage(msg = "logout", str="logout" )))
            socket.close()
            return
        }

        val user=usersService.getUser(tocken.userid)
        if (user == null) {
            logger.info("websocket user is null")
            socket.send(Gson().toJson(ToastMessage(msg = "logout", str="logout" )))
            socket.close()
            return
        }
        runBlocking {add(accessToken,socket)}

    }

    @Throws(IOException::class)
    override fun onMessage(socket: WebSocket, text: String) {
        socket.send("我收到了：$text")
    }

    override  fun onClose(socket: WebSocket?){
        super.onClose(socket)
         runBlocking {
             remove(socket!!.param("id"))
        }
    }
}