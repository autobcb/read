package web.controller.api


import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.core.util.DataThrowable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import web.model.*
import web.response.*
import web.service.BookSourceService
import web.service.UserBookSourceService
import web.service.UsersService
import web.service.UsertockenService

const val routepath="/api/{v}"

@Controller
open class BaseController {

    val logger: Logger = LoggerFactory.getLogger(BaseController::class.java)


    @Inject
    lateinit var usersService: UsersService

    @Inject
    lateinit var usertockenService: UsertockenService


    @Inject
    lateinit var bookSourceService: BookSourceService


    @Inject
    lateinit var userBookSourceService: UserBookSourceService

    val apiversion = 5

    val appversion="2.16.0"


    fun getuserbytocken(accessToken:String?): Users{
        if (accessToken.isNullOrBlank()) {
            throw DataThrowable().data(JsonResponse(false,NEED_LOGIN))
        }
        val tocken= usertockenService.getUsertocken(accessToken) ?: throw DataThrowable().data(JsonResponse(false,NEED_LOGIN))
        val user= usersService.getUser(tocken.userid) ?: throw DataThrowable().data(JsonResponse(false,NEED_LOGIN))
        return user
    }

    fun getsourceuser(accessToken:String?,  bookSourceUrl:String?): Pair<Users,BaseSource>{
        val user=getuserbytocken(accessToken)
        if(bookSourceUrl == null){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val source:BaseSource= if(user.source == 2){
            userBookSourceService.getBookSource(bookSourceUrl,user.id!!)?.toBaseSource()
        }else{
            bookSourceService.getBookSource(bookSourceUrl)?.toBaseSource()
        }?: throw DataThrowable().data(JsonResponse(false, NOT_SOURCE))
        return Pair(user,source)
    }

    fun getsource(accessToken:String?,  bookSourceUrl:String?): BaseSource{
        return getsourceuser(accessToken,bookSourceUrl).second
    }

    fun getsource(user: Users,  bookSourceUrl:String?): BaseSource{
        if(bookSourceUrl == null){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val source:BaseSource= if(user.source == 2){
            userBookSourceService.getBookSource(bookSourceUrl,user.id!!)?.toBaseSource()
        }else{
            bookSourceService.getBookSource(bookSourceUrl)?.toBaseSource()
        }?: throw DataThrowable().data(JsonResponse(false, NOT_SOURCE))
        return source
    }

    fun getsource( bookSourceUrl:String,user: Users): BaseSource?{
        val source:BaseSource?= if(user.source == 2){
            userBookSourceService.getBookSource(bookSourceUrl,user.id!!)?.toBaseSource()
        }else{
            bookSourceService.getBookSource(bookSourceUrl)?.toBaseSource()
        }
        return source
    }

    fun  getallBookSourcelist(user: Users): List<BaseSource>{
        val list = mutableListOf<BaseSource>()
        if(user.source == 2){
            userBookSourceService.getallBookSourcelist(user.id!!)?.forEach {
                list.add(it.toBaseSource())
            }
        }else{
            bookSourceService.getallBookSourcelist()?.forEach {
                list.add(it.toBaseSource())
            }
        }

        return list
    }

    fun  getBookSourcelist(enabled: Boolean,user: Users): List<BaseSource>{
        val list = mutableListOf<BaseSource>()
        if(user.source == 2){
            userBookSourceService.getBookSourcelist(enabled,user.id!!)?.forEach {
                list.add(it.toBaseSource())
            }
        }else{
            bookSourceService.getBookSourcelist(enabled)?.forEach {
                list.add(it.toBaseSource())
            }
        }
        return list
    }


    fun getsourceuser(accessToken:String?):Users{
        val user=getuserbytocken(accessToken)
        if(user.source == 0){
            throw DataThrowable().data(JsonResponse(false, CAN_NOT))
        }
        return user
    }


}