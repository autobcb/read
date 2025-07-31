package book.model

import book.app.App
import book.util.*
import book.util.help.CacheManager
import book.util.help.CookieStore
import book.util.help.RuleBigDataHelp
import book.webBook.DebugLog
import book.webBook.analyzeRule.JsExtensions
import book.webBook.analyzeRule.RssJsExtensions
import com.script.ScriptBindings
import com.script.buildScriptBindings
import com.script.rhino.RhinoScriptEngine
import com.script.rhino.runScriptWithContext
import org.mozilla.javascript.Scriptable
import java.io.InputStream

/**
 * 可在js里调用,source.xxx()
 */
@Suppress("unused")
interface BaseSource : JsExtensions {
    //var bookSourceUrl: String
    var concurrentRate: String? // 并发率
    var loginUrl: String?       // 登录地址
    var loginUi: String?   // 登录UI
    var header: String?         // 请求头
    var jsLib:String?
    var userid:String?
    var usertocken:String?

    /**
     * 启用cookieJar
     */
    var enabledCookieJar: Boolean?

    var phonehttp: Boolean?


    fun getTag(): String

    fun getKey(): String

    override fun getSource(): BaseSource? {
        return this
    }



    fun getLoginJs(): String? {
        val loginJs = loginUrl
        return when {
            loginJs == null -> null
            loginJs.startsWith("@js:") -> loginJs.substring(4)
            loginJs.startsWith("<js>") -> loginJs.substring(4, loginJs.lastIndexOf("<"))
            else -> loginJs
        }
    }
    /**
     * 调用login函数 实现登录请求
     */
    fun login() {
        val loginJs = getLoginJs()
        if (!loginJs.isNullOrBlank()) {
            val js = """$loginJs
                if(typeof login=='function'){
                    login.apply(this);
                } else {
                    throw('Function login not implements!!!')
                }
            """.trimIndent()
            evalJS(js)
        }
    }

    /**
     * 解析header规则
     */
    fun getHeaderMap(hasLoginHeader: Boolean = false) = HashMap<String, String>().apply {
        //this[AppConst.UA_NAME] = AppConst.userAgent
        runCatching {
            header?.let {
                GSON.fromJsonObject<Map<String, String>>(
                    when {
                        it.startsWith("@js:", true) ->
                            evalJS(it.substring(4)).toString()
                        it.startsWith("<js>", true) ->
                            evalJS(it.substring(4, it.lastIndexOf("<"))).toString()
                        else -> it
                    }
                ).getOrNull()?.let { map ->
                    putAll(map)
                }
            }
        }.onFailure {
            App.log("${getKey()}:格式化header错误:${it.message}",usertocken?:"")
        }
        if (!has(AppConst.UA_NAME, true) ) {
            put(AppConst.UA_NAME, AppConst.userAgent)
        }
        if (hasLoginHeader) {
            getLoginHeaderMap()?.let {
                putAll(it)
            }
        }
    }

    /**
     * 获取用于登录的头部信息
     */
    fun getLoginHeader(): String? {
        return  RuleBigDataHelp.getSourceVariable(getKey(),userid?:"","loginHeader")
    }

    fun getLoginHeaderMap(): Map<String, String>? {
        val cache = getLoginHeader() ?: return null
        return GSON.fromJsonObject<Map<String, String>>(cache).getOrNull()
    }

    /**
     * 保存登录头部信息,map格式,访问时自动添加
     */
    fun putLoginHeader(header: String) {
        RuleBigDataHelp.putSourceVariable(getKey(),userid?:"","loginHeader",header)
    }

    fun removeLoginHeader() {
        RuleBigDataHelp.putSourceVariable(getKey(),userid?:"","loginHeader",null)
    }

    /**
     * 获取用户信息,可以用来登录
     * 用户信息采用aes加密存储
     */
    fun getLoginInfo(): String? {
        try {
            val cache = RuleBigDataHelp.getSourceVariable(getKey(),userid?:"","userInfo")
            return cache
        } catch (e: Exception) {
            e.printStackTrace()
            log("获取登陆信息出错 " + e.localizedMessage)
            return null
        }
    }

    fun getLoginInfoMap(): Map<String, String>? {
        return GSON.fromJsonObject<Map<String, String>>(getLoginInfo()).getOrNull()
    }




    /**
     * 保存用户信息,aes加密
     */
    fun putLoginInfo(info: String): Boolean {
        return try {
            RuleBigDataHelp.putSourceVariable(getKey(),userid?:"","userInfo",info)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            log("保存登陆信息出错 " + e.localizedMessage)
            false
        }
    }

    fun removeLoginInfo() {
        RuleBigDataHelp.putSourceVariable(getKey(),userid?:"","userInfo",null)
    }



    fun setVariable(variable: String?) {
        RuleBigDataHelp.putSourceVariable(getKey(),userid?:"","sourceVariable",variable)
    }

    fun getVariable(): String {
       return RuleBigDataHelp.getSourceVariable(getKey(),userid?:"","sourceVariable")?:""
    }

    suspend fun runaction(action:String){
        val js =getLoginJs() + "\n$action"
        evalJS(js) {
            put("result", getLoginInfoMap()?: mapOf<String,String>())
        }
    }

    fun shouldOverrideUrlLoadingdo(js:String ,url: String): String? {
        val result =runCatching {
           evalJS(js) {
                put("java", RssJsExtensions(source = getSource()))
                put("url", url)
            }.toString()
        }.onFailure {
            it.printStackTrace()
            App.log("${getTag()}: url跳转拦截js出错:${it.message}",usertocken?:"")
        }.getOrNull()
        return  result
    }

    /**
     * 执行JS
     */
    @Throws(Exception::class)
    fun evalJS(jsStr: String, bindingsConfig: ScriptBindings.() -> Unit = {}): Any? {
        val bindings = buildScriptBindings { bindings ->
            bindings.apply(bindingsConfig)
            if (!bindings.containsKey("java")){
                bindings["java"] = this
            }
            bindings["source"] = this
            bindings["baseUrl"] = getKey()
            bindings["cookie"] =  getCookieManger()
            bindings["cache"] = getCacheManger()
            binding(bindings)
        }
        val scope = RhinoScriptEngine.getRuntimeScope(bindings)
        getShareScope()?.let {
            scope.prototype = it
        }
        return RhinoScriptEngine.eval(getjs(jsStr), scope)
    }

    fun getShareScope(): Scriptable? {
        return SharedJsScope.getScope(if(jsLib != null )getjs(jsLib?:"") else jsLib,userid?:"")
    }



    /**
     * 保存数据
     */
    fun put(key: String, value: String): String {
        //println("sourceput: $key: $value")
        RuleBigDataHelp.putSourceVariable(getKey(),userid?:"","getv_${key}",value)
        return value
    }

    /**
     * 获取保存的数据
     */
    fun get(key: String): String {
        //println("sourceget: $key")
        return  RuleBigDataHelp.getSourceVariable(getKey(),userid?:"","getv_${key}") ?:""
    }


    fun getcookieJarHeaderid():String{
        return MD5Utils.md5Encode(userid!!)
    }


    fun  getCacheManger() :CacheManager{
        val store=CacheManager(userid!!)
        return store
    }


    fun  getCookieManger() :CookieStore{
        val store=CookieStore(userid!!)
        val key=getcookieJarHeaderid()
        if(!CookieStore.Stores.containsKey(key)){
            CookieStore.Stores.put(key, store)
        }
        return store
    }

    fun loginUi(): List<RowUi>? {
        return GSON.fromJsonArray<RowUi>(loginUi).onFailure {
            it.printOnDebug()
        }.getOrNull()
    }


}