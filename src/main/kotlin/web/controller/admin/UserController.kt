package web.controller.admin

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import kotlinx.coroutines.runBlocking
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
import web.controller.api.ApiWebSocket
import web.mapper.BackGroundMapper
import web.mapper.BookCacheMapper
import web.mapper.BookGroupMapper
import web.mapper.BooklistMapper
import web.mapper.BookmarkMapper
import web.mapper.ItemMapper
import web.mapper.ReplaceRuleMapper
import web.mapper.UserBookSourceMapper
import web.mapper.UsersMapper
import web.mapper.UsertockenMapper
import web.model.Users
import web.response.*
import web.util.page.PageByAjax

@Controller
@Mapping("/admin")
open class UserController {

    @Inject
    lateinit var usersMapper: UsersMapper


    @Inject
    lateinit var booklistMapper: BooklistMapper


    @Inject
    lateinit var bookGroupMapper: BookGroupMapper


    @Inject
    lateinit var usertockenMapper: UsertockenMapper


    @Inject
    lateinit var bookCacheMapper: BookCacheMapper

    @Inject
    lateinit var userBookSourceMapper: UserBookSourceMapper


    @Inject
    lateinit var replaceRuleMapper: ReplaceRuleMapper

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
    fun adduser( user: Users) = runBlocking{
        val (checkok,msg)=user.Check()
        if (!checkok) {
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = msg))
        }
        if (user.id.isNullOrBlank()){
            if(usersMapper.getUserByusername(user.username?:"") != null) {
                throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = USER_IS))
            }
            //新增用户
            usersMapper.insert(user.create())
        }else{
            usersMapper.getUser(user.id!!).also {
                if ( it == null ){ throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = USER_NOT)) }
                if(!it.username.equals(user.username)){ user.username = it.username }
            }
            //更新用户数据
            user.update().run {
                usersMapper.updateinfo(this)
            }
            ApiWebSocket.colseByuserid(user.id!!)
        }
        JsonResponse(true)
    }

    @Mapping("/getuser")
    fun getuser(id: String?) = run{
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = NOT_BANK))
        }
        val user= usersMapper.getUser(id) ?: throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = USER_NOT))
        user.password=null
        JsonResponse(true).Data(user)
    }


    @Mapping("/seachusers")
    fun seachusers(where:String? , order:String? ,@Param(defaultValue = "1") page:Int,@Param(defaultValue = "20") limit:Int) = run  {
        val queryWrapper: QueryWrapper<Users> = QueryWrapper()
        if(!where.isNullOrBlank()){
            queryWrapper.like("code",where).or().like("username",where).or().like("email",where).or().like("phone",where)
        }
        PageByAjax(usersMapper,queryWrapper,page,limit,order).also {
            (it.data as List<Users>).forEach{
                it.password = ""
            }
        }
    }

    @Mapping("/deluser")
    fun deluser(id: String?) = runBlocking{
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = NOT_BANK))
        }
        usersMapper.getUser(id) ?: throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = USER_NOT))
        deluserbyid(id)
        ApiWebSocket.colseByuserid(id)
        JsonResponse(true)
    }

    @Mapping("/delusers")
    fun delusers(@Body ids:List<String>?) = runBlocking{
        ids?.forEach { id-> runCatching {
            if (id.isNotBlank()){
                val user=usersMapper.getUser(id)
                if (user != null){
                    deluserbyid(id)
                    ApiWebSocket.colseByuserid(id)
                }
            }
        } }
        JsonResponse(true)
    }

    @Tran(policy = TranPolicy.requires_new)
    fun deluserbyid(id:String) = run{
        usersMapper.deleteById(id)
        booklistMapper.delUserbooks(id)
        bookGroupMapper.delUsergroup(id)
        usertockenMapper.delUsertockens(id)
        bookCacheMapper.delUserCache(id)
        userBookSourceMapper.delUserSource(id)
        replaceRuleMapper.delUserrule(id)
        backGroundMapper.delUserGround(id)
        itemMapper.delUserItem(id)
        bookmarkMapper.delUserBookmar(id)
    }
}