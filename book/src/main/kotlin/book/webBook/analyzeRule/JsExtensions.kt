package book.webBook.analyzeRule

import book.app.App
import book.model.BaseSource
import book.util.*
import book.util.AppConst.dateFormat
import book.util.Base64
import book.util.help.cookieJarHeader
import book.util.http.*
import book.webBook.Debug
import book.webBook.DebugLog
import book.webBook.exception.NoStackTraceException
import cn.hutool.core.codec.Base64Decoder
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URLEncoder
import java.nio.charset.Charset
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.text.SimpleDateFormat
import cn.hutool.core.util.HexUtil
import com.script.ScriptBindings
import org.slf4j.Logger
import java.io.FileOutputStream
import java.nio.file.Paths
import java.time.LocalDateTime
import kotlin.collections.set
import kotlin.concurrent.thread

/**
 * js扩展类, 在js中通过java变量调用
 * 所有对于文件的读写删操作都是相对路径,只能操作阅读缓存内的文件
 * /android/data/{package}/cache/...
 */
@Suppress("unused")
interface JsExtensions: JsEncodeUtils  {

    val logger: Logger

    var debugLog: DebugLog?

    fun getSource(): BaseSource?

    fun androidId(): String {
        return getSource()?.userid?:""
    }

    fun binding(bindings: ScriptBindings){
        bindings["Base64"] = Base64
    }

    /**
     * 访问网络,返回String
     */
    fun ajax(urlStr: String): String? {
        //logger.info("ajax url: $urlStr")
        return runBlocking {
            kotlin.runCatching {
                val analyzeUrl = AnalyzeUrl(urlStr, source = getSource(),debugLog = debugLog)
                analyzeUrl.getStrResponse(urlStr).body
            }.onFailure {
                it.printOnDebug()
            }.getOrElse {
                ""
            }
        }
    }

    /**
     * 并发访问网络
     */
    fun ajaxAll(urlList: Array<String>): Array<StrResponse?> {
        return runBlocking {
            val asyncArray = Array(urlList.size) {
                async(IO) {
                    val url = urlList[it]
                    val analyzeUrl = AnalyzeUrl(url, source = getSource(),debugLog = debugLog)
                    analyzeUrl.getStrResponse(url)
                }
            }
            val resArray = Array<StrResponse?>(urlList.size) {
                asyncArray[it].await()
            }
            resArray
        }
    }

    /**
     * 访问网络,返回Response<String>
     */
    fun connect(urlStr: String): StrResponse {
        //logger.info("connect:$urlStr")
        return runBlocking {
            val analyzeUrl = AnalyzeUrl(urlStr, source = getSource(),debugLog = debugLog)
            kotlin.runCatching {
                analyzeUrl.getStrResponseAwait()
            }.onFailure {
                it.printOnDebug()
            }.getOrElse {
                StrResponse(analyzeUrl.url, it.localizedMessage)
            }
        }
    }

    fun connect(urlStr: String, header: String?): StrResponse {
        return runBlocking {
            val headerMap = GSON.fromJsonObject<Map<String, String>>(header).getOrNull()
            val analyzeUrl = AnalyzeUrl(urlStr, headerMapF = headerMap, source = getSource(),debugLog = null)
            kotlin.runCatching {
                analyzeUrl.getStrResponseAwait()
            }.onFailure {
                it.printOnDebug()
            }.getOrElse {
                StrResponse(analyzeUrl.url, it.localizedMessage)
            }
        }
    }

    fun webview(html: String?, url: String?, js: String?): StrResponse{
        val headerMap =getSource()?.getHeaderMap(true)?: hashMapOf()
        runCatching {
            if(!url.isNullOrBlank() && ( url.startsWith("http://") || url.startsWith("https://"))){
                val store=getSource()?.getCookieManger()
                val cookie = (store?.getCookie(url))?:""
                if (cookie.isNotEmpty()) {
                    store?.mergeCookies(cookie, headerMap["Cookie"])?.let {
                        headerMap.put("Cookie", it)
                    }
                }
            }
        }
        val header= GSON.toJson(headerMap)
        return App.webview(html,url,js,getSource()?.usertocken?:"",header,"","")
    }

