package web.Controller.Admin

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import web.Mapper.BooklistMapper
import web.Mapper.UsersMapper
import web.Mapper.UsertockenMapper
import web.Model.Users
import web.Response.JsonResponse
import web.Util.cache.getlocalpath
import web.Util.page.PageByAjax
import java.io.File


@AdminApiController
class AdminUserController {

    @Autowired
    lateinit var usersMapper: UsersMapper

    @Autowired
    lateinit var booklistMapper: BooklistMapper

    @Autowired
    lateinit var usertockenMapper: UsertockenMapper

    @PostMapping("/adduser")
    fun adduser( user: Users) = run add@{
        val (checkok,msg)=user.Check()
        if (!checkok) {
            return JsonResponse(false,msg)
        }
        if (user.id.isBlank()){
            if(usersMapper.getUserByusername(user.username) != null) {
                return  JsonResponse(false,"用户名已存在")
            }
            //新增用户
            usersMapper.insert(user.create())
        }else{
            usersMapper.getUser(user.id).also {
                if ( it == null ){ return@add  JsonResponse(false,"用户不存在")}
                if(!it.username.equals(user.username)){ return@add  JsonResponse(false,"不允许修改用户名")}
            }
            //更新用户数据
            usersMapper.updateById(user.update())
        }

        JsonResponse(true,"success")
    }

    @PostMapping("/getuser")
    fun getuser(@RequestParam("id") id: String?) = run{
        if (id == null || id.isBlank()){
            return JsonResponse(false,"ID?")
        }
        var user=usersMapper.getUser(id)
        if (user == null){
            return JsonResponse(false,"用户不存在")
        }
        user.password=null
        JsonResponse(true,"success").Data(user)
    }

    @PostMapping("/deluser")
    fun deluser(@RequestParam("id") id: String?) = run{
        if (id == null || id.isBlank()){
            return JsonResponse(false,"ID?")
        }
        var user=usersMapper.getUser(id)
        if (user == null){
            return JsonResponse(false,"用户不存在")
        }
        var file= File(getlocalpath(user.username))
        if(file.exists()){
            file.deleteRecursively()
        }
        usersMapper.delUserall(id)

        JsonResponse(true,"success")
    }

    @PostMapping("/delusers")
    fun delusers(@RequestBody ids:List<String>) = run{
        ids.forEach { id->
            if (id.isNotBlank()){
                var user=usersMapper.getUser(id)
                if (user != null){
                    var file= File(getlocalpath(user.username))
                    if(file.exists()){
                        file.deleteRecursively()
                    }
                    usersMapper.delUserall(id)
                }
            }
        }
        JsonResponse(true,"success")
    }

    @GetMapping("/seachusers")
    fun seachusers(@RequestParam("where") _where:String? , @RequestParam("order") _order:String? , @RequestParam("page") page:Int,@RequestParam("limit")  limit:Int) = run{
        var queryWrapper:QueryWrapper<Users> = QueryWrapper()
        if(_where != null && _where.isNotBlank()){
            queryWrapper.like("username",_where).or().like("email",_where).or().like("phone",_where)
        }
       PageByAjax(usersMapper,queryWrapper,page,limit,_order).apply {
            (data as List<Users>).forEach({
                it.password=null
            })
        }
    }
}