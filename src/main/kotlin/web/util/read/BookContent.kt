package web.util.read

import book.app.App
import book.model.Book
import book.webBook.WBook
import book.webBook.exception.ConcurrentException

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.slf4j.LoggerFactory
import web.controller.api.ReadController.Companion.getBookbycache
import web.controller.api.ReadController.Companion.getChapterListbycache
import web.controller.api.ReadController.Companion.setBookContentbycache
import web.controller.api.ReadController.Companion.setBookbycache
import web.controller.api.ReadController.Companion.setChapterListbycache
import web.model.BaseSource
import web.model.Users
import web.util.mapper.mapper

object  BookContent {
    private var ma:MutableMap<String, Deferred<String>> = mutableMapOf()
    private val mutex = Mutex()
    private val logger = LoggerFactory.getLogger(BookContent::class.java)

    fun  getbookcontent(accessToken:String, user: Users, source: BaseSource, url:String, index:Int, type:Int):String= runBlocking{
        val key="url:$url,index:$index"
        var deferred: Deferred<String>?
        mutex.withLock {
            deferred=ma[key]
        }
        if(deferred == null) {
            mutex.withLock {
                deferred=async{ getBookContent(accessToken,user,source,url,index,type) }
                ma[key]= deferred!!
            }
        }
        runCatching {
           return@runBlocking deferred!!.await().also { logger.info(key+"完成");remove(key) }
        }.onFailure {
            App.log("正文获取失败:"+it.message,accessToken)
            it.printStackTrace()
        }
        logger.error(key+"失败")
        remove(key).let { "" }
    }

    @Suppress("DeferredResultUnused")
    private suspend fun remove(key:String){
        mutex.withLock {
            ma.remove(key)
        }
    }

    private suspend fun getBookContent(accessToken:String,user: Users, source:BaseSource, url:String, index:Int,type:Int):String {
     runCatching {
          var chapterlist= getChapterListbycache(url,user.id!!)
          if(chapterlist == null){
              chapterlist= getlist(url,source,user.id!!,accessToken).also{
                  setChapterListbycache(url,it,user.id!!)
              }
          }
         val webBook = WBook(source.json,user.id!!,accessToken, false)
         val book= getBookbycache(url,user.id!!).let { it1->
             it1 ?: getbook(webBook,url)!!.also { setBookbycache(url,it,user.id!!) }
          }
          // println("目录链接1 ${chapterlist[index].baseUrl}")
          //println("目录链接 ${chapterlist[index].url}")
         val systembook=mapper.get().booklistService.getbook(user.id!!,url)
          if(systembook!=null){
             // println("获取阅读进度1:${url},index:${systembook.durChapterIndex}")
              book.durChapterIndex=systembook.durChapterIndex?:0
          }else{
             runCatching {
                  val durChapterIndex=mapper.get().cacheService.get("indexuerid:${user.id},bookurl:${url}",Int::class.java)
                  //println("获取阅读进度:${url},index:${durChapterIndex}")
                  book.durChapterIndex=durChapterIndex
              }.getOrElse {
                  it.printStackTrace()
             }
          }
         val nexturl=if(index+1 < chapterlist.size) chapterlist[index+1].url else ""
          return webBook.getBookContent(book,chapterlist[index],nexturl).also { if( type != 1) setBookContentbycache(url,it,index,user.id!!) }
      }
      return  ""
    }

    private fun getbook(webBook: WBook, url:String): Book?= runBlocking{
        var book:Book?=null
        runCatching {
           book= webBook.getBookInfo(url,canReName = true)
        }.onFailure {
            if(it is ConcurrentException){
                logger.info("getbook 并发原因？？？？")
                delay(1000)
                book=getbook(webBook,url)
            }
        }
        book
    }
}