    fun webViewGetSource(html: String?, url: String?, js: String?, sourceRegex: String): String? {
        val headerMap =getSource()?.getHeaderMap(true)?: hashMapOf()
        runCatching {
            if(!url.isNullOrBlank() && ( url.startsWith("http://") || url.startsWith("https://"))){
                val store=getSource()?.getCookieManger()
                val cookie = (store?.getCookie(url))?:""
                if (cookie.isNotEmpty()) {
                    store?.mergeCookies(cookie, headerMap["Cookie"])?.let {
                        headerMap.put("Cookie", it)
                    }
                }
            }
        }
        val header= GSON.toJson(headerMap)
        return App.webview(html,url,js,getSource()?.usertocken?:"",header,sourceRegex,"").body
    }

    /**
     * 使用webView获取跳转url
     */
    fun webViewGetOverrideUrl(
        html: String?,
        url: String?,
        js: String?,
        overrideUrlRegex: String
    ): String? {
        val headerMap =getSource()?.getHeaderMap(true)?: hashMapOf()
        runCatching {
            if(!url.isNullOrBlank() && ( url.startsWith("http://") || url.startsWith("https://"))){
                val store=getSource()?.getCookieManger()
                val cookie = (store?.getCookie(url))?:""
                if (cookie.isNotEmpty()) {
                    store?.mergeCookies(cookie, headerMap["Cookie"])?.let {
                        headerMap.put("Cookie", it)
                    }
                }
            }
        }
        val header= GSON.toJson(headerMap)
        return App.webview(html,url,js,getSource()?.usertocken?:"",header,"",overrideUrlRegex).body
    }

    /**
     * 使用webView访问网络
     * @param html 直接用webView载入的html, 如果html为空直接访问url
     * @param url html内如果有相对路径的资源不传入url访问不了
     * @param js 用来取返回值的js语句, 没有就返回整个源代码
     * @return 返回js获取的内容
     */
    fun webView(html: String?, url: String?, js: String?): String? {
        return webview(html,url,js).body
    }


    fun getWebViewUA(): String {
        return  AppConst.defaultuserAgent;
    }

    fun getWebViewUANEW(): String {
        return App.getWebViewUA(getSource()?.usertocken?:"");
    }


    /**
     * 可从网络，本地文件(阅读私有缓存目录和书籍保存位置支持相对路径)导入JavaScript脚本
     */
    fun importScript(path: String): String {
        logger.info("importScript:$path")
        val result = when {
            path.startsWith("http") -> cacheFile(path) ?: ""
            path.startsWith("/storage") -> FileUtils.readText(path)
            else -> readTxtFile(path)
        }
        if (result.isBlank()) throw NoStackTraceException("$path 内容获取失败或者为空")
        logger.info("importScriptok:$path")
        return result
    }

    /**
     * 缓存以文本方式保存的文件 如.js .txt等
     */
    fun cacheFile(urlStr: String): String? {
        return cacheFile(urlStr, 0)
    }

    /**
     * 缓存以文本方式保存的文件 如.js .txt等
     * @param saveTime 缓存时间，单位：秒
     */
    fun cacheFile(urlStr: String, saveTime: Int): String {
        val key = md5Encode16(urlStr)
        val cachePath = getSource()!!.getCacheManger().get(key)
        return if (
            cachePath.isNullOrBlank() ||
            !getFile(cachePath).exists()
        ) {
            val path = downloadFile(urlStr)
            log("首次下载 $urlStr >> $path")
            getSource()!!.getCacheManger().put(key, path, saveTime)
            readTxtFile(path)
        } else {
            readTxtFile(cachePath)
        }
    }


