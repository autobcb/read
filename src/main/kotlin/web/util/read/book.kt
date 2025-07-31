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
import web.model.Users
import web.util.mapper.mapper
import kotlin.random.Random

fun updatebook(book: Booklist, source: BaseSource,user: Users) = runBlocking{
    val userid=user.id!!
    val list= getlist(book.bookUrl!! ,source,user,"")
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

fun getlist(url:String, source: BaseSource,user: Users,accessToken :String):List<BookChapter>{
    return BookCatalog.getChapterlist(accessToken,user,source,url)
}

fun getbook(accessToken: String, user: Users, source: BaseSource, url: String): Book{
    val book = (getBookbycache(url,user.id!!)?: BookInfo.getbookinfo(accessToken,user,source,url))?: throw Exception("书本获取失败")
    return book
}