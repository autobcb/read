package web.notification


import org.slf4j.LoggerFactory
import web.model.Users

object TTs:NotifyBase() {

    override val  logger = LoggerFactory.getLogger(TTs::class.java)

    fun sendNotification(user: Users){
        asyncSafeExecute{
            sendNotification(user,"tssmd5")
        }
    }

}