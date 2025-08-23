package web.util.read

import book.app.App
import book.webBook.WBook
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.slf4j.LoggerFactory
import web.controller.api.ReadController.Companion.getChapterListbycache
import web.controller.api.ReadController.Companion.setBookContentbycache
import web.model.BaseSource
import web.model.Users
import web.util.mapper.mapper

object  BookContent {
    private var ma:MutableMap<String, Deferred<String>> = mutableMapOf()
    private val mutex = Mutex()
    private val logger = LoggerFactory.getLogger(BookContent::class.java)

    fun  getbookcontent(accessToken:String, user: Users, source: BaseSource, url:String, index:Int, type:Int):String= runBlocking{
        val key="url:$url,index:$index,type:$type,${user.id}"
        var deferred: Deferred<String>?
        deferred=ma[key]
        if(deferred == null) {
            mutex.withLock {
                deferred=ma[key] ?:async{ getBookContent(accessToken,user,source,url,index) }
                ma[key]= deferred
            }
        }
        val str=runCatching {
           deferred!!.await().also { if( type != 1) setBookContentbycache(url,it,index,user.id!!) }
        }.onSuccess {
            logger.info(key+"完成")
        }.onFailure {
            logger.error("正文获取失败:"+it.message)
            App.log("正文获取失败:"+it.message,accessToken)
            it.printStackTrace()
        }.getOrElse { "" }
        remove(key)
        str
    }

    @Suppress("DeferredResultUnused")
    private suspend fun remove(key:String){
        mutex.withLock {
            ma.remove(key)
        }
    }

    private suspend fun getBookContent(accessToken:String,user: Users, source:BaseSource, url:String, index:Int):String {
        var (chapterlist,_)= getChapterListbycache(url,user.id!!)
        if(chapterlist == null || index >= chapterlist.size ){
            chapterlist= getlist(url,source,user,accessToken)
        }
        val webBook = WBook(source.json,user.id!!,accessToken, false)
        val book = getbook(accessToken,user,source,url)

        val systembook=mapper.get().booklistMapper.getbook(user.id!!,url)
        if(systembook!=null){
            book.durChapterIndex=systembook.durChapterIndex?:0
        }else{
            runCatching {
                val durChapterIndex=mapper.get().cacheService.get("indexuerid:${user.id},bookurl:${url}",Int::class.java)
                book.durChapterIndex=durChapterIndex
            }
        }
        val nexturl=if(index+1 < chapterlist.size) chapterlist[index+1].url else ""
        return webBook.getBookContent(book,chapterlist[index],nexturl)
    }


}