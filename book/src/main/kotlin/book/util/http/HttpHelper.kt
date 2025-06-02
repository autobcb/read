package book.util.http



import book.util.AppConst
import book.util.NetworkUtils
import book.util.help.CookieStore
import book.util.help.cookieJarHeader
import book.webBook.DebugLog
import okhttp3.ConnectionSpec
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Route
import okhttp3.Authenticator
import okhttp3.Response
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.io.IOException
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor

private val proxyClientCache: ConcurrentHashMap<String, OkHttpClient> by lazy {
    ConcurrentHashMap()
}

val okHttpClient: OkHttpClient by lazy {
    val specs = arrayListOf(
        ConnectionSpec.MODERN_TLS,
        ConnectionSpec.COMPATIBLE_TLS,
        ConnectionSpec.CLEARTEXT
    )

    val builder = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .callTimeout(60, TimeUnit.SECONDS)
        .sslSocketFactory(SSLHelper.unsafeSSLSocketFactory, SSLHelper.unsafeTrustManager)
        .retryOnConnectionFailure(true)
        .hostnameVerifier(SSLHelper.unsafeHostnameVerifier)
        .connectionSpecs(specs)
        .followRedirects(true)
        .followSslRedirects(true)
        .addInterceptor(OkHttpExceptionInterceptor)
        .addInterceptor(Interceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()
            if (request.header(AppConst.UA_NAME) == null) {
                builder.addHeader(AppConst.UA_NAME, AppConst.userAgent)
            } else if (request.header(AppConst.UA_NAME) == "null") {
                builder.removeHeader(AppConst.UA_NAME)
            }
            builder.addHeader("Keep-Alive", "300")
            builder.addHeader("Connection", "Keep-Alive")
            builder.addHeader("Cache-Control", "no-cache")
            chain.proceed(builder.build())
        })
        .addNetworkInterceptor { chain ->
            val request = chain.request()
            val enableCookieJar = request.header(cookieJarHeader)

            if (!enableCookieJar.isNullOrEmpty() && CookieStore.Stores.containsKey(enableCookieJar)) {
                val store=CookieStore.Stores[enableCookieJar]
                store?.loadRequest(request)
            }

            val networkResponse = chain.proceed(request)

            if (!enableCookieJar.isNullOrEmpty() && CookieStore.Stores.containsKey(enableCookieJar)) {
                //println("saveCookie ${networkResponse.request.url}")
                val store=CookieStore.Stores[enableCookieJar]
                store?.saveResponse(networkResponse);
            }
            networkResponse
        }
    // if (AppConfig.isCronet) {
    //     builder.addInterceptor(CronetInterceptor())
    // }
    builder.addInterceptor(DecompressInterceptor)
    builder.build().apply {
        val okHttpName =
            OkHttpClient::class.java.name.removePrefix("okhttp3.").removeSuffix("Client")
        val executor = dispatcher.executorService as ThreadPoolExecutor
        val threadName = "$okHttpName Dispatcher"
        executor.threadFactory = ThreadFactory { runnable ->
            Thread(runnable, threadName).apply {
                isDaemon = false
                uncaughtExceptionHandler = OkhttpUncaughtExceptionHandler
            }
        }
    }
}


/**
 * 缓存代理okHttp
 */
fun getProxyClient(proxy: String? = null, debugLog: DebugLog? = null): OkHttpClient {
    if (proxy.isNullOrBlank()) {
        if (debugLog == null) {
            return okHttpClient
        }
        val builder = okHttpClient.newBuilder()
        val logInterceptor = HttpLoggingInterceptor(debugLog);//创建拦截对象
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这一句一定要记得写，否则没有数据输出

        builder.addNetworkInterceptor(logInterceptor)  //设置打印拦截日志
        return builder.build()
    }
    if (debugLog == null) {
        proxyClientCache[proxy]?.let {
            return it
        }
    }
    val r = Regex("(http|socks4|socks5)://(.*):(\\d{2,5})(@.*@.*)?")
    val ms = r.findAll(proxy)
    val group = ms.first()
    var username = ""       //代理服务器验证用户名
    var password = ""       //代理服务器验证密码
    val type = if (group.groupValues[1] == "http") "http" else "socks"
    val host = group.groupValues[2]
    val port = group.groupValues[3].toInt()
    if (group.groupValues[4] != "") {
        username = group.groupValues[4].split("@")[1]
        password = group.groupValues[4].split("@")[2]
    }
    if (type != "direct" && host != "") {
        val builder = okHttpClient.newBuilder()
        if (type == "http") {
            builder.proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress(host, port)))
        } else {
            builder.proxy(Proxy(Proxy.Type.SOCKS, InetSocketAddress(host, port)))
        }
        if (username != "" && password != "") {
            val proxyAuthenticator = object: Authenticator {
                @Throws(IOException::class)
                override fun authenticate(route: Route?, response: Response): Request {
                    //设置代理服务器账号密码
                    val credential = Credentials.basic(username, password);
                    return response.request.newBuilder()
                        .header("Proxy-Authorization", credential)
                        .build();
                }
            }
            builder.proxyAuthenticator(proxyAuthenticator);
            // builder.proxyAuthenticator { _, response -> //设置代理服务器账号密码
            //     val credential: String = Credentials.basic(username, password)
            //     response.request.newBuilder()
            //         .header("Proxy-Authorization", credential)
            //         .build()
            // }
        }
        if (debugLog != null) {
            val logInterceptor = HttpLoggingInterceptor(debugLog);//创建拦截对象
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这一句一定要记得写，否则没有数据输出

            builder.addNetworkInterceptor(logInterceptor)  //设置打印拦截日志
            return builder.build()
        }
        val proxyClient = builder.build()
        proxyClientCache[proxy] = proxyClient
        return proxyClient
    }
    return okHttpClient
}

// suspend fun getWebViewSrc(params: AjaxWebView.AjaxParams): StrResponse =
//     suspendCancellableCoroutine { block ->
//         val webView = AjaxWebView()
//         block.invokeOnCancellation {
//             webView.destroyWebView()
//         }
//         webView.callback = object : AjaxWebView.Callback() {
//             override fun onResult(response: StrResponse) {

//                 if (!block.isCompleted)
//                     block.resume(response)
//             }

//             override fun onError(error: Throwable) {
//                 if (!block.isCompleted)
//                     block.cancel(error)
//             }
//         }
//         webView.load(params)
//     }