package web.notification


import org.slf4j.LoggerFactory
import web.model.Users


object Book:NotifyBase() {
    override val  logger = LoggerFactory.getLogger(Book::class.java)

    fun sendNotification(user: Users) {
        asyncSafeExecute{
           sendNotification(user,"bookmd5")
        }
    }

}