    /**
     *js实现读取cookie
     */
    fun getCookie(tag: String, key: String? = null): String {
        val store=getSource()?.getCookieManger()
        val cookie =(store?.getCookie(tag))?:""
        val cookieMap =store?.cookieToMap(cookie)
        return if (key != null) {
            cookieMap?.get(key) ?:""
        } else {
            cookie
        }
    }

    fun getCookie(key: String): String {
        val store=getSource()?.getCookieManger()
        val cookie = store?.getCookie(key)
        return cookie?:""
    }


    fun startBrowserAwait(url: String,title: String, refetchAfterSuccess: Boolean): StrResponse = runBlocking {
        logger.info("跳转URL：$url")

        val headerMap : HashMap<String, String> = hashMapOf()
        val headerMapF: HashMap<String, String> = hashMapOf()


        val analyzeUrl = AnalyzeUrl(
            url, source = getSource(),
            debugLog = debugLog
        )
        val baseUrl = analyzeUrl.url
        headerMap.putAll(analyzeUrl.headerMap)
        headerMapF.putAll(analyzeUrl.headerMap)


        runCatching {
            val store=getSource()?.getCookieManger()
            val cookie = (store?.getCookie(baseUrl))?:""
            if (cookie.isNotEmpty()) {
                store?.mergeCookies(cookie, headerMap["Cookie"])?.let {
                    headerMap.put("Cookie", it)
                }
            }
        }

        val header= GSON.toJson(headerMap)
        logger.info("header:$header")
        var re=App.startBrowserAwait(baseUrl,title,getSource()?.usertocken?:"",header)
        if(refetchAfterSuccess){
            logger.info("重新加载网页:$url")
            re = AnalyzeUrl(
                url,
                headerMapF = headerMapF,
                source = getSource(),
                debugLog = debugLog,
            ).getStrResponseAwait(useWebView = false)
        }
        re
    }

    fun startBrowser(url: String, title: String) {
        thread {
            startBrowserAwait(url, title,false)
        }
    }

    fun startBrowserAwait(url: String, title: String): StrResponse {
        return startBrowserAwait(url, title,true)
    }


    /**
     * 下载文件
     * @param url 下载地址:可带参数type
     * @return 下载的文件相对路径
     */
    fun downloadFile(url: String): String {
        val analyzeUrl = AnalyzeUrl(url, source = getSource(),debugLog = debugLog)
        val type = UrlUtil.getSuffix(url, analyzeUrl.type)
        val path = FileUtils.getPath(
            FileUtils.downDir,getSource()?.userid?:"",
            "${MD5Utils.md5Encode16(url)}.${type}"
        )
        val file = File(path).createFileReplace()
        analyzeUrl.getInputStream().use { iStream ->
            FileOutputStream(file).use { oStream ->
                iStream.copyTo(oStream)
            }
        }
        return path.substring(FileUtils.getCachePath().length)
    }

    /**
     * 实现16进制字符串转文件
     * @param content 需要转成文件的16进制字符串
     * @param url 通过url里的参数来判断文件类型
     * @return 相对路径
     */
    fun downloadFile(content: String, url: String): String {
        val type = AnalyzeUrl(url, source = getSource(), debugLog = debugLog).type
            ?: return ""
        val path = FileUtils.getPath(
            FileUtils.createFolderIfNotExist(FileUtils.getCachePath()),getSource()?.userid?:"",
            "${MD5Utils.md5Encode16(url)}.${type}"
        )
        val file = File(path)
        file.createFileReplace()
        HexUtil.decodeHex(content).let {
            if (it.isNotEmpty()) {
                file.writeBytes(it)
            }
        }
        return path.substring(FileUtils.getCachePath().length)
    }


