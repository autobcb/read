package web.controller.api


import book.util.GSON
import book.util.fromJsonArray
import book.util.fromJsonObject
import org.noear.solon.annotation.Body
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.Cache
import org.noear.solon.data.annotation.Tran
import org.noear.solon.data.cache.CacheService
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.mapper.ReplaceRuleMapper
import web.model.HttpTts
import web.model.ReplaceRule
import web.model.Users
import web.response.*
import web.util.hash.Md5

@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class ReplaceRuleController:BaseController() {

    
    @Inject
    lateinit var replaceRuleMapper: ReplaceRuleMapper


    @Inject
    lateinit var cacheService: CacheService


    @Inject(value = "\${default.rule:}", autoRefreshed=true)
    var rule:String=""


    @Mapping("/getdefaultrule")
    fun getdefaultrule(accessToken:String?) = run{
        getuserbytocken(accessToken)
        JsonResponse(true).Data(rule)
    }


    @Mapping("/getReplaceRulesPage")
    fun getReplaceRules(accessToken:String?) = run{
        val user = getuserbytocken(accessToken)
        if (user.replacemd5.isNullOrBlank()){
            user.replacemd5=Md5(System.currentTimeMillis().toString())
            usersMapper.updatereplacemd5(user.id!!, user.replacemd5!!)
        }
        var list:MutableList<ReplaceRule> = mutableListOf()
        var page = 1
        replaceRuleMapper.getallrule(user.id!!).forEach {
            if(list.size >= 50){
                cacheService.store("getReplaceRulesNew:${accessToken}${user.replacemd5}${page}",JsonResponse(true).Data(list),cachetime)
                page++
                list=mutableListOf()
            }
            list.add(it)
        }
        cacheService.store("getReplaceRulesNew:${accessToken}${user.replacemd5}${page}",JsonResponse(true).Data(list),cachetime)
        JsonResponse(true).Data(mapOf("page" to page, "md5" to user.replacemd5))
    }

    @Cache(key = "getReplaceRulesNew:\${accessToken}\${md5}\${page}",  seconds = 60)
    @Mapping("/getReplaceRulesNew")
    open fun getBookshelf(accessToken: String?,md5: String?,page: String) = run {
        JsonResponse(false)
    }

    @Tran
    @Mapping("/addReplaceRule")
    open fun addReplaceRule(accessToken:String?, @Body rule: ReplaceRule)=run{
        val user = getuserbytocken(accessToken)
        if(rule.name.isBlank()) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        if(rule.id.isNullOrBlank()){
            replaceRuleMapper.getrulebyname(user.id!!,rule.name).also {
                if(it.isNotEmpty()){
                    throw DataThrowable().data(JsonResponse(false, NAME_ERROR))
                }
            }
            rule.create(user.id!!,rule.name)
            rule.isEnabled = true
            replaceRuleMapper.insert(rule)
        }else{
            replaceRuleMapper.getrulebyname(user.id!!,rule.name).forEach{
                if(it.id != rule.id){
                    throw DataThrowable().data(JsonResponse(false, NAME_ERROR))
                }
            }
            rule.create(user.id!!,rule.name)
            replaceRuleMapper.deleteById(rule.id)
            replaceRuleMapper.insert(rule)
        }
        web.notification.ReplaceRule.sendNotification(user)
        JsonResponse(true)
    }

    @Mapping("/topReplaceRule")
    fun topReplaceRule( accessToken:String?, id: String?)= run{
        val user = getuserbytocken(accessToken)
        val rule= replaceRuleMapper.getrule(id?:throw DataThrowable().data(JsonResponse(false, NOT_BANK)) ,user.id!!) ?:
             throw DataThrowable().data(JsonResponse(false, NOT_IS))
        val rules=replaceRuleMapper.getallrule(user.id!!)
        var order=1
        for( it in rules){
            if(it.id == rule.id){
                replaceRuleMapper.changeorder(it.id?:"", 0)
            }else{
                replaceRuleMapper.changeorder(it.id?:"", order)
                order++
            }
        }
        web.notification.ReplaceRule.sendNotification(user)
        JsonResponse(true)
    }

    @Mapping("/delReplaceRule")
    fun delReplaceRule(accessToken:String?,id: String?) = run{
        val user = getuserbytocken(accessToken)
        val rule= replaceRuleMapper.getrule(id?:throw DataThrowable().data(JsonResponse(false, NOT_BANK)) ,user.id!!) ?:
        throw DataThrowable().data(JsonResponse(false, NOT_IS))
        replaceRuleMapper.deleteById(rule.id)
        web.notification.ReplaceRule.sendNotification(user)
        JsonResponse(true)
    }

    @Mapping("/delReplaceRules")
    fun delReplaceRules(accessToken:String?,@Body ids: List<String>?) = run{
        val user = getuserbytocken(accessToken)
        ids?.forEach {id->
            if (id.isNotBlank()){
               runCatching {
                   replaceRuleMapper.getrule(id,user.id!!)?.let {
                       replaceRuleMapper.deleteById(id)
                   }
               }
            }
        }
        web.notification.ReplaceRule.sendNotification(user)
        JsonResponse(true)
    }



    @Mapping("/stopReplaceRules")
    fun stopReplaceRules(accessToken:String?,id: String? ,st: String?)= run{
        val user = getuserbytocken(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        replaceRuleMapper.getrule(id,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        when(st){
            "0"->{
                replaceRuleMapper.changeEnabled(id,false)
            }
            "1"->{
                replaceRuleMapper.changeEnabled(id,true)
            }
            else -> throw DataThrowable().data(JsonResponse(false, USE_ERROE))
        }
        web.notification.ReplaceRule.sendNotification(user)
        JsonResponse(true)
    }

    @Mapping("/stopReplaceRulesbyIds")
    fun stopReplaceRulesbyIds(accessToken:String?,@Body ids: List<String>?)= run{
        val user = getuserbytocken(accessToken)
        ids?.forEach {
            if (it.isNotBlank()){
                val rule=
                    replaceRuleMapper.getrule(it,user.id!!)  ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                replaceRuleMapper.changeEnabled(rule.id!!,false)
            }
        }
        web.notification.ReplaceRule.sendNotification(user)
        JsonResponse(true)
    }

    @Mapping("/startReplaceRulesbyIds")
    fun startReplaceRulesbyIds(accessToken:String?,@Body ids: List<String>?)= run{
        val user = getuserbytocken(accessToken)
        ids?.forEach {
            if (it.isNotBlank()){
                val rule=
                    replaceRuleMapper.getrule(it,user.id!!)  ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                replaceRuleMapper.changeEnabled(rule.id!!,true)
            }
        }
        web.notification.ReplaceRule.sendNotification(user)
        JsonResponse(true)
    }

    @Mapping("/saverules")
    fun saverules(accessToken:String?, @Body content:String)=run{
        val user = getuserbytocken(accessToken)
        var insert = 0
        var update = 0
        val rules= GSON.fromJsonArray<ReplaceRule>(content).getOrNull()
        rules?.forEach {
            runCatching {
                addorupdate(it,user).let {  (ins,ups)->
                    insert += ins
                    update += ups
                }
            }
        }
        web.notification.ReplaceRule.sendNotification(user)
        JsonResponse(true,"新增${insert}条规则，更新${update}条规则")
    }

    @Mapping("/saverule")
    fun saverule( accessToken:String?, @Body content:String)=run{
        val user = getuserbytocken(accessToken)
        var insert = 0
        var update = 0
        val rule= GSON.fromJsonObject<ReplaceRule>(content).getOrNull()
        if (rule != null  && rule.name.isNotEmpty())
            addorupdate(rule, user ).let {  (ins,ups)->
                insert += ins
                update += ups
            }
        web.notification.ReplaceRule.sendNotification(user)
        JsonResponse(true,"新增${insert}条规则，更新${update}条规则")
    }

    private fun addorupdate(rule: ReplaceRule, user: Users) = run{
        var insert = 0
        var update = 0
        if(rule.name.isEmpty()){
            return  Pair(insert, update)
        }
        replaceRuleMapper.getrulebyname(user.id!!,rule.name).let {
            if (it.isNotEmpty()){
                val r=it[0]
                rule.id=r.id
                rule.userid=r.userid
                rule.name = r.name
                rule.ruleorder=r.ruleorder
                update+=replaceRuleMapper.updateById(rule)
            }else{
                rule.create(user.id!!,rule.name)
                insert+= replaceRuleMapper.insert(rule)
            }
        }
        Pair(insert, update)
    }
}