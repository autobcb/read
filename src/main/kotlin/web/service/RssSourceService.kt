package web.service

import com.google.gson.reflect.TypeToken
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Configuration
import org.noear.solon.annotation.Inject
import org.noear.solon.data.cache.CacheService
import web.mapper.RssSourceMapper
import web.model.RssSource

@Configuration
open class RssSourceService {

    @Inject
    private lateinit var cache: CacheService

    @Db("db")
    @Inject
    lateinit  var rssSourceMapper: RssSourceMapper

    private fun getlistkey():String{
        return "rsslist"
    }

    private fun getalllistkey():String{
        return "rssalllist"
    }


    fun getallSourcelist(): List<RssSource>?{
        val t= object : TypeToken<List<RssSource>>() {}.type
        return cache.getOrStore(getalllistkey(), t,300) {
            rssSourceMapper.getallSourcelist()
        }
    }

    fun getEnabledSourcelist(): List<RssSource>?{
        val t= object : TypeToken<List<RssSource>>() {}.type
        return cache.getOrStore(getlistkey(), t,300) {
            rssSourceMapper.getEnabledSourcelist()
        }
    }

    fun getRssSource( source_url: String): RssSource?{
        return  rssSourceMapper.getRssSource(source_url)
    }

    fun changeorder( source_url: String, sourceorder: Int):Int{
        cleancache()
        return rssSourceMapper.changeorder(source_url, sourceorder)
    }

    fun changeEnabled( source_url: String, enabled: Boolean):Int{
        cleancache()
        return rssSourceMapper.changeEnabled(source_url, enabled)
    }

    fun changegroup( bookSourceUrl: String?, group: String?):Int{
        cleancache()
        return rssSourceMapper.changegroup(bookSourceUrl?:"", group?:"")
    }

    fun cleancache(){
        cache.remove(getalllistkey())
        cache.remove(getlistkey())
    }
}