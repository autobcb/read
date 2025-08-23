package web.controller.api


import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.core.util.DataThrowable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import web.mapper.BookSourceMapper
import web.mapper.UserBookSourceMapper
import web.mapper.UsersMapper
import web.mapper.UsertockenMapper
import web.model.*
import web.response.*

const val routepath="/api/{v}"

@Controller
open class BaseController {

    val logger: Logger = LoggerFactory.getLogger(BaseController::class.java)


    @Inject
    lateinit var usersMapper: UsersMapper

    @Inject
    lateinit var usertockenMapper: UsertockenMapper


    @Inject
    lateinit var bookSourceMapper: BookSourceMapper


    @Inject
    lateinit var userBookSourceMapper: UserBookSourceMapper

    val apiversion = 5

    val appversion="3.1.0"

    val cachetime=60

    fun getuserbytocken(accessToken:String?): Users{
        if (accessToken.isNullOrBlank()) {
            throw DataThrowable().data(JsonResponse(false,NEED_LOGIN))
        }
        val tocken= usertockenMapper.getUsertocken(accessToken) ?: throw DataThrowable().data(JsonResponse(false,NEED_LOGIN))
        val user= tocken.userid?.let { usersMapper.getUser(it)  } ?: throw DataThrowable().data(JsonResponse(false,NEED_LOGIN))
        return user
    }

    fun getsourceuser(accessToken:String?,  bookSourceUrl:String?): Pair<Users,BaseSource>{
        val user=getuserbytocken(accessToken)
        if(bookSourceUrl == null){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val source:BaseSource= if(user.source == 2){
            userBookSourceMapper.getBookSource(bookSourceUrl,user.id!!)?.toBaseSource()
        }else{
            bookSourceMapper.getBookSource(bookSourceUrl)?.toBaseSource()
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
            userBookSourceMapper.getBookSource(bookSourceUrl,user.id!!)?.toBaseSource()
        }else{
            bookSourceMapper.getBookSource(bookSourceUrl)?.toBaseSource()
        }?: throw DataThrowable().data(JsonResponse(false, NOT_SOURCE))
        return source
    }

    fun getsource( bookSourceUrl:String,user: Users): BaseSource?{
        val source:BaseSource?= if(user.source == 2){
            userBookSourceMapper.getBookSource(bookSourceUrl,user.id!!)?.toBaseSource()
        }else{
            bookSourceMapper.getBookSource(bookSourceUrl)?.toBaseSource()
        }
        return source
    }

    fun  getallBookSourcelist(user: Users): List<BaseSource>{
        val list = mutableListOf<BaseSource>()
        if(user.source == 2){
            userBookSourceMapper.getallBookSourcelist(user.id!!)?.forEach {
                list.add(it.toBaseSource())
            }
        }else{
            bookSourceMapper.getallBookSourcelist()?.forEach {
                list.add(it.toBaseSource())
            }
        }

        return list
    }

    fun  getBookSourcelist(enabled: Boolean,user: Users): List<BaseSource>{
        val list = mutableListOf<BaseSource>()
        if(user.source == 2){
            userBookSourceMapper.getBookSourcelist(enabled,user.id!!)?.forEach {
                list.add(it.toBaseSource())
            }
        }else{
            bookSourceMapper.getBookSourcelist(enabled)?.forEach {
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