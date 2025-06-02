package web.controller.api

import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Inject
import org.noear.solon.net.websocket.WebSocket
import org.noear.solon.net.websocket.listener.SimpleWebSocketListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import web.mapper.UsersMapper
import web.mapper.UsertockenMapper
import web.model.Users
import web.service.UsersService
import web.service.UsertockenService

open class BaseDebug: SimpleWebSocketListener()  {
    @Inject
    lateinit var usersService: UsersService

    @Inject
    lateinit var usertockenService: UsertockenService

    open val logger: Logger = LoggerFactory.getLogger(BaseDebug::class.java)

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
            socket.close()
            return
        }

        val user=usersService.getUser(tocken.userid)
        if (user == null) {
            logger.info("websocket user is null")
            socket.close()
            return
        }
    }

    fun getuserbytocken(accessToken:String?): Users?{
        if (accessToken.isNullOrBlank()) {
            return null
        }
        val tocken= usertockenService.getUsertocken(accessToken) ?: return null
        val user= usersService.getUser(tocken.userid) ?: return null
        return user
    }
}

class DebugMsg{
    var url:String?=null
    var key:String?=null
}
