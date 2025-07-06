package web.service

import com.google.gson.reflect.TypeToken
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Configuration
import org.noear.solon.annotation.Inject
import org.noear.solon.data.cache.CacheService
import web.mapper.UserBookSourceMapper
import web.model.UserBookSource


@Configuration
open class UserBookSourceService {

    @Inject
    private lateinit var cache: CacheService

    @Db("db")
    @Inject
    lateinit  var userBookSourceMapper: UserBookSourceMapper

    private fun getlistkey(userid:String,enabled: Boolean):String{
        return "usersource:$userid,$enabled"
    }

    private fun getalllistkey(userid:String):String{
        return "userallsource:$userid"
    }

    fun getBookSource(bookSourceUrl: String?, userid: String?): UserBookSource?{
        if(userid.isNullOrBlank() || bookSourceUrl.isNullOrBlank()) return null
        return userBookSourceMapper.getBookSource( bookSourceUrl,userid)
    }

    fun getBookSourcelike(bookSourceUrl: String?, userid: String?): UserBookSource?{
        if(userid.isNullOrBlank() || bookSourceUrl.isNullOrBlank()) return null
        return userBookSourceMapper.getBookSourcelike( bookSourceUrl,userid)
    }

    fun getBookSourcelist( enabled: Boolean, userid: String?): List<UserBookSource>?{
        if(userid.isNullOrBlank()) return listOf()
        val t= object : TypeToken<List<UserBookSource>>() {}.type
        return cache.getOrStore(getlistkey(userid,enabled), t,300) {
             userBookSourceMapper.getBookSourcelist(enabled, userid)
        }
    }

    fun getallBookSourcelist(userid: String?): List<UserBookSource>?{
        if(userid.isNullOrBlank()) return listOf()
        val t= object : TypeToken<List<UserBookSource>>() {}.type
        return cache.getOrStore(getalllistkey(userid), t,300) {
            userBookSourceMapper.getallBookSourcelist(userid)
        }
    }

    fun changegroup(id: String?, userid: String?,  group: String?):Int{
        cleancache(userid)
        return userBookSourceMapper.changegroup(id?:"", group?:"")
    }

    fun changeEnabled(id: String, userid: String?,enabled: Boolean):Int{
        cleancache(userid)
        return userBookSourceMapper.changeEnabled(id, enabled)
    }

    fun changeenabledExplore(id: String, userid: String?,  enabled: Boolean):Int{
        cleancache(userid)
        return userBookSourceMapper.changeenabledExplore(id, enabled)
    }

    fun changeorder(id: String, userid: String?,  sourceorder: Int):Int{
        cleancache(userid)
        return userBookSourceMapper.changeorder(id, sourceorder)
    }

    fun delUserSource( userid: String): Int{
        cleancache(userid)
        return userBookSourceMapper.delUserSource(userid)
    }

    fun delBookSource( bookSourceUrl: String, userid: String): Int{
        cleancache(userid)
        return userBookSourceMapper.delBookSource( bookSourceUrl,userid)
    }

    fun cleancache(userid: String?){
        if(userid.isNullOrBlank()) return
        cache.remove(getlistkey(userid,true))
        cache.remove(getlistkey(userid,false))
        cache.remove(getalllistkey(userid))
    }
}