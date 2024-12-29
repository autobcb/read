
package web.Controller.Api


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import web.Errors.JsonException
import web.Mapper.UsersMapper
import web.Mapper.UsertockenMapper
import web.Model.Users
import web.Model.Usertocken
import web.Response.JsonResponse
import web.Util.passsign

@ApiController
class UserController : BaseController() {

    @RequestMapping(value = ["/login"])
    fun login(@RequestParam("username") _username:String? , @RequestParam("password") _password:String? ,
              @RequestParam("model") model:String?) = run {
        if (_username.isNullOrBlank() || _password.isNullOrBlank() )  {
            return JsonResponse(false,NOT_BANK)
        }
        var user: Users?=usersMapper.getUserByusername(_username)
        if (user == null || !user.password.equals(passsign( _password))) {
            return JsonResponse(false,"账号或密码错误")
        }

        var tockens=usertockenMapper.getUsertockens(user.id)
        //登陆设备超过5个自动登出全部
        if(tockens != null && tockens.size >= 5){
            usertockenMapper.delUsertockens(user.id)
        }
        var tocken=Usertocken().create()
        tocken.userid=user.id
        tocken.model=model?:""
        usertockenMapper.insert(tocken)

        JsonResponse(true,"success").Data(mapOf("accessToken" to tocken.id))
    }

    @RequestMapping(value = ["/getUserInfo"], produces = [MediaType.APPLICATION_JSON_VALUE] )
    fun getUserInfo(@RequestParam("accessToken") accessToken:String?) = run {
        var user=getuserbytocken(accessToken).also {
            if(it == null){
                return@run JsonResponse(false,NEED_LOGIN)
            }
        }!!
        JsonResponse(true,"success").Data(mapOf("userInfo" to
                mapOf("username" to user.username,"phone" to user.phone,"email" to user.email)
        ))
    }

    @RequestMapping(value = ["/changepass"])
    fun changepass(@RequestParam("accessToken") accessToken:String? , @RequestParam("password") _password:String? ) = run {
        if ( _password.isNullOrBlank() )  {
            return JsonResponse(false,NOT_BANK)
        }
        if(_password != null  && (_password!!.length <6 || _password!!.length > 15) ){
            return JsonResponse(false,"密码长度请在6位到15位之间")
        }
        var user=getuserbytocken(accessToken).also {
            if(it == null){
                return@run JsonResponse(false,NEED_LOGIN)
            }
        }!!

        usersMapper.changepass(user.id,passsign( _password))

       JsonResponse(true,"success")
    }

    @RequestMapping(value = ["/getalltocken"])
    fun getalltocken(@RequestParam("accessToken") accessToken:String?) = run {
        var user=getuserbytocken(accessToken).also {
            if(it == null){
                return@run JsonResponse(false,NEED_LOGIN)
            }
        }!!

        var tockens=usertockenMapper.getUsertockens(user.id)

        JsonResponse(true,"success").Data(tockens)
    }
}