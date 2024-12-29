package web.Util.read

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import web.Model.Cookie
import io.legado.app.CookieList
import io.legado.app.Cookiemodel
import web.Util.mapper

fun Updatecookie(){
    println("Update cookiemodel")
    var cookieList= mapper.get().cookiemapper.selectList(QueryWrapper(Cookie::class.java))?: listOf()
    var list:MutableList<Cookiemodel> = mutableListOf()
    cookieList.forEach {
        var cookie = Cookiemodel()
        cookie.host=it.host
        cookie.value=it.value
        list.add(cookie)
    }
    CookieList.set(list)
}