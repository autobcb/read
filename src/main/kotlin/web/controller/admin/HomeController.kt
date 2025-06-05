package web.controller.admin

import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Get
import org.noear.solon.annotation.Mapping
import org.noear.solon.core.handle.ModelAndView



@Controller
@Mapping("/admin")
class HomeController {

    @get:Get
    @get:Mapping("/adduser")
    val adduser = ModelAndView("admin/adduser.html")

    @get:Get
    @get:Mapping("/addcookie")
    val addcookie = ModelAndView("admin/addcookie.html")

    @get:Get
    @get:Mapping("/login")
    val login =ModelAndView("login.html")

    @get:Get
    @get:Mapping("/?")
    val index =ModelAndView("admin/index.html").also {
        it.put("index","layui-this")
    }

    @get:Get
    @get:Mapping("/book")
    val book =ModelAndView("admin/book.html").also {
        it.put("book","layui-this")
    }

    @get:Get
    @get:Mapping("/rss")
    val rss =ModelAndView("admin/rss.html").also {
        it.put("rss","layui-this")
    }

    @get:Get
    @get:Mapping("/code")
    val cookie =ModelAndView("admin/code.html").also {
        it.put("code","layui-this")
    }

}