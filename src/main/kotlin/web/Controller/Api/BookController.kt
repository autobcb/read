package web.Controller.Api

import io.legado.app.model.webBook.WebBook
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import web.Mapper.BookSourceMapper
import web.Mapper.BooklistMapper
import web.Model.BookSource
import web.Response.JsonResponse
import web.Util.model.SearchBook
import web.Util.read.updatebook
import java.io.File

@ApiController
class BookController:BaseController() {


    @Autowired
    lateinit var booklistMapper: BooklistMapper

    @PostMapping(value = ["/searchBook"], produces = [MediaType.APPLICATION_JSON_VALUE] )
    fun searchBook(@RequestParam("accessToken") accessToken:String?,@RequestParam("bookSourceUrl") bookSourceUrl:String?,
                   @RequestParam("page") page:Int?,@RequestParam("key") key:String? )= runBlocking{
        val (source,jp)=getsource(accessToken,bookSourceUrl)
        if(jp != null){
            return@runBlocking jp!!
        }
        logger.info ("searchBook")
        var webBook = WebBook(source!!.json?:"", false)
        var re=webBook.searchBook(key?:"", page?:0)
        return@runBlocking JsonResponse(true,SUCCESS).Data(re)
    }

    @PostMapping(value = ["/exploreBook"], produces = [MediaType.APPLICATION_JSON_VALUE] )
    fun exploreBook(@RequestParam("accessToken") accessToken:String?,@RequestParam("bookSourceUrl") bookSourceUrl:String?,
                    @RequestParam("page") page:Int?,@RequestParam("ruleFindUrl") ruleFindUrl:String? ) = runBlocking{
        val (source,jp)=getsource(accessToken,bookSourceUrl)
        if(jp != null){
            return@runBlocking jp!!
        }
        logger.info ("exploreBook")
        var webBook = WebBook(source!!.json?:"", false)
        var re=webBook.exploreBook(ruleFindUrl?:"", page?:0)
        return@runBlocking JsonResponse(true,SUCCESS).Data(re)
    }

    @PostMapping(value = ["/saveBook"], produces = [MediaType.APPLICATION_JSON_VALUE] )
    fun saveBook(@RequestParam("accessToken") accessToken:String?,@RequestBody book: SearchBook) = run{
        val user=getuserbytocken(accessToken).also {
            if(it == null){
                return@run JsonResponse(false,NEED_LOGIN)
            }
        }!!
        with(book){
            if (bookUrl.isBlank() || name.isBlank()){
                return@run JsonResponse(false,NOT_BANK)
            }
        }
        var booktolist=book.tobooklist(user.id)
        if (booklistMapper.getbook(user.id,booktolist.bookUrl) != null){
            return@run JsonResponse(false,BOOKIS)
        }
        booklistMapper.insert(booktolist)
        Thread{
            runBlocking {
                var source=getsource(book.origin)!!
                updatebook(booktolist, source)
            }
        }.start()
        return@run JsonResponse(true,SUCCESS)
    }

    @PostMapping(value = ["/deleteBook"], produces = [MediaType.APPLICATION_JSON_VALUE] )
    fun deleteBook(@RequestParam("accessToken") accessToken:String?,@RequestBody book: SearchBook)=run{
        val user=getuserbytocken(accessToken).also {
            if(it == null){
                return@run JsonResponse(false,NEED_LOGIN)
            }
        }!!
        with(book){
            if (bookUrl.isBlank()){
                return@run JsonResponse(false,NOT_BANK)
            }
        }
        var booktolist=booklistMapper.getbook(user.id,book.bookUrl).also {
            if(it == null){
                return@run JsonResponse(false,NO_BOOK)
            }
        }!!
        if (booktolist.origin == "loc_book"){
            var file=File(booktolist.bookUrl)
            if (file.exists()){
                file.delete()
            }
        }
        booklistMapper.deleteById(booktolist.id!!)
        return@run JsonResponse(true,SUCCESS)
    }


}