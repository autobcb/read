package web.notification

import org.slf4j.LoggerFactory
import web.model.Users

object Read:NotifyBase() {
    override val  logger = LoggerFactory.getLogger(Read::class.java)

    fun sendNotification(user: Users,tocken:String,bookurl:String){
        asyncSafeExecute{
            sendNotification(user,"read",tocken,bookurl)
        }
    }
}