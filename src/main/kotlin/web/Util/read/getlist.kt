package web.Util.read

import com.google.gson.Gson
import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import io.legado.app.model.localBook.LocalBook
import io.legado.app.model.webBook.WebBook
import org.springframework.stereotype.Component
import web.Model.BookSource
import web.Model.Booklist
import web.Util.model.ChapterList
import web.Util.cache.getcache
import web.Util.cache.setcache
import web.Util.mapper

fun getlist(url:String):List<BookChapter>{
    var cachetxt= getcache(url)
    if(cachetxt != null){
        var cache= Gson().fromJson<ChapterList>(cachetxt, ChapterList::class.java)
        return cache.bookChapters
    }
    val book = Book.initLocalBook(url, url, "")
    val chapters = LocalBook.getChapterList(book)
    var cache= ChapterList(book =book, bookChapters=chapters,source=null)
    val json= Gson().toJson(cache)
    setcache(url,json)
    return  chapters
}

suspend fun getlist(url:String, source: BookSource):List<BookChapter>{
    var cachetxt= getcache(url)
    if(cachetxt != null){
        var cache= Gson().fromJson<ChapterList>(cachetxt, ChapterList::class.java)
        if(System.currentTimeMillis()-cache.addtime < 60*60*1000){
            return cache.bookChapters
        }
    }
    var webBook = WebBook(source.json?:"", false)
    var re:List<BookChapter> = listOf()
    var book: Book?=null
    book=webBook.getBookInfo(url,canReName = true)
    re=webBook.getChapterList(book!!)
    var cache= ChapterList(book =book, bookChapters=re,source=source)
    val json= Gson().toJson(cache)
    setcache(url,json)
    return  re
}


suspend fun updatebook(book: Booklist, source: BookSource){
    var list= getlist(book.bookUrl ,source)
    if (list!=null){
        var lastCheckTime=System.currentTimeMillis()
        var lastCheckCount=list.size
        if (list.size != book.totalChapterNum ){
            var totalChapterNum=list.size
            var latestChapterTitle=list[list.size-1].title
            var latestChapterTime=System.currentTimeMillis()
            mapper.get().booklistMapper.updatetime(book.id,latestChapterTitle,latestChapterTime,lastCheckTime,lastCheckCount, totalChapterNum )
        }else{
            mapper.get().booklistMapper.updatetimefail(book.id,lastCheckTime,lastCheckCount)
        }
    }
}