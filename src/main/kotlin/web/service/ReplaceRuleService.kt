package web.service

import com.google.gson.reflect.TypeToken
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Configuration
import org.noear.solon.annotation.Inject
import org.noear.solon.data.cache.CacheService
import web.mapper.ReplaceRuleMapper
import web.model.ReplaceRule

@Configuration
open  class ReplaceRuleService {

    @Inject
    private lateinit var cache: CacheService

    @Db("db")
    @Inject
    lateinit  var replacementRuleMapper: ReplaceRuleMapper

    private fun getlistkey(userid: String):String{
        return "replace$userid"
    }


    fun getrule( id: String?,  userid: String?): ReplaceRule?{
        if(id.isNullOrBlank() || userid.isNullOrBlank()) return null
        return  replacementRuleMapper.getrule(id, userid)
    }

    fun getrulebyname( userid: String?, name: String?): List<ReplaceRule>{
        if(name.isNullOrBlank() || userid.isNullOrBlank()) return listOf()
        return  replacementRuleMapper.getrulebyname(userid, name)
    }

    fun getrulebybookname(userid: String?, name: String?): List<ReplaceRule>{
        if(name.isNullOrBlank() || userid.isNullOrBlank()) return listOf()
        return  replacementRuleMapper.getrulebybookname(userid, name)
    }

    fun getallrule(userid: String?): List<ReplaceRule>{
        if(userid.isNullOrBlank()) return listOf()
        val t= object : TypeToken<List<ReplaceRule>>() {}.type
        return cache.getOrStore(getlistkey(userid), t,300) {
            replacementRuleMapper.getallrule(userid)
        }
    }

    fun changeorder( id: String,userid: String?,  ruleorder: Int):Int{
        cleancache(userid)
        return replacementRuleMapper.changeorder(id, ruleorder)
    }

    fun changeEnabled( id: String,userid: String?,  enabled: Boolean):Int{
        cleancache(userid)
        return replacementRuleMapper.changeEnabled(id, enabled)
    }

    fun delUserrule( id: String?): Int{
        if(id.isNullOrBlank()) return 0
        cleancache(id)
        return replacementRuleMapper.delUserrule(id)
    }

    fun cleancache(userid: String?){
        if(userid.isNullOrBlank()) return
        cache.remove(getlistkey(userid))
    }
}