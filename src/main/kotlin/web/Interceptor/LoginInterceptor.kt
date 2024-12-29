package web.Interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor
import web.Util.admin.islogin

class LoginInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if(!islogin(request.session)){
            //没登录的滚去登录
            response.sendRedirect("/admin/login")
            return false
        }
        return true
    }

}