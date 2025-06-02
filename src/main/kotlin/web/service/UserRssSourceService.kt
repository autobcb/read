package web.service


import com.google.gson.reflect.TypeToken
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Configuration
import org.noear.solon.annotation.Inject
import org.noear.solon.data.cache.CacheService
import web.mapper.UserRssSourceMapper
import web.model.UserRssSource


@Configuration
open class UserRssSourceService {

    @Inject
    private lateinit var cache: CacheService

    @Db("db")
    @Inject
    lateinit  var userRssSourceMapper: UserRssSourceMapper

    private fun getlistkey(userid:String):String{
        return "userrsslist:$userid"
    }

    fun getallSourcelist( userid: String?): List<UserRssSource>{
        if(userid.isNullOrBlank()) return listOf()
        val t= object : TypeToken<List<UserRssSource>>() {}.type
        return cache.getOrStore(getlistkey(userid), t,300) {
            userRssSourceMapper.getallSourcelist(userid)
        }
    }

    fun getRssSource( source_url: String, userid: String): UserRssSource?{
        return userRssSourceMapper.getRssSource(source_url, userid)
    }

    fun changeorder(id: String, userid: String?,  sourceorder: Int): Int{
        cleancache(userid)
        return userRssSourceMapper.changeorder(id, sourceorder)
    }

    fun changeEnabled( id: String, userid: String?,  enabled: Boolean):Int{
        cleancache(userid)
        return userRssSourceMapper.changeEnabled(id,enabled)
    }

    fun delRssSource( source_url: String, userid: String): Int{
        cleancache(userid)
        return userRssSourceMapper.delRssSource(source_url,userid)
    }

    fun cleancache(userid: String?){
        if(userid.isNullOrBlank()) return
        cache.remove(getlistkey(userid))
    }
}