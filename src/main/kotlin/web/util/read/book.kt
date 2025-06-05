package web.util.read

import book.model.Book
import book.model.BookChapter
import book.webBook.WBook
import book.webBook.exception.ConcurrentException
import book.webBook.localBook.LocalBook
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import web.controller.api.ReadController
import web.controller.api.ReadController.Companion.getBookbycache
import web.controller.api.ReadController.Companion.setBookbycache
import web.model.BaseSource
import web.model.Booklist
import web.util.mapper.mapper
import kotlin.random.Random

fun updatebook(book: Booklist, source: BaseSource,userid:String) = runBlocking{
    val list= getlist(book.bookUrl!! ,source,userid,"")
    if (list.isNotEmpty()){
        val lastCheckTime=System.currentTimeMillis()
        val lastCheckCount=list.size
        if (list.size != book.totalChapterNum ){
            val totalChapterNum=list.size
            val latestChapterTitle=list[list.size-1].title
            val latestChapterTime=System.currentTimeMillis()
            ReadController.removeChapterListbycache(book.bookUrl?:"",userid)
            ReadController.setChapterListbycache(book.bookUrl?:"",list,userid)
            mapper.get().booklistService.updatetime(book.id!!,userid,latestChapterTitle,latestChapterTime,lastCheckTime,lastCheckCount, totalChapterNum )
            mapper.get().bookCacheService.getCache(book.userid!!,book.id!!).let {
                if(it!=null){
                    mapper.get().bookCacheService.updatetime(it.id!!,userid,totalChapterNum)
                }
            }
        }else{
            mapper.get().booklistService.updatetimefail(book.id!!,userid,lastCheckTime,lastCheckCount)
        }
    }
}

fun getlist(url:String):List<BookChapter>{
    val book = Book.initLocalBook(url, url, "")
    val chapters = LocalBook.getChapterList(book)
    return  chapters
}

suspend fun getlist(url:String, source: BaseSource,userid:String,accessToken :String):List<BookChapter>{
    val webBook = WBook(source.json,userid,accessToken, false)
    val book= getBookbycache(url,userid).let {
        it ?: getbook(webBook,url).also { it1 -> setBookbycache(url,it1,userid) }
    }
    return getChapterList(webBook,book)
}

suspend fun getChapterList(webBook: WBook,book: Book):List<BookChapter>{
    var re:List<BookChapter> = listOf()
    runCatching {
        re=webBook.getChapterList(book)
    }.onFailure {
        if(it is ConcurrentException){
            println("getlist并发原因？？？？${it.message}")
            val randomNumber = Random.nextInt(1000, 500).toLong()
            delay(randomNumber)
            return getChapterList(webBook,book)
        }
        throw it
    }
    return re
}

suspend fun  getbook(webBook:WBook, url:String):Book{
    var book: Book?=null
    runCatching {
        book=webBook.getBookInfo(url,canReName = true)
    }.onFailure {
        if(it is ConcurrentException){
            println("getlist并发原因？？？？${it.message}")
            val randomNumber = Random.nextInt(1000, 500).toLong()
            delay(randomNumber)
            return getbook(webBook,url)
        }
        throw it
    }
    return book!!
}