    private fun  getrequestHeaders(urlStr: String, headers: Map<String, String>) : MutableMap<String, String>{
        val cookie = (getSource()?.getCookieManger()?.getCookie(urlStr))?:""
        val headerMap =if (cookie.isNotEmpty()) {
            var key="Cookie"
            headers.keys.forEach {
                if(it.lowercase(Locale.getDefault()) == "Cookie".lowercase(Locale.getDefault())){
                    key = it
                }
            }
            val oldc=headers[key]?:""
            headers.toMutableMap().apply {
                getSource()?.getCookieManger()?.mergeCookies( oldc,cookie)?.also {
                    remove(key)
                    put("Cookie", it)
                }
            }
        } else headers.toMutableMap()
        return headerMap
    }

    /**
     * js实现重定向拦截,网络访问get
     */
    fun get(urlStr: String, headers: Map<String, String>): Connection.Response {
        if(getSource()?.phonehttp == true){
            return  getusePhone(urlStr, headers)
        }else{
            val requestHeaders = getrequestHeaders(urlStr,headers)
            logger.info("get:$urlStr,headers:${GSON.toJson(requestHeaders)}")
            val rateLimiter = ConcurrentRateLimiter(getSource())
            val response = rateLimiter.withLimitBlocking {
                Jsoup.connect(urlStr)
                    .sslSocketFactory(SSLHelper.unsafeSSLSocketFactory)
                    .ignoreContentType(true)
                    .followRedirects(false)
                    .headers(requestHeaders)
                    .method(Connection.Method.GET)
                    .timeout(60000)
                    .execute()
            }
            val source=getSource()
            if(source?.enabledCookieJar == true) {
                val store=getSource()?.getCookieManger()
                store?.savejsonResponse(response)
            }
            return response
        }
    }

    fun getusePhone(urlStr: String, headers: Map<String, String>): Connection.Response {
        val requestHeaders = getrequestHeaders(urlStr,headers)
        if(getSource()?.enabledCookieJar == true) {
            requestHeaders[cookieJarHeader] = "12345"
        }
        logger.info("getusePhone:$urlStr,headers:${GSON.toJson(requestHeaders)}")
        val rateLimiter = ConcurrentRateLimiter(getSource())
        val response = rateLimiter.withLimitBlocking {
            App.get(urlStr,GSON.toJson(requestHeaders),getSource()?.usertocken?:"",false)
        }
        return response
    }



    /**
     * js实现重定向拦截,网络访问head,不返回Response Body更省流量
     */
    fun head(urlStr: String, headers: Map<String, String>): Connection.Response {
        if(getSource()?.phonehttp == true){
            return  headusePhone(urlStr, headers)
        }else{
            val requestHeaders = getrequestHeaders(urlStr,headers)
            logger.info("head:$urlStr,headers:${GSON.toJson(requestHeaders)}")
            val rateLimiter = ConcurrentRateLimiter(getSource())
            val response = rateLimiter.withLimitBlocking {
                Jsoup.connect(urlStr)
                    .sslSocketFactory(SSLHelper.unsafeSSLSocketFactory)
                    .ignoreContentType(true)
                    .followRedirects(false)
                    .headers(requestHeaders)
                    .method(Connection.Method.HEAD)
                    .timeout(60000)
                    .execute()
            }
            val source=getSource()
            if(source?.enabledCookieJar == true) {
                val store=getSource()?.getCookieManger()
                store?.savejsonResponse(response)
            }
            return response
        }
    }

    fun headusePhone(urlStr: String, headers: Map<String, String>): Connection.Response {
        val requestHeaders = getrequestHeaders(urlStr,headers)
        if(getSource()?.enabledCookieJar == true) {
            requestHeaders[cookieJarHeader] = "12345"
        }
        logger.info("headusePhone:$urlStr,headers:${GSON.toJson(requestHeaders)}")
        val rateLimiter = ConcurrentRateLimiter(getSource())
        val response = rateLimiter.withLimitBlocking {
            App.head(urlStr,GSON.toJson(requestHeaders),getSource()?.usertocken?:"",false)
        }
        return response
    }

