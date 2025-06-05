package web.service

import com.google.gson.reflect.TypeToken
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Configuration
import org.noear.solon.annotation.Inject
import org.noear.solon.data.cache.CacheService
import web.mapper.HttpTTSMapper
import web.model.HttpTts

@Configuration
open class HttpTTSService {

    @Inject
    private lateinit var cache: CacheService

    @Db("db")
    @Inject
    lateinit  var httpTTSMapper: HttpTTSMapper

    private fun getlistkey(userid: String):String{
        return "tts$userid"
    }

    fun gettts( id: String,  userid: String): HttpTts?{
        return httpTTSMapper.gettts(id, userid)
    }

    fun getttsbyname( userid: String, name: String): List<HttpTts>{
        return httpTTSMapper.getttsbyname(userid, name)
    }

    fun getalltts( userid: String?): List<HttpTts>{
        if(userid.isNullOrBlank()) return listOf()
        val t= object : TypeToken<List<HttpTts>>() {}.type
        return cache.getOrStore(getlistkey(userid), t,300) {
            httpTTSMapper.getalltts(userid)
        }
    }

    fun cleancache(userid: String?){
        if(userid.isNullOrBlank()) return
        cache.remove(getlistkey(userid))
    }
}