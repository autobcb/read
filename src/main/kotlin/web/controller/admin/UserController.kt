package web.controller.admin

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Body
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.annotation.Param
import org.noear.solon.annotation.Post
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.Tran
import org.noear.solon.data.tran.TranPolicy
import web.mapper.BackGroundMapper
import web.mapper.BookmarkMapper
import web.mapper.ItemMapper
import web.model.Users
import web.response.*
import web.service.*
import web.util.page.PageByAjax

@Controller
@Mapping("/admin")
open class UserController {

    @Inject
    lateinit var usersService: UsersService


    @Inject
    lateinit var booklistService: BooklistService


    @Inject
    lateinit var bookGroupService: BookGroupService


    @Inject
    lateinit var usertockenService: UsertockenService


    @Inject
    lateinit var bookCacheService: BookCacheService

    @Inject
    lateinit var userBookSourceService: UserBookSourceService


    @Inject
    lateinit var replaceRuleService: ReplaceRuleService

    @Db("db")
    @Inject
    lateinit var backGroundMapper: BackGroundMapper

    @Db("db")
    @Inject
    lateinit var itemMapper: ItemMapper

    @Db("db")
    @Inject
    lateinit var bookmarkMapper: BookmarkMapper

    @Post
    @Mapping("/adduser")
    fun adduser( user: Users) = run{
        val (checkok,msg)=user.Check()
        if (!checkok) {
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = msg))
        }
        if (user.id.isNullOrBlank()){
            if(usersService.getUserByusername(user.username?:"") != null) {
                throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = USER_IS))
            }
            //新增用户
            usersService.usersMapper.insert(user.create())
        }else{
            usersService.getUser(user.id!!).also {
                if ( it == null ){ throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = USER_NOT)) }
                if(!it.username.equals(user.username)){ user.username = it.username }
            }
            //更新用户数据
            user.update().run {
                usersService.updateinfo(this)
            }

        }
        JsonResponse(true)
    }

    @Mapping("/getuser")
    fun getuser(id: String?) = run{
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = NOT_BANK))
        }
        val user= usersService.getUser(id) ?: throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = USER_NOT))
        user.password=null
        JsonResponse(true).Data(user)
    }


    @Mapping("/seachusers")
    fun seachusers(where:String? , order:String? ,@Param(defaultValue = "1") page:Int,@Param(defaultValue = "20") limit:Int) = run  {
        val queryWrapper: QueryWrapper<Users> = QueryWrapper()
        if(!where.isNullOrBlank()){
            queryWrapper.like("code",where).or().like("username",where).or().like("email",where).or().like("phone",where)
        }
        PageByAjax(usersService.usersMapper,queryWrapper,page,limit,order).also {
            (it.data as List<Users>).forEach{
                it.password = ""
            }
        }
    }

    @Mapping("/deluser")
    fun deluser(id: String?) = run{
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = NOT_BANK))
        }
        usersService.getUser(id) ?: throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = USER_NOT))
        deluserbyid(id)
        JsonResponse(true)
    }

    @Mapping("/delusers")
    fun delusers(@Body ids:List<String>?) = run{
        ids?.forEach { id-> runCatching {
            if (id.isNotBlank()){
                val user=usersService.getUser(id)
                if (user != null){
                    deluserbyid(id)
                }
            }
        } }
        JsonResponse(true)
    }

    @Tran(policy = TranPolicy.requires_new)
    fun deluserbyid(id:String) = run{
        usersService.usersMapper.deleteById(id)
        booklistService.delUserbooks(id)
        bookGroupService.delUsergroup(id)
        usertockenService.delUsertockens(id)
        bookCacheService.delUserCache(id)
        userBookSourceService.delUserSource(id)
        replaceRuleService.delUserrule(id)
        backGroundMapper.delUserGround(id)
        itemMapper.delUserItem(id)
        bookmarkMapper.delUserBookmar(id)
    }
}