    /**
     * 打开图片验证码对话框，等待返回验证结果
     */
    fun getVerificationCode(imageUrl: String): String = runBlocking{
        if(imageUrl.startsWith("data:image")){
            logger.info("getVerificationCode:$imageUrl")
            val code=App.getVerificationCode(imageUrl,getSource()?.usertocken?:"").trim()
            logger.info("获取到code:$code")
            code
        }else{
            if (getSource()?.phonehttp == true){
                getVerificationCodeusePhone(imageUrl)
            }else{
                logger.info("getVerificationCode:$imageUrl")
                val analyzeUrl = AnalyzeUrl(imageUrl, source = getSource(),debugLog = null)
                val img=analyzeUrl.getByteArrayAwait()
                val coverFile = "${MD5Utils.md5Encode16(getSource()?.getKey() +getSource()?.userid +"VerificationCode")}.jpg"
                val relativeCoverUrl = Paths.get("assets", "", "codes", coverFile).toString()
                val  url="/" + relativeCoverUrl
                val coverUrl = Paths.get("", "storage", relativeCoverUrl).toString()
                val file=File(coverUrl)
                if (file.exists()) {
                    file.delete()
                }
                FileUtils.writeBytes(coverUrl,img)
                logger.info("url:$url")
                val code=App.getVerificationCode(url+"?time=${LocalDateTime.now()}",getSource()?.usertocken?:"").trim()
                logger.info("获取到code:$code")
                code
            }
        }

    }

    fun getVerificationCodeusePhone(imageUrl: String): String = runBlocking{
        logger.info("getVerificationCodeusePhone:$imageUrl")
        if(imageUrl.startsWith("data:image")){
            val code=App.getVerificationCode(imageUrl,getSource()?.usertocken?:"").trim()
            logger.info("获取到code:$code")
            code
        }else{
            val analyzeUrl = AnalyzeUrl(imageUrl, source = getSource(),debugLog = null)
            analyzeUrl.setCookie()
            val code=App.getVerificationCodeusePhone(imageUrl, GSON.toJson(analyzeUrl.headerMap),getSource()?.usertocken?:"").trim()
            logger.info("获取到code:$code")
            code
        }
    }


    /**
     * 网络访问post
     */
    fun post(urlStr: String, body: String, headers: Map<String, String>): Connection.Response {
        if(getSource()?.phonehttp == true){
            return postusePhone(urlStr,body,headers)
        }else{
            val requestHeaders = getrequestHeaders(urlStr,headers)
            logger.info("post:$urlStr,body:$body,headers:${GSON.toJson(requestHeaders)}")
            val rateLimiter = ConcurrentRateLimiter(getSource())
            val response = rateLimiter.withLimitBlocking {
                Jsoup.connect(urlStr)
                    .sslSocketFactory(SSLHelper.unsafeSSLSocketFactory)
                    .ignoreContentType(true)
                    .followRedirects(false)
                    .requestBody(body)
                    .headers(requestHeaders)
                    .timeout(60000)
                    .method(Connection.Method.POST)
                    .execute()
            }
            val source=getSource()
            if(source?.enabledCookieJar == true) {
                val store=getSource()?.getCookieManger()
                store?.savejsonResponse(response)
            }

            return response
        }
    }

    fun postusePhone(urlStr: String, body: String, headers: Map<String, String>): Connection.Response {
        val requestHeaders = getrequestHeaders(urlStr,headers)
        if(getSource()?.enabledCookieJar == true) {
            requestHeaders[cookieJarHeader] = "12345"
        }
        logger.info("postusePhone:$urlStr,body:$body,headers:${GSON.toJson(requestHeaders)}")
        val rateLimiter = ConcurrentRateLimiter(getSource())
        var key="Content-Type"
        var value="application/x-www-form-urlencoded"
        requestHeaders.keys.forEach {
            if(it.lowercase(Locale.getDefault()) == "Content-Type".lowercase(Locale.getDefault())){
                key = it
                value=requestHeaders[it]?:""
            }
        }
        requestHeaders.remove(key)
        requestHeaders["Content-Type"]=value
        val response = rateLimiter.withLimitBlocking {
            App.post(urlStr,body,GSON.toJson(requestHeaders),getSource()?.usertocken?:"",false)
        }
        return response
    }

