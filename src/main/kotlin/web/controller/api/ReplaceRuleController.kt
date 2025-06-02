package web.controller.api


import book.util.GSON
import book.util.fromJsonArray
import book.util.fromJsonObject
import org.noear.solon.annotation.Body
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.Tran
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.model.ReplaceRule
import web.model.Users
import web.response.*
import web.service.ReplaceRuleService

@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class ReplaceRuleController:BaseController() {

    
    @Inject
    lateinit var replaceRuleService: ReplaceRuleService

    @Inject(value = "\${default.rule:}", autoRefreshed=true)
    var rule:String=""

    @Mapping("/getdefaultrule")
    fun getdefaultrule(accessToken:String?) = run{
        JsonResponse(true).Data(rule)
    }


    @Tran
    @Mapping("/addReplaceRule")
    open fun addReplaceRule(accessToken:String?, @Body rule: ReplaceRule)=run{
        val user = getuserbytocken(accessToken)
        if(rule.name.isBlank()) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        if(rule.id.isNullOrBlank()){
            replaceRuleService.getrulebyname(user.id!!,rule.name).also {
                if(it.isNotEmpty()){
                    throw DataThrowable().data(JsonResponse(false, NAME_ERROR))
                }
            }
            rule.create(user.id!!,rule.name)
            rule.isEnabled = true
            replaceRuleService.replacementRuleMapper.insert(rule)
        }else{
            replaceRuleService.getrulebyname(user.id!!,rule.name).forEach{
                if(it.id != rule.id){
                    throw DataThrowable().data(JsonResponse(false, NAME_ERROR))
                }
            }
            rule.create(user.id!!,rule.name)
            replaceRuleService.replacementRuleMapper.deleteById(rule.id)
            replaceRuleService.replacementRuleMapper.insert(rule)
        }
        replaceRuleService.cleancache(user.id)
        JsonResponse(true)
    }

    @Mapping("/topReplaceRule")
    fun topReplaceRule( accessToken:String?, id: String?)= run{
        val user = getuserbytocken(accessToken)
        val rule= replaceRuleService.getrule(id?:throw DataThrowable().data(JsonResponse(false, NOT_BANK)) ,user.id!!) ?:
             throw DataThrowable().data(JsonResponse(false, NOT_IS))
        val rules=replaceRuleService.getallrule(user.id!!)
        var order=1
        for( it in rules){
            if(it.id == rule.id){
                replaceRuleService.changeorder(it.id?:"",user.id, 0)
            }else{
                replaceRuleService.changeorder(it.id?:"",user.id, order)
                order++
            }
        }
        JsonResponse(true)
    }

    @Mapping("/delReplaceRule")
    fun delReplaceRule(accessToken:String?,id: String?) = run{
        val user = getuserbytocken(accessToken)
        val rule= replaceRuleService.getrule(id?:throw DataThrowable().data(JsonResponse(false, NOT_BANK)) ,user.id!!) ?:
        throw DataThrowable().data(JsonResponse(false, NOT_IS))
        replaceRuleService.replacementRuleMapper.deleteById(rule.id)
        replaceRuleService.cleancache(user.id)
        JsonResponse(true)
    }

    @Mapping("/delReplaceRules")
    fun delReplaceRules(accessToken:String?,@Body ids: List<String>?) = run{
        val user = getuserbytocken(accessToken)
        ids?.forEach {id->
            if (id.isNotBlank()){
                replaceRuleService.getrule(id,user.id!!)?.let {
                   replaceRuleService.replacementRuleMapper.deleteById(id)
                    replaceRuleService.cleancache(user.id)
                }
            }
        }
        JsonResponse(true)
    }

    @Mapping("/getReplaceRules")
    fun getReplaceRules(accessToken:String?) = run{
        val user = getuserbytocken(accessToken)
        val rules=replaceRuleService.getallrule(user.id!!)
        JsonResponse(true).Data(rules)
    }

    @Mapping("/stopReplaceRules")
    fun stopReplaceRules(accessToken:String?,id: String? ,st: String?)= run{
        val user = getuserbytocken(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        replaceRuleService.getrule(id,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        when(st){
            "0"->{
                replaceRuleService.changeEnabled(id,user.id,false)
            }
            "1"->{
                replaceRuleService.changeEnabled(id,user.id,true)
            }
            else -> throw DataThrowable().data(JsonResponse(false, USE_ERROE))
        }
        JsonResponse(true)
    }

    @Mapping("/stopReplaceRulesbyIds")
    fun stopReplaceRulesbyIds(accessToken:String?,@Body ids: List<String>?)= run{
        val user = getuserbytocken(accessToken)
        ids?.forEach {
            if (it.isNotBlank()){
                val rule=
                    replaceRuleService.getrule(it,user.id!!)  ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                replaceRuleService.changeEnabled(rule.id!!,user.id,false)
            }
        }
        JsonResponse(true)
    }

    @Mapping("/startReplaceRulesbyIds")
    fun startReplaceRulesbyIds(accessToken:String?,@Body ids: List<String>?)= run{
        val user = getuserbytocken(accessToken)
        ids?.forEach {
            if (it.isNotBlank()){
                val rule=
                    replaceRuleService.getrule(it,user.id!!)  ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                replaceRuleService.changeEnabled(rule.id!!,user.id,true)
            }
        }
        JsonResponse(true)
    }

    @Mapping("/saverules")
    fun saverules(accessToken:String?, @Body content:String)=run{
        val user = getuserbytocken(accessToken)
        var insert = 0
        var update = 0
        val rules= GSON.fromJsonArray<ReplaceRule>(content).getOrNull()
        rules?.forEach {
            addorupdate(it,user).let {  (ins,ups)->
                insert += ins
                update += ups
            }
        }
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
        JsonResponse(true,"新增${insert}条规则，更新${update}条规则")
    }

    private fun addorupdate(rule: ReplaceRule, user: Users) = run{
        var insert = 0
        var update = 0
        if(rule.name.isEmpty()){
            return  Pair(insert, update)
        }
        replaceRuleService.getrulebyname(user.id!!,rule.name).let {
            if (it.isNotEmpty()){
                val r=it[0]
                rule.id=r.id
                rule.userid=r.userid
                rule.name = r.name
                rule.ruleorder=r.ruleorder
                update+=replaceRuleService.replacementRuleMapper.updateById(rule)
            }else{
                rule.create(user.id!!,rule.name)
                insert+= replaceRuleService.replacementRuleMapper.insert(rule)
            }
        }
        replaceRuleService.cleancache(user.id)
        Pair(insert, update)
    }
}