package web.notification


import org.slf4j.LoggerFactory
import web.model.Users

object ReplaceRule :NotifyBase() {

    override val  logger = LoggerFactory.getLogger(ReplaceRule::class.java)

    fun sendNotification(user: Users){
        asyncSafeExecute{
            sendNotification(user,"replacemd5")
        }
    }

}