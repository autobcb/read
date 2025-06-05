package book.model


import book.appCtx
import book.util.*
import book.util.http.newCallStrResponse
import book.util.http.okHttpClient
import book.webBook.exception.NoStackTraceException
import com.google.gson.reflect.TypeToken
import com.script.ScriptBindings
import com.script.rhino.RhinoScriptEngine
import kotlinx.coroutines.runBlocking
import org.mozilla.javascript.Scriptable
import java.io.File
import java.lang.ref.WeakReference
import kotlin.collections.set

object SharedJsScope {

    private val cacheFolder = File(appCtx.cacheDir, "shareJs")
    private val aCache = ACache.get(cacheFolder)

    private val scopeMap = hashMapOf<String, WeakReference<Scriptable>>()

    fun getScope(jsLib: String?,userid:String ?): Scriptable? {
        //println("cacheFolder:$cacheFolder")
        if (jsLib.isNullOrBlank()) {
            return null
        }
        val key = MD5Utils.md5Encode("$userid:$jsLib")
        var scope = scopeMap[key]?.get()
        if (scope == null) {
            scope = RhinoScriptEngine.run {
                getRuntimeScope(ScriptBindings())
            }
            if (jsLib.isJsonObject()) {
                val jsMap: Map<String, String> = GSON.fromJson(
                    jsLib,
                    TypeToken.getParameterized(
                        Map::class.java,
                        String::class.java,
                        String::class.java
                    ).type
                )
                jsMap.values.forEach { value ->
                    if (value.isAbsUrl()) {
                        val fileName = MD5Utils.md5Encode("$userid:$value")
                        var js = aCache.getAsString(fileName)
                        if (js == null) {
                            js = runBlocking {
                                okHttpClient.newCallStrResponse {
                                    url(value)
                                }.body
                            }
                            if (js !== null) {
                                aCache.put(fileName, js)
                            } else {
                                throw NoStackTraceException("下载jsLib-${value}失败")
                            }
                        }
                        RhinoScriptEngine.eval(js, scope)
                    }
                }
            } else {
                RhinoScriptEngine.eval(jsLib, scope)
            }
            scopeMap[key] = WeakReference(scope)
        }
        return scope
    }

}