    /* Str转ByteArray */
    fun strToBytes(str: String): ByteArray {
        return str.toByteArray(charset("UTF-8"))
    }

    fun strToBytes(str: String, charset: String): ByteArray {
        return str.toByteArray(charset(charset))
    }

    /* ByteArray转Str */
    fun bytesToStr(bytes: ByteArray): String {
        return String(bytes, charset("UTF-8"))
    }

    fun bytesToStr(bytes: ByteArray, charset: String): String {
        return String(bytes, charset(charset))
    }



    /**
     * js实现解码,不能删
     */
    fun base64Decode(str: String): String {
        return EncoderUtils.base64Decode(str, Base64.NO_WRAP)
    }

    fun base64Decode(str: String?, charset: String): String {
        return Base64Decoder.decodeStr(str, charset(charset));
    }


    fun base64Decode(str: String, flags: Int): String {
        return EncoderUtils.base64Decode(str, flags)
    }

    fun base64DecodeToByteArray(str: String?): ByteArray? {
        if (str.isNullOrBlank()) {
            return null
        }
        return Base64.decode(str, Base64.DEFAULT)
    }

    fun base64DecodeToByteArray(str: String?, flags: Int): ByteArray? {
        if (str.isNullOrBlank()) {
            return null
        }
        return Base64.decode(str, flags)
    }


    fun base64Encode(str: String): String? {
        return EncoderUtils.base64Encode(str, Base64.NO_WRAP)
    }

    fun base64Encode(str: String, flags: Int): String? {
        return EncoderUtils.base64Encode(str, flags)
    }



    /* HexString 解码为字节数组 */
    fun hexDecodeToByteArray(hex: String): ByteArray? {
        return HexUtil.decodeHex(hex)
    }

    /* hexString 解码为utf8String*/
    fun hexDecodeToString(hex: String): String? {
        return HexUtil.decodeHexStr(hex)
    }

    /* utf8 编码为hexString */
    fun hexEncodeToString(utf8: String): String? {
        return HexUtil.encodeHexStr(utf8)
    }




    /**
     * 格式化时间
     */
    fun timeFormatUTC(time: Long, format: String, sh: Int): String? {
        val utc = SimpleTimeZone(sh, "UTC")
        return SimpleDateFormat(format, Locale.getDefault()).run {
            timeZone = utc
            format(Date(time))
        }
    }

    /**
     * 时间格式化
     */
    fun timeFormat(time: Long): String {
        return dateFormat.format(Date(time))
    }

    /**
     * utf8编码转gbk编码
     */
    fun utf8ToGbk(str: String): String {
        val utf8 = String(str.toByteArray(charset("UTF-8")))
        val unicode = String(utf8.toByteArray(), charset("UTF-8"))
        return String(unicode.toByteArray(charset("GBK")))
    }

    fun encodeURI(str: String): String {
        return try {
            URLEncoder.encode(str, "UTF-8")
        } catch (e: Exception) {
            ""
        }
    }

    fun encodeURI(str: String, enc: String): String {
        return try {
            URLEncoder.encode(str, enc)
        } catch (e: Exception) {
            ""
        }
    }

    fun htmlFormat(str: String): String {
        return HtmlFormatter.formatKeepImg(str)
    }

    fun t2s(text: String): String {
        return ChineseUtils.t2s(text)
    }

    fun s2t(text: String): String {
        return ChineseUtils.s2t(text)
    }

    //****************文件操作******************//

    /**
     * 获取本地文件
     * @param path 相对路径
     * @return File
     */
    fun getFile(path: String): File {
        val cachePath = FileUtils.getCachePath()
        val aPath: String = if (path.startsWith(File.separator)) {
            cachePath + path
        } else {
            cachePath + File.separator + path
        }
        return File(aPath)
    }

