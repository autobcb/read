package web.Controller.Admin

import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import web.Util.admin.islogin

@AdminController
class AdminHomeController {

    @GetMapping("/login")
    fun login() = "adminlogin"

    @GetMapping("/adduser")
    fun adduser() = "admin/adduser"

    @GetMapping("/addcookie")
    fun addcookie() = "admin/addcookie"

    @GetMapping("/index")
    fun index(model: Model)  = run {
        model.addAttribute("index","layui-this")
        "admin/index"
    }

    @GetMapping("/book")
    fun book(model: Model)  = run {
        model.addAttribute("book","layui-this")
        "admin/book"
    }

    @GetMapping("/cookie")
    fun cookie(model: Model)  = run {
        model.addAttribute("cookie","layui-this")
        "admin/cookie"
    }

    @GetMapping(value = ["/",""])
    @ResponseBody
    fun index(session: HttpSession,resp: HttpServletResponse) = if(islogin(session)) resp.sendRedirect("/admin/index")
    else resp.sendRedirect("/admin/login")
}