package web.controller.api


import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.mapper.BooklistMapper
import web.mapper.BookmarkMapper
import web.model.Bookmark
import web.response.JsonResponse
import web.response.MARK_IS
import web.response.NOT_BANK
import web.response.NOT_IS
import web.response.NO_BOOK


@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class BookMarkController :BaseController(){

    @Inject
    lateinit var bookmarkMapper: BookmarkMapper

    @Inject
    lateinit var booklistMapper: BooklistMapper

    @Mapping("/addbookmark")
    fun  addbookmark(accessToken:String?, url:String ,name:String, index: Int,pos : Double) = run{
        if (url.isBlank()) {
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val user=getuserbytocken(accessToken)
        val book = booklistMapper.getbook(user.id!!,url)?:throw DataThrowable().data(JsonResponse(false,NO_BOOK))
        val marks = bookmarkMapper.getbybook(user.id!!,book.bookUrl!!)
        for ( mark in marks) {
            if (mark.cindex == index && mark.cpos == pos){
                throw DataThrowable().data(JsonResponse(false,MARK_IS))
            }
        }
        val bookmark= Bookmark().create(user.id!!,book).apply {
            cindex=index
            cpos =pos
            cname =name
        }
        bookmarkMapper.insert(bookmark)
        JsonResponse(true)
    }


    @Mapping("/getbookmark")
    fun  getbookmark(accessToken:String?, url:String) = run{
        if (url.isBlank()) {
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val user=getuserbytocken(accessToken)
        val book = booklistMapper.getbook(user.id!!,url)?:throw DataThrowable().data(JsonResponse(true).Data(listOf<String>()))
        val marks = bookmarkMapper.getbybook(user.id!!,book.bookUrl!!)
        JsonResponse(true).Data(marks)
    }

    @Mapping("/delbookmark")
    fun  delbookmark(accessToken:String?, id:String) = run{
        if (id.isBlank()) {
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val user=getuserbytocken(accessToken)
        val mark = bookmarkMapper.selectById(id)
        if(mark == null || mark.userid != user.id) {
            throw DataThrowable().data(JsonResponse(false,NOT_IS))
        }
        bookmarkMapper.deleteById(id)
        JsonResponse(true)
    }
}