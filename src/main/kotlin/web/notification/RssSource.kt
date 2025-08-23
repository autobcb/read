package web.notification

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import web.model.Users

object RssSource : NotifyBase(){

    override val logger: Logger  = LoggerFactory.getLogger(RssSource::class.java)


    //独立书源才需要传user,非独立书源请勿传递
    fun sendNotification(user: Users? = null){
        asyncSafeExecute{
            if (user == null) {
                sendSourceNotification("rssmd5")
            }else{
                sendNotification(user,"rssmd5")
            }
        }
    }

}