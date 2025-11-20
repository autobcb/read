package web.controller.api

import kotlinx.coroutines.runBlocking
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.annotation.Path
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.Cache
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.model.Users
import web.model.Usertocken
import web.response.*
import web.util.admin.passsign

@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class UserController:BaseController() {

    @Mapping("/appversion")
    fun lookappversion()=run {
        appversion
    }

    @Inject(value = "\${user.allowchange:false}", autoRefreshed=true)
    var allowchange:Boolean=false

    @Mapping("/changeSourcePermission")
    open fun changeSourcePermission( accessToken:String?, permission: Int)= runBlocking{
        val user=getuserbytocken(accessToken)
        if(!allowchange || user.source == 1){
            throw DataThrowable().data(QJsonResponse(false).Msg("不允许权限"))
        }
        when (permission) {
            0->{
                //不允许修改书源
                usersMapper.updatesource(user.id!!,0)
            }
            1 ->{
                throw DataThrowable().data(QJsonResponse(false).Msg("不允许修改到此权限"))
            }
            2->{
                //独立书源
                usersMapper.updatesource(user.id!!,2)
            }
            else -> {
                throw DataThrowable().data(QJsonResponse(false).Msg("permission错误或者后端版本太低"))
            }
        }
        ApiWebSocket.colseByuserid(user.id!!)
        JsonResponse(true)
    }

    @Mapping("/login")
    fun login(username:String? , password:String? , model:String?,@Path v:Int) = run {
        if (username.isNullOrBlank() || password.isNullOrBlank() )  {
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        val users=usersMapper.getUserByusernameoremail(username)
        val user:Users? = if (users.isNotEmpty()) users[0] else null
        if (user == null || !user.password.equals(passsign( password))) {
            throw DataThrowable().data(JsonResponse(false,PASS_ERROR))
        }
        if(v < apiversion){
            throw DataThrowable().data(JsonResponse(false,"当前app版本已不在支持，请更新版本"))
        }else  if(v > apiversion){
            throw DataThrowable().data(JsonResponse(false,"当前后端不支持您的app，请联系管理员更新后端"))
        }

        val tockens=usertockenMapper.getUsertockens(user.id!!)
        //登陆设备超过20个自动登出全部
        if(tockens != null && tockens.size >= 20){
            usertockenMapper.delUsertockens(user.id!!)
        }
        val tocken=Usertocken().create()
        tocken.userid=user.id
        tocken.model=model?:""
        usertockenMapper.insert(tocken)

        JsonResponse(true,"success").Data(mapOf("accessToken" to tocken.id))
    }

    @Cache(key = "getUserInfo:\${accessToken}", tags = "getUserInfo", seconds = 600)
    @Mapping("/getUserInfo")
    open fun getUserInfo( accessToken:String?) = run {
        val user=getuserbytocken(accessToken)
        JsonResponse(true,"success").Data(mapOf("userInfo" to
                mapOf("username" to user.username,"phone" to user.phone,"email" to user.email)
        ))
    }

    @Mapping("/changepass")
    fun changepass( accessToken:String? , password:String?,oldpassword:String ) = run {
        if ( password.isNullOrBlank() )  {
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        if(password.length <6 || password.length > 15){
            throw DataThrowable().data(JsonResponse(false,PASS_VAIL_ERROR))
        }
        val user=getuserbytocken(accessToken)
        if (!user.password.equals(passsign( oldpassword))) {
            throw DataThrowable().data(JsonResponse(false,PASS_ERROR))
        }
        usersMapper.changepass(user.id!!,passsign( password))

        JsonResponse(true,"success")
    }

    @Mapping("/getalltocken")
    fun getalltocken( accessToken:String?) = run {
        val user=getuserbytocken(accessToken)

        val tockens=usertockenMapper.getUsertockens(user.id!!)

        JsonResponse(true,"success").Data(tockens)
    }
}