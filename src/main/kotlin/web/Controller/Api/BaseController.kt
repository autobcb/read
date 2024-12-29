package web.Controller.Api

import org.springframework.beans.factory.annotation.Autowired
import web.Mapper.BookSourceMapper
import web.Mapper.UsersMapper
import web.Mapper.UsertockenMapper
import web.Model.BookSource
import web.Model.Users
import web.Response.JsonResponse
import web.Util.LogFactory

open class BaseController:LogFactory() {
    val NEED_LOGIN = "NEED_LOGIN"

    val NOT_BANK = "NOT_BANK"

    val NOT_SOURCE = "书源不存在"

    val SUCCESS = "success"

    val BOOKIS="书本已在书架"

    val NO_BOOK="书本不存在"

    val NOT_ALLOW_TXT ="不允许导入图书"

    val NOT_TXT ="当前文件格式不支持"

    @Autowired
    lateinit var usersMapper: UsersMapper

    @Autowired
    lateinit var usertockenMapper: UsertockenMapper

    @Autowired
    lateinit var bookSourcemapper: BookSourceMapper


    fun getuserbytocken(accessToken:String?):Users?{
        if (accessToken == null || accessToken.isBlank()) {
            return null
        }
        var tocken=usertockenMapper.selectById(accessToken)
        if (tocken == null) {
            return null
        }
        var user=usersMapper.selectById(tocken.userid)
        if (user == null) {
            return null
        }
        return user
    }

    fun getsource(accessToken:String?,  bookSourceUrl:String?): Pair<BookSource?, JsonResponse?>{
        getuserbytocken(accessToken).also {
            if(it == null){
                return Pair(null, JsonResponse(false,NEED_LOGIN))
            }
        }!!
        var source=bookSourcemapper.getBookSource(bookSourceUrl.also {
            if (it == null){
                return Pair(null, JsonResponse(false,NOT_BANK))
            }
        }!!).also {
            if (it == null){
                return Pair(null, JsonResponse(false,NOT_SOURCE))
            }
        }!!
        return Pair(source,null)
    }

    fun getsource( bookSourceUrl:String?): BookSource?{
        if(bookSourceUrl == null){return null}
        return bookSourcemapper.getBookSource(bookSourceUrl!!)
    }
}