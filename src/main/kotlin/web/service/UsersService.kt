package web.service


import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Configuration
import org.noear.solon.annotation.Inject
import org.noear.solon.data.cache.CacheService
import web.mapper.UsersMapper
import web.model.Users

@Configuration
open class UsersService {

    @Inject
    private lateinit var cache: CacheService

    @Db("db")
    @Inject
    lateinit  var usersMapper: UsersMapper

    fun getUser(id: String?): Users?{
        if(id.isNullOrBlank()) return null
        val key = "user:$id"
        return cache.getOrStore(key,Users::class.java,300) {
            usersMapper.getUser(id)
        }
    }

    fun getAllUser(): List<Users>{
        return usersMapper.getAllUser()
    }

    fun getUserByusername(username: String): Users?{
        return usersMapper.getUserByusername(username)
    }

    fun getUserByemail(email: String): List<Users>{
        return usersMapper.getUserByemail(email)
    }

    fun changepass( id: String,password: String):Int{
        cleancache(id)
        return usersMapper.changepass(id, password)
    }

    fun delUserall( id: String): Int{
        cleancache(id)
        return usersMapper.delUserall(id)
    }


    fun updateinfo( user: Users):Int{
        cleancache(user.id)
        return usersMapper.updateinfo(user)
    }

    fun cleancache(userid: String?){
        if(userid.isNullOrBlank()) return
        cache.remove("user:${userid}")
    }

    fun Drop(): Int{
        return Drop()
    }
}