    fun readFile(path: String): ByteArray? {
        val file = getFile(path)
        if (file.exists()) {
            return file.readBytes()
        }
        return null
    }

    fun readTxtFile(path: String): String {
        val file = getFile(path)
        if (file.exists()) {
            val charsetName = EncodingDetect.getEncode(file)
            return String(file.readBytes(), charset(charsetName))
        }
        return ""
    }

    fun readTxtFile(path: String, charsetName: String): String {
        val file = getFile(path)
        if (file.exists()) {
            return String(file.readBytes(), charset(charsetName))
        }
        return ""
    }

    /**
     * 删除本地文件
     */
    fun deleteFile(path: String) {
        val file = getFile(path)
        FileUtils.delete(file, true)
    }

    /**
     * js实现压缩文件解压
     * @param zipPath 相对路径
     * @return 相对路径
     */
    fun unzipFile(zipPath: String): String {
        if (zipPath.isEmpty()) return ""
        val unzipPath = FileUtils.getPath(
            FileUtils.createFolderIfNotExist(FileUtils.getCachePath()),
            FileUtils.getNameExcludeExtension(zipPath)
        )
        FileUtils.deleteFile(unzipPath)
        val zipFile = getFile(zipPath)
        val unzipFolder = FileUtils.createFolderIfNotExist(unzipPath)
        ZipUtils.unzipFile(zipFile, unzipFolder)
        FileUtils.deleteFile(zipFile.absolutePath)
        return unzipPath.substring(FileUtils.getCachePath().length)
    }



    /**
     * js实现文件夹内所有文件读取
     */
    fun getTxtInFolder(unzipPath: String): String {
        if (unzipPath.isEmpty()) return ""
        val unzipFolder = getFile(unzipPath)
        val contents = StringBuilder()
        unzipFolder.listFiles().let {
            if (it != null) {
                for (f in it) {
                    val charsetName = EncodingDetect.getEncode(f)
                    contents.append(String(f.readBytes(), charset(charsetName)))
                        .append("\n")
                }
                contents.deleteCharAt(contents.length - 1)
            }
        }
        FileUtils.deleteFile(unzipFolder.absolutePath)
        return contents.toString()
    }

    /**
     * 获取网络zip文件里面的数据
     * @param url zip文件的链接或十六进制字符串
     * @param path 所需获取文件在zip内的路径
     * @return zip指定文件的数据
     */
    fun getZipStringContent(url: String, path: String): String {
        val byteArray = getZipByteArrayContent(url, path) ?: return ""
        val charsetName = EncodingDetect.getEncode(byteArray)
        return String(byteArray, Charset.forName(charsetName))
    }

    fun getZipStringContent(url: String, path: String, charsetName: String): String {
        val byteArray = getZipByteArrayContent(url, path) ?: return ""
        return String(byteArray, Charset.forName(charsetName))
    }

    /**
     * 获取网络zip文件里面的数据
     * @param url zip文件的链接或十六进制字符串
     * @param path 所需获取文件在zip内的路径
     * @return zip指定文件的数据
     */
    fun getZipByteArrayContent(url: String, path: String): ByteArray? {
        val bytes = if (url.startsWith("http://") || url.startsWith("https://")) {
            runBlocking {
                return@runBlocking okHttpClient.newCall { url(url) }.bytes()
            }
        } else {
            StringUtils.hexStringToByte(url)
        }
        val bos = ByteArrayOutputStream()
        val zis = ZipInputStream(ByteArrayInputStream(bytes))
        var entry: ZipEntry? = zis.nextEntry
        while (entry != null) {
            if (entry.name.equals(path)) {
                zis.use { it.copyTo(bos) }
                return bos.toByteArray()
            }
            entry = zis.nextEntry
        }
        Debug.log("getZipContent 未发现内容")

        return null
    }




    //******************文件操作************************//

    /**
     * 解析字体,返回字体解析类
     */
    fun queryBase64TTF(base64: String?): QueryTTF? {
        base64DecodeToByteArray(base64)?.let {
            return QueryTTF(it)
        }
        return null
    }


