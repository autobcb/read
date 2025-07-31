package web.util.read

import book.app.App
import book.model.Book
import book.webBook.WBook
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.slf4j.LoggerFactory
import web.controller.api.ReadController.Companion.setBookbycache
import web.model.BaseSource
import web.model.Users

object BookInfo {
    private var ma:MutableMap<String, Deferred<Book?>> = mutableMapOf()
    private val mutex = Mutex()
    private val logger = LoggerFactory.getLogger(BookInfo::class.java)

    fun  getbookinfo(accessToken:String, user: Users, source: BaseSource, url:String):Book?= runBlocking{
        val key="url:$url,${user.id}"
        var deferred: Deferred<Book?>?
        deferred= ma[key]
        if(deferred == null) {
           mutex.withLock {
                deferred=ma[key] ?:async{ getBookinfo(accessToken,user,source,url) }
                ma[key]=deferred
            }
        }
        val book=runCatching {
             deferred!!.await().also {  if (it != null){setBookbycache(url,it,user.id!!)} }
        }.onSuccess {
            logger.info(key+"完成")
        }.onFailure {
            logger.error("书本详情获取失败:"+it.message)
            App.log("书本详情获取失败:"+it.message,accessToken)
            it.printStackTrace()
        }.getOrNull()
        remove(key)
        book
    }



    private suspend fun getBookinfo(accessToken:String,user: Users, source:BaseSource, url:String): Book? {
        val webBook = WBook(source.json,user.id!!,accessToken, false)
        return webBook.getBookInfo(url,canReName = true)
    }

    @Suppress("DeferredResultUnused")
    private suspend fun remove(key:String){
        mutex.withLock {
          ma.remove(key)
        }
    }
}