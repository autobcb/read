package web.notification

import org.slf4j.LoggerFactory
import web.model.Users

object Ground:NotifyBase() {
    override val  logger = LoggerFactory.getLogger(Ground::class.java)

    fun sendNotification(user: Users) {
        asyncSafeExecute{
            sendNotification(user,"groundmd5")
        }
    }

}