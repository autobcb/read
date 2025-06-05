package web.controller.api

import book.model.Book
import book.webBook.localBook.LocalBook
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.core.handle.UploadedFile
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.mapper.BooklistMapper
import web.model.Booklist
import web.response.*
import web.service.BooklistService
import web.util.cache.getlocalpath
import java.io.File
import java.net.URLDecoder
import kotlin.concurrent.thread

@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class LocalBookController:BaseController() {


    @Inject
    lateinit var booklistService: BooklistService


    @Mapping("/importBookPreview")
    open fun importBookPreview(accessToken:String?, file: UploadedFile?)=run{
        if(file == null) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        if (file.isEmpty) {
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val user=getuserbytocken(accessToken)
        if(user.AllowUpTxt != true) {
            throw DataThrowable().data(JsonResponse(false,NOT_ALLOW_TXT))
        }
        if(!file.name.endsWith(".txt") && !file.name.endsWith(".epub")){
            throw DataThrowable().data(JsonResponse(false,NOT_TXT))
        }
        var f1=file.name
        kotlin.runCatching {
            f1= URLDecoder.decode( f1, "UTF-8" )
        }
        val  uploadDir = getlocalpath(user.username?:"")
        val ufile= File(uploadDir)
        if(!ufile.exists()){ufile.mkdirs()}
        val localpath= "$uploadDir/$f1"
        val  uploadedFile =  File(localpath)
        uploadedFile.writeBytes(file.contentAsBytes)
        val book = Book.initLocalBook(localpath, localpath, "")
        val chapters = LocalBook.getChapterList(book)
        val booklist= Booklist().create().bookto(book)
        booklistService.getbook(user.id!!,book.bookUrl)?.let {
           // booklist.durChapterTime=it.durChapterTime
            booklist.durChapterTitle=it.durChapterTitle
            booklist.durChapterPos=it.durChapterPos
            booklist.durChapterIndex=it.durChapterIndex
            booklistService.booklistMapper.deleteById(it.id)
        }
        booklist.originName="本地"
        booklist.userid=user.id
        booklist.lastCheckTime=System.currentTimeMillis()
        booklist.lastCheckCount=chapters.size
        booklist.totalChapterNum=chapters.size
        booklist.latestChapterTitle=chapters[chapters.size-1].title
        booklist.latestChapterTime=System.currentTimeMillis()
        booklistService.booklistMapper.insert(booklist)
        booklistService.cleancache(user.id)
        thread {
            ReadController.removeChapterListbycache(book.bookUrl,user.id!!)
            ReadController.removeallBookContentbycache(book.bookUrl,user.id!!)
            ReadController.setChapterListbycache(book.bookUrl,chapters,user.id!!)
        }
        return@run JsonResponse(true,SUCCESS).Data(mapOf("books" to book,"chapters" to chapters))
    }

}