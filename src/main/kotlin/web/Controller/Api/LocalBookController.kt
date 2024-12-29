package web.Controller.Api

import com.google.gson.Gson
import io.legado.app.data.entities.Book
import io.legado.app.model.localBook.LocalBook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import web.Mapper.BooklistMapper
import web.Model.Booklist
import web.Response.JsonResponse
import web.Util.cache.getlocalpath
import web.Util.cache.removecache
import web.Util.cache.setcache
import web.Util.model.ChapterList
import java.io.File
import java.net.URLDecoder

@ApiController
class LocalBookController() :BaseController() {

    @Autowired
    lateinit var booklistMapper: BooklistMapper

    @PostMapping(value = ["/importBookPreview"], produces = [MediaType.APPLICATION_JSON_VALUE] )
    fun importBookPreview(@RequestParam("accessToken") accessToken:String?,@RequestParam("file")  file: MultipartFile)=run{
        if (file.isEmpty()) {
            return@run JsonResponse(false,  NOT_BANK)
        }
        val user=getuserbytocken(accessToken).also {
            if(it == null){
                return@run JsonResponse(false,NEED_LOGIN)
            }
        }!!
        if(!user.AllowUpTxt){
            return@run JsonResponse(false,NOT_ALLOW_TXT)
        }
        if(!file.originalFilename.endsWith(".txt")){
            return@run JsonResponse(false,NOT_TXT)
        }
        var f1=file.getOriginalFilename()
        kotlin.runCatching {
            f1= URLDecoder.decode( f1, "UTF-8" )
        }
        var  uploadDir = getlocalpath(user.username)
        var ufile=File(uploadDir)
        if(!ufile.exists()){ufile.mkdirs()}
        var localpath=uploadDir+"/" + f1
        var  uploadedFile =  File(localpath)
        uploadedFile.writeBytes(file.bytes)
        val book = Book.initLocalBook(localpath, localpath, "")
        val chapters = LocalBook.getChapterList(book)
        if(booklistMapper.getbook(user.id,book.bookUrl) == null){
            var booklist=Booklist().create().bookto(book)
            booklist.userid=user.id
            booklist.lastCheckTime=System.currentTimeMillis()
            booklist.lastCheckCount=chapters.size
            booklist.totalChapterNum=chapters.size
            booklist.latestChapterTitle=chapters[chapters.size-1].title
            booklist.latestChapterTime=System.currentTimeMillis()
            booklistMapper.insert(booklist)
        }
        removecache(book.bookUrl)
        var cache= ChapterList(book =book, bookChapters=chapters,source=null)
        val json= Gson().toJson(cache)
        setcache(book.bookUrl,json)
        return@run JsonResponse(true,SUCCESS).Data(mapOf("books" to book,"chapters" to chapters))
    }
}