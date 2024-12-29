package web.Controller.Admin

import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import web.Properties.AdminProperties
import web.Response.JsonResponse
import web.Util.admin.loginok
import web.Util.admin.logout

@AdminApiController
class LoginController {

    @Autowired
    lateinit var adminProperties: AdminProperties

    @PostMapping(value = ["/login"], produces = [MediaType.APPLICATION_JSON_VALUE] )
    fun login(@RequestParam("username") _username:String?, @RequestParam("password") _password:String?, session: HttpSession): JsonResponse = run {
        if (_username.isNullOrBlank() || _password.isNullOrBlank() )  {
            return JsonResponse(false,"NOT blank")
        }
        if(adminProperties.username.equals(_username) && adminProperties.password.equals(_password)) {
            loginok(_username,session)
            JsonResponse(true,"success")
        }else{
            JsonResponse(false,"用户名或密码错误")
        }

    }

    @RequestMapping("/logout")
    fun logout(session: HttpSession,resp: HttpServletResponse){
        logout(session)
        resp.sendRedirect("/admin/login")
    }


}