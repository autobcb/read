package web.notification

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import web.controller.api.ApiWebSocket
import web.controller.api.Md5Message
import web.model.Users
import web.util.hash.Md5
import web.util.mapper.mapper

open class  NotifyBase {

    open val logger: Logger  = LoggerFactory.getLogger(this::class.java)

    protected fun asyncSafeExecute(r: suspend ()->Unit = {}): Deferred<Unit> {
        // 使用协程作用域和IO调度器，避免手动创建线程
        return CoroutineScope(Dispatchers.IO).async {
            try {
                r()
                logger.debug("Saferun execution completed successfully")
            } catch (e: Exception) {
                logger.error("Error in saferun execution", e)
                // 可以根据需要决定是否重新抛出异常
                // throw e
            }
        }
    }

    protected suspend fun sendSourceNotification(type: String){
        val  md5=Md5(System.currentTimeMillis().toString())
        when(type){
            "sourcemd5" ->{
                mapper.get().usersMapper.updatesourcemd52( md5)
            }
            "rssmd5" ->{
                mapper.get().usersMapper.updaterssmd52( md5)
            }
            else ->  throw IllegalArgumentException("sendSourceNotification Unrecognized type: $type")
        }
        val userids=mutableListOf<String>()
        mapper.get().usersMapper.getSourceUser().forEach {
            userids.add(it.id?:"")
        }
        ApiWebSocket.getByuserids(userids).forEach {
            runCatching {
                it.send(Gson().toJson(Md5Message(
                    msg = type,
                    md5 =md5,
                )))
            }
        }
    }


    protected  suspend fun sendNotification(user: Users, type: String,tocken:String?=null,bookurl:String?=null){
        val  md5=Md5(System.currentTimeMillis().toString())
        when(type){
            "bookmd5" ->{
                mapper.get().usersMapper.updatebookmd5(user.id!!, md5)
            }
            "read" ->{
                mapper.get().usersMapper.updatebookmd5(user.id!!, md5)
            }
            "replacemd5" ->{
                mapper.get().usersMapper.updatereplacemd5(user.id!!, md5)
            }
            "tssmd5" ->{
                mapper.get().usersMapper.updatettsmd5(user.id!!, md5)
            }
            "sourcemd5" ->{
                mapper.get().usersMapper.updatesourcemd5(user.id!!, md5)
            }
            "rssmd5" ->{
                mapper.get().usersMapper.updaterssmd5(user.id!!, md5)
            }
            "groundmd5" -> {
                mapper.get().usersMapper.updategroundmd5(user.id!!, md5)
            }
            else ->  throw IllegalArgumentException("sendNotification Unrecognized type: $type")
        }

        (tocken?.let { ApiWebSocket.getByuserid(user.id!!,tocken) }?:ApiWebSocket.getByuserid(user.id!!)).forEach {
            runCatching {
                it.send(Gson().toJson(Md5Message(
                    msg = type,
                    bookurl = bookurl,
                    md5 =md5,
                )))
            }
        }
    }
}
