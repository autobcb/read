package web.Controller.Admin

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import web.Mapper.CookieMapper
import web.Model.Cookie
import web.Response.JsonResponse
import web.Util.page.PageByAjax
import web.Util.read.Updatecookie

@AdminApiController
class AdminCookieController {

    @Autowired
    lateinit var cookieMapper: CookieMapper

    @GetMapping("/seachcookie")
    fun seachcookie(@RequestParam("where") _where:String?, @RequestParam("order") _order:String?, @RequestParam("page") page:Int, @RequestParam("limit")  limit:Int) = run{
        var queryWrapper: QueryWrapper<Cookie> = QueryWrapper()
        if(_where != null && _where.isNotBlank()){
            queryWrapper.like("host",_where)
        }
        PageByAjax(cookieMapper,queryWrapper,page,limit,_order)
    }

    @PostMapping("/delcookie")
    fun delcookie(@RequestParam("host") host: String?) = run{
        if (host == null || host.isBlank()){
            return JsonResponse(false,"host?")
        }
        var cookie=cookieMapper.selectById(host)
        if (cookie == null){
            return JsonResponse(false,"不存在")
        }
        cookieMapper.deleteById(host)
        Thread{
            runCatching {
                Updatecookie()
            }
        }.start()
        JsonResponse(true,"success")
    }

    @PostMapping("/addcookie")
    fun addcookie(@RequestParam("host") host: String?,@RequestParam("value")  value:String) = run{
        if (host == null || host.isBlank()){
            return JsonResponse(false,"host?")
        }
        var cookie=cookieMapper.selectById(host)
        if (cookie != null){
            return JsonResponse(false,"cookie已存在，请删除后重新添加")
        }
        cookie=Cookie().create()
        cookie.host=host
        cookie.value=value
        cookieMapper.insert(cookie)
        Thread{
            runCatching {
                Updatecookie()
            }
        }.start()
        JsonResponse(true,"success")
    }
}