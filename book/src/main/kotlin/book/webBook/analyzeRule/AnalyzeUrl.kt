package book.webBook.analyzeRule

import book.app.App
import book.model.BaseSource
import book.model.Book
import book.model.BookChapter
import book.util.*
import book.util.AppConst.UA_NAME
import book.util.AppPattern.JS_PATTERN
import book.util.AppPattern.dataUriRegex
import book.util.Base64
import book.util.help.CacheManager
import book.util.help.cookieJarHeader
import book.util.http.*
import book.webBook.DebugLog
import com.script.buildScriptBindings
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.URLEncoder
import java.util.regex.Pattern
import com.script.rhino.RhinoScriptEngine
import okhttp3.OkHttpClient
import okhttp3.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.math.max


class AnalyzeUrl(
    val mUrl: String,
    val key: String? = null,
    val page: Int? = null,
    val speakText: String? = null,
    val speakSpeed: Int? = null,
    var baseUrl: String = "",
    private val source: BaseSource? = null,
    private val ruleData: RuleDataInterface? = null,
    private val readTimeout: Long? = null,
    private val chapter: BookChapter? = null,
    private var coroutineContext: CoroutineContext = EmptyCoroutineContext,
    headerMapF: Map<String, String>? = null,
    val needanalyzeUrl :Boolean=true,
    override  var debugLog: DebugLog?,hasLoginHeader: Boolean = true
) :JsExtensions {

    companion object {
        val paramPattern: Pattern = Pattern.compile("\\s*,\\s*(?=\\{)")
        private val pagePattern = Pattern.compile("<(.*?)>")
        private val concurrentRecordMap = hashMapOf<String, ConcurrentRecord>()
    }

    override val logger: Logger
        get() =  LoggerFactory.getLogger(AnalyzeUrl::class.java)

    var ruleUrl = ""
        private set
    var url: String = ""
        private set
    var body: String? = null
        private set
    var type: String? = null
        private set
    val headerMap = HashMap<String, String>()

    private var urlNoQuery: String = ""
    private var queryStr: String? = null
    private val fieldMap = LinkedHashMap<String, String>()
    private var charset: String? = null
    private var method = RequestMethod.GET
    private var proxy: String? = null
    private var retry: Int = 0
    private var useWebView: Boolean = false
    private var usePhone: Boolean = false
    private var webJs: String? = null
    private val enabledCookieJar = source?.enabledCookieJar ?: false
    private val concurrentRateLimiter = ConcurrentRateLimiter(source)
    private val domain: String


    init {
        if(source != null) {
            runCatching {
                headerMap.putAll(source!!.getHeaderMap(hasLoginHeader))
            }
            if (headerMap.containsKey("proxy")) {
                proxy = headerMap["proxy"]
                headerMap.remove("proxy")
            }
        }
        initUrl()
        domain = NetworkUtils.getSubDomain(source?.getKey() ?: url)
    }


    fun initUrl() {

        coroutineContext = coroutineContext.minusKey(ContinuationInterceptor)
        if ( chapter != null ) {
            if(source != null){
                chapter.userid=source.userid?:""
                ruleData?.userid=source.userid?:""
            }
        }
        ruleUrl = mUrl

        //执行@js,<js></js>
        analyzeJs()
        //println("ruleUrl1 $ruleUrl")
        //替换参数
        if(needanalyzeUrl)  replaceKeyPageJs()
        //println("ruleUrl2 $ruleUrl")

        //处理URL
        if(needanalyzeUrl) analyzeUrl()

        logger.info("ruleUrl $ruleUrl")
        //debugLog?.log(source?. getKey(), ruleUrl)
    }

    override fun toString(): String {
        val hashCode = this.hashCode()
        val hexHash = Integer.toHexString(hashCode)
        val s="io.legado.app.model.analyzeRule.AnalyzeUrl@"+hexHash
        return s
    }

    private fun analyzeJs() {
        var start = 0
        val jsMatcher = JS_PATTERN.matcher(ruleUrl)
        var result = ruleUrl
        while (jsMatcher.find()) {
            if (jsMatcher.start() > start) {
                ruleUrl.substring(start, jsMatcher.start()).trim().let {
                    if (it.isNotEmpty()) {
                        result = it.replace("@result", result)
                    }
                }
            }
            result = evalJS(jsMatcher.group(2) ?: jsMatcher.group(1), result).toString()
            start = jsMatcher.end()
        }
        if (ruleUrl.length > start) {
            ruleUrl.substring(start).trim().let {
                if (it.isNotEmpty()) {
                    result = it.replace("@result", result)
                }
            }
        }
        ruleUrl = result
    }

    /**
     * 替换关键字,页数,JS
     */
    private fun replaceKeyPageJs() { //先替换内嵌规则再替换页数规则，避免内嵌规则中存在大于小于号时，规则被切错
        //js
        if (ruleUrl.contains("{{") && ruleUrl.contains("}}")) {
            val analyze = RuleAnalyzer(ruleUrl) //创建解析
            //替换所有内嵌{{js}}
            val url = analyze.innerRule("{{", "}}") {
                val jsEval = evalJS(it) ?: ""
                when {
                    jsEval is String -> jsEval
                    jsEval is Double && jsEval % 1.0 == 0.0 -> String.format("%.0f", jsEval)
                    else -> jsEval.toString()
                }
            }
            if (url.isNotEmpty()) ruleUrl = url
        }
        //page
        page?.let {
            val matcher = pagePattern.matcher(ruleUrl)
            while (matcher.find()) {
                val pages = matcher.group(1)!!.split(",")
                ruleUrl = if (page < pages.size) { //pages[pages.size - 1]等同于pages.last()
                    ruleUrl.replace(matcher.group(), pages[page - 1].trim { it <= ' ' })
                } else {
                    ruleUrl.replace(matcher.group(), pages.last().trim { it <= ' ' })
                }
            }
        }
        //println(ruleUrl)
    }

    /**
     * 解析Url
     */
    private fun analyzeUrl() {
        //replaceKeyPageJs已经替换掉额外内容，此处url是基础形式，可以直接切首个‘,’之前字符串。
        val urlMatcher = paramPattern.matcher(ruleUrl)
        val urlNoOption =
            if (urlMatcher.find()) ruleUrl.substring(0, urlMatcher.start()) else ruleUrl
        url = NetworkUtils.getAbsoluteURL(baseUrl, urlNoOption)
        NetworkUtils.getBaseUrl(url)?.let {
            baseUrl = it
        }
        if (urlNoOption.length != ruleUrl.length) {
            GSON.fromJsonObject<UrlOption>(ruleUrl.substring(urlMatcher.end())).getOrNull()
                ?.let { option ->
                    option.getMethod()?.let {
                        if (it.equals("POST", true)) method = RequestMethod.POST
                    }
                    option.getHeaderMap()?.forEach { entry ->
                        headerMap[entry.key.toString()] = entry.value.toString()
                    }
                    option.getBody()?.let {
                        body = it
                    }
                    type = option.getType()
                    charset = option.getCharset()
                    retry = option.getRetry()
                    useWebView = option.useWebView()
                    usePhone = option.usePhone()
                    webJs = option.getWebJs()
                    option.getJs()?.let { jsStr ->
                        //println(jsStr)
                        evalJS(jsStr, url)?.toString()?.let {
                            url = it
                        }
                    }
                }
        }
        headerMap[UA_NAME] ?: let {
            headerMap[UA_NAME] = AppConst.userAgent
        }
        urlNoQuery = url
        when (method) {
            RequestMethod.GET -> {
                val pos = url.indexOf('?')
                if (pos != -1) {
                    analyzeFields(url.substring(pos + 1))
                    urlNoQuery = url.substring(0, pos)
                }
            }
            RequestMethod.POST -> body?.let {
                if (!it.isJson() && !it.isXml() && headerMap["Content-Type"].isNullOrEmpty()) {
                    analyzeFields(it)
                }
            }
        }
    }
    /**
     * 解析QueryMap
     */
    private fun analyzeFields(fieldsTxt: String) {
        queryStr = fieldsTxt
        val queryS = fieldsTxt.splitNotBlank("&")
        for (query in queryS) {
            val pos = query.indexOf('=')
            val value =if (pos != -1 && pos+1 != query.length ) query.substring(pos+1, query.length) else ""
            val queryM = if (pos != -1 && pos != 0 ) query.substring(0, pos) else ""
            if (charset.isNullOrEmpty()) {
                if (NetworkUtils.hasUrlEncoded(value)) {
                    fieldMap[queryM] = value
                } else {
                    fieldMap[queryM] = URLEncoder.encode(value, "UTF-8")
                }
            } else if (charset == "escape") {
                fieldMap[queryM] = EncoderUtils.escape(value)
            } else {
                fieldMap[queryM] = URLEncoder.encode(value, charset)
            }
        }
    }


    fun evalJS(jsStr: String, result: Any? = null): Any? {
        var userid=""
        if(source != null){
            userid = source.userid?:""
            chapter?.userid = userid
            ruleData?.userid = userid
        }
        val bindings = buildScriptBindings { bindings ->
            bindings["java"] = this
            bindings["baseUrl"] = baseUrl
            bindings["cookie"] =source?.getCookieManger()!!
            bindings["cache"] = source?.getCacheManger()!!
            bindings["page"] = page
            bindings["key"] = key
            bindings["speakText"] = speakText
            bindings["speakSpeed"] = speakSpeed
            bindings["book"] = ruleData as? Book
            bindings["source"] = source
            bindings["result"] = result
            binding(bindings)
        }
        val scope = RhinoScriptEngine.getRuntimeScope(bindings)
        source?.getShareScope()?.let {
            scope.prototype = it
        }

        return RhinoScriptEngine.eval(getjs(jsStr), scope, coroutineContext)
    }

    fun put(key: String, value: String): String {
        if(source != null){
            val userid = source.userid?:""
            chapter?.userid = userid
            ruleData?.userid = userid
        }
        //println("put key: $key")
        chapter?.putVariable(key, value)
            ?: ruleData?.putVariable(key, value)
        return value
    }

    fun setCookie() {
        val store=source?.getCookieManger()
        val cookie = (store?.getCookie(urlNoQuery))?:""
        if (cookie.isNotEmpty()) {
            var key="Cookie"
            headerMap.keys.forEach {
                if(it.lowercase(Locale.getDefault()) == "Cookie".lowercase(Locale.getDefault())){
                    key = it
                }
            }
            val oldc=headerMap[key]
            headerMap.remove(key)
            store?.mergeCookies( oldc,cookie)?.let {
                headerMap.put("Cookie", it)
            }
        }
        if (enabledCookieJar) {
             source?.getcookieJarHeaderid()?:""
        } else {
            headerMap.remove(cookieJarHeader)
        }
    }

    /**
     * 访问网站,返回StrResponse
     */
    suspend fun getStrResponseAwait(
        jsStr: String? = null,
        sourceRegex: String? = null,
        useWebView: Boolean = true
    ): StrResponse {
        if (type != null) {
            return StrResponse(url, StringUtils.byteToHexString(getByteArrayAwait()))
        }
        logger.info("ajaxurl:$urlNoQuery,type:$type")
        var strResponse: StrResponse
        concurrentRateLimiter.withLimit{
            setCookie()
            if (this.useWebView && useWebView) {
                when (method) {
                    RequestMethod.POST -> {
                        if(headerMap["Content-Type"].isNullOrBlank()){
                            if (fieldMap.isNotEmpty() || body.isNullOrBlank()) {
                                headerMap["Content-Type"]="application/x-www-form-urlencoded"
                                body = fieldMap.entries.joinToString("&") {
                                    "${it.key}=${it.value}"
                                }
                            } else {
                                headerMap["Content-Type"]="application/json; charset=UTF-8"
                            }
                        }
                        return  App.webviewbody("",url,webJs ?: jsStr,getSource()?.usertocken?:"",GSON.toJson(headerMap),body?:"",sourceRegex?:"","")
                    }
                    else -> {
                        return  App.webview("",url,webJs ?: jsStr,getSource()?.usertocken?:"",GSON.toJson(headerMap),sourceRegex?:"","")
                    }
                }
                //return  webview("",url,"")
            } else  if(source?.phonehttp == true || this.usePhone){
                when (method) {
                    RequestMethod.POST -> {
                        if(headerMap["Content-Type"].isNullOrBlank()){
                            if (fieldMap.isNotEmpty() || body.isNullOrBlank()) {
                                headerMap["Content-Type"]="application/x-www-form-urlencoded"
                                body = fieldMap.entries.joinToString("&") {
                                    "${it.key}=${it.value}"
                                }
                            } else {
                                headerMap["Content-Type"]="application/json; charset=UTF-8"
                            }
                        }
                        val body=App.post(url,body?:"" ,GSON.toJson(headerMap),getSource()?.usertocken?:"",true)
                        return  StrResponse(body.url,body.body())
                    }
                    else -> {
                        val body=App.get(url,GSON.toJson(headerMap),getSource()?.usertocken?:"",true)
                        return  StrResponse(body.url,body.body())
                    }
                }
            }else {
                strResponse = getClient().newCallStrResponse(retry) {
                   // println(GSON.toJson(headerMap))
                    addHeaders(headerMap)
                    when (method) {
                        RequestMethod.POST -> {
                            url(urlNoQuery)
                            val contentType = headerMap["Content-Type"]
                            val body = body
                            if (fieldMap.isNotEmpty() || body.isNullOrBlank()) {
                                postForm(fieldMap, true)
                            } else if (!contentType.isNullOrBlank()) {
                                val requestBody = body.toRequestBody(contentType.toMediaType())
                                post(requestBody)
                            } else {
                                postJson(body)
                            }
                        }
                        else -> get(urlNoQuery, fieldMap, true)
                    }
                }.let {
                    val isXml = it.raw.body?.contentType()?.toString()
                        ?.matches(AppPattern.xmlContentTypeRegex) == true
                    if (isXml && it.body?.trim()?.startsWith("<?xml", true) == false) {
                        StrResponse(it.raw, "<?xml version=\"1.0\"?>" + it.body)
                    } else it
                }
                return strResponse
            }
        }
    }


    @JvmOverloads
    fun getStrResponse(
        jsStr: String? = null,
        sourceRegex: String? = null,
        useWebView: Boolean = true,
    ): StrResponse {
        return runBlocking {
            getStrResponseAwait(jsStr, sourceRegex, useWebView)
        }
    }


    /**
     * 访问网站,返回Response
     */
    suspend fun getResponseAwait(): Response {
        concurrentRateLimiter.withLimit {
            setCookie()
            val response = getClient().newCallResponse(retry) {
                addHeaders(headerMap)
                when (method) {
                    RequestMethod.POST -> {
                       // println("urlNoQuery:$urlNoQuery")
                        url(urlNoQuery)
                        val contentType = headerMap["Content-Type"]
                        val body = body
                        if (fieldMap.isNotEmpty() || body.isNullOrBlank()) {
                            postForm(fieldMap, true)
                        } else if (!contentType.isNullOrBlank()) {
                            val requestBody = body.toRequestBody(contentType.toMediaType())
                            post(requestBody)
                        } else {
                            postJson(body)
                        }
                    }

                    else -> get(urlNoQuery, fieldMap, true)
                }
            }

            return response
        }
    }

    private fun getClient(): OkHttpClient {
        val client = getProxyClient(proxy)
        if (readTimeout == null) {
            return client
        }
        return client.newBuilder()
            .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
            .callTimeout(max(60 * 1000L, readTimeout * 2), TimeUnit.MILLISECONDS)
            .build()
    }

    fun getResponse(): Response {
        return runBlocking(coroutineContext) {
            getResponseAwait()
        }
    }

    private fun getByteArrayIfDataUri(): ByteArray? {
        val dataUriFindResult = dataUriRegex.find(urlNoQuery)
        if (dataUriFindResult != null) {
            val dataUriBase64 = dataUriFindResult.groupValues[1]
            val byteArray = Base64.decode(dataUriBase64, Base64.DEFAULT)
            return byteArray
        }
        return null
    }

    /**
     * 访问网站,返回ByteArray
     */
    suspend fun getByteArrayAwait(): ByteArray {
        getByteArrayIfDataUri()?.let {
            return it
        }
        return getResponseAwait().body!!.bytes()
    }

    fun getByteArray(): ByteArray {
        return runBlocking(coroutineContext) {
            getByteArrayAwait()
        }
    }

    /**
     * 访问网站,返回InputStream
     */
    suspend fun getInputStreamAwait(): InputStream {
        getByteArrayIfDataUri()?.let {
            return ByteArrayInputStream(it)
        }
        return getResponseAwait().body!!.byteStream()
    }

    fun getInputStream(): InputStream {
        return runBlocking(coroutineContext) {
            getInputStreamAwait()
        }
    }

    /**
     * 上传文件
     */
    suspend fun upload(fileName: String, file: Any, contentType: String): StrResponse {
        return getProxyClient(proxy).newCallStrResponse(retry) {
            url(urlNoQuery)
            val bodyMap = GSON.fromJsonObject<HashMap<String, Any>>(body).getOrNull()!!
            bodyMap.forEach { entry ->
                if (entry.value.toString() == "fileRequest") {
                    bodyMap[entry.key] = mapOf(
                        Pair("fileName", fileName),
                        Pair("file", file),
                        Pair("contentType", contentType)
                    )
                }
            }
            postMultipart(type, bodyMap)
        }
    }


    fun getUserAgent(): String {
        return headerMap.get(UA_NAME, true) ?: AppConst.userAgent
    }

    fun isPost(): Boolean {
        return method == RequestMethod.POST
    }



    override fun getSource(): BaseSource? {
        return source
    }

    data class UrlOption(
        private var method: String? = null,
        private var charset: String? = null,
        private var headers: Any? = null,
        private var body: Any? = null,
        private var retry: Int? = null,
        private var type: String? = null,
        private var webView: Any? = null,
        private var webJs: String? = null,
        private var js: String? = null,
        private var usePhone: Any? = null,
    ) {
        fun setMethod(value: String?) {
            method = if (value.isNullOrBlank()) null else value
        }

        fun getMethod(): String? {
            return method
        }

        fun setCharset(value: String?) {
            charset = if (value.isNullOrBlank()) null else value
        }

        fun getCharset(): String? {
            return charset
        }

        fun setRetry(value: String?) {
            retry = if (value.isNullOrEmpty()) null else value.toIntOrNull()
        }

        fun getRetry(): Int {
            return retry ?: 0
        }

        fun setType(value: String?) {
            type = if (value.isNullOrBlank()) null else value
        }

        fun getType(): String? {
            return type
        }

        fun useWebView(): Boolean {
            return when (webView) {
                null, "", false, "false" -> false
                else -> true
            }
        }

        fun usePhone(): Boolean {
            return when (usePhone) {
                null, "", false, "false" -> false
                else -> true
            }
        }

        fun usePhone(boolean: Boolean) {
            usePhone = if (boolean) true else null
        }

        fun useWebView(boolean: Boolean) {
            webView = if (boolean) true else null
        }

        fun setHeaders(value: String?) {
            headers = if (value.isNullOrBlank()) {
                null
            } else {
                GSON.fromJsonObject<Map<String, Any>>(value).getOrNull()
            }
        }

        fun getHeaderMap(): Map<*, *>? {
            return when (val value = headers) {
                is Map<*, *> -> value
                is String -> GSON.fromJsonObject<Map<String, Any>>(value).getOrNull()
                else -> null
            }
        }

        fun setBody(value: String?) {
            body = when {
                value.isNullOrBlank() -> null
                value.isJsonObject() -> GSON.fromJsonObject<Map<String, Any>>(value)
                value.isJsonArray() -> GSON.fromJsonArray<Map<String, Any>>(value)
                else -> value
            }
        }

        fun getBody(): String? {
            return body?.let {
                if (it is String) it else GSON.toJson(it)
            }
        }

        fun setWebJs(value: String?) {
            webJs = if (value.isNullOrBlank()) null else value
        }

        fun getWebJs(): String? {
            return webJs
        }

        fun setJs(value: String?) {
            js = if (value.isNullOrBlank()) null else value
        }

        fun getJs(): String? {
            return js
        }
    }

    data class ConcurrentRecord(
        val isConcurrent: Boolean,
        var time: Long,
        var frequency: Int
    )

}