    /**
     * 返回字体解析类
     * @param str 支持url,本地文件,base64,自动判断,自动缓存
     */
    fun queryTTF(str: String?): QueryTTF? {
        str ?: return null
        val key = md5Encode16(str)
        var qTTF = getSource()!!.getCacheManger().getQueryTTF(key)
        if (qTTF != null) return qTTF
        val font: ByteArray? = when {
            str.isAbsUrl() -> runBlocking {
                var x = getSource()!!.getCacheManger().getByteArray(key)
                if (x == null) {
                    x = okHttpClient.newCall { url(str) }.bytes()
                    x.let {
                        getSource()!!.getCacheManger().put(key, it)
                    }
                }
                return@runBlocking x
            }
            str.indexOf("storage/") > 0 -> File(str).readBytes()
            else -> base64DecodeToByteArray(str)
        }
        font ?: return null
        qTTF = QueryTTF(font)
        getSource()!!.getCacheManger().put(key, qTTF)
        return qTTF
    }


    /**
     * @param text 包含错误字体的内容
     * @param font1 错误的字体
     * @param font2 正确的字体
     */
    fun replaceFont(
        text: String,
        font1: QueryTTF?,
        font2: QueryTTF?
    ): String {
        if (font1 == null || font2 == null) return text
        val contentArray = text.toCharArray()
        contentArray.forEachIndexed { index, s ->
            val oldCode = s.code
            if (font1.inLimit(s)) {
                val code = font2.getCodeByGlyf(font1.getGlyfByCode(oldCode))
                if (code != 0) contentArray[index] = code.toChar()
            }
        }
        return contentArray.joinToString("")
    }

    /**
     * 章节数转数字
     */
    fun toNumChapter(s: String?): String? {
        s ?: return null
        val matcher = AppPattern.titleNumPattern.matcher(s)
        if (matcher.find()) {
            val intStr = StringUtils.stringToInt(matcher.group(2))
            return "${matcher.group(1)}${intStr}${matcher.group(3)}"
        }
        return s
    }


    fun toURL(urlStr: String): JsURL {
        return JsURL(urlStr)
    }

    fun toURL(url: String, baseUrl: String? = null): JsURL {
        return JsURL(url, baseUrl)
    }

    /**
     * 弹窗提示
     */
    fun toast(msg: Any?) {
        logger.info("toast:$msg")
        App.toast("$msg",getSource()?.usertocken?:"")
    }

    /**
     * 弹窗提示 停留时间较长
     */
    fun longToast(msg: Any?) {
        toast(msg)
    }

    /**
     * 输出调试日志
     */
    fun log(msg: Any?): Any? {
        logger.info("sourceUrl: {}, msg: {}", getSource()?. getKey(), msg)
        debugLog?.log(getSource()?. getKey(), "$msg")
        App.log("${getSource()?. getKey()}:$msg",getSource()?.usertocken?:"")
        return msg
    }


    /**
     * 输出对象类型
     */
    fun logType(any: Any?) {
        if (any == null) {
            log("null")
        } else {
            log(any.javaClass.name)
        }
    }

    /**
     * 生成UUID
     */
    fun randomUUID(): String {
        return UUID.randomUUID().toString()
    }

    fun openUrl(url: String) {
        openUrl(url, null)
    }

    // 新增 mimeType 参数，默认为 null（保持兼容性）
    fun openUrl(url: String, mimeType: String? = null) {
        /*val source = getSource() ?: throw NoStackTraceException("openUrl source cannot be null")
        appCtx.startActivity<OpenUrlConfirmActivity> {
            putExtra("uri", url)
            putExtra("mimeType", mimeType)
            putExtra("sourceOrigin", source.getKey())
            putExtra("sourceName", source.getTag())
        }*/
        logger.info("openUrl: $url,mimeType: $mimeType")
        App.openurl(url, mimeType,getSource()?.usertocken?:"")
    }



}
