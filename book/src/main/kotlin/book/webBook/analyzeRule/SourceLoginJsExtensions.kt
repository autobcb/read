package book.webBook.analyzeRule

import book.app.App
import book.model.BaseSource
import book.model.HttpTTS
import book.util.GSON

@Suppress("unused")
class SourceLoginJsExtensions(
    source: BaseSource?,
    bookType: Int = 0,
) : RssJsExtensions( source) {

    fun reLoginView(deltaUp: Boolean = false) {
        App.reLoginView(false, getSource()?.usertocken?:"")
    }

    fun reLoginView() {
        App.reLoginView(false, getSource()?.usertocken?:"")
    }

    fun upLoginData(data: Map<String, Any?>?) {
        App.upLoginData(data, getSource()?.usertocken?:"")
    }

    fun refreshBookInfo() {
        val bookSourceUrl = getSource()?.getKey()?:""
        App.refreshBookInfo(bookSourceUrl,getSource()?.usertocken?:"")
    }

    fun refreshBookToc() {
        val bookSourceUrl = getSource()?.getKey()?:""
        App.refreshBookToc(bookSourceUrl,getSource()?.usertocken?:"")
    }

    fun refreshContent() {
        val bookSourceUrl = getSource()?.getKey()?:""
        App.refreshContent(bookSourceUrl,getSource()?.usertocken?:"")
    }

    fun copyText(text: String) {
        App.copyText(text, getSource()?.usertocken?:"")
    }

    fun refreshExplore() {
        val bookSourceUrl = getSource()?.getKey()?:""
        App.refreshExplore(bookSourceUrl,getSource()?.usertocken?:"")
    }

    @JvmOverloads
    fun showBrowser(url: String, html: String? = null, preloadJs: String? = null, config: String? = null) {
        val headerMap : java.util.HashMap<String, String> = hashMapOf()
        val headerMapF: java.util.HashMap<String, String> = hashMapOf()


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
        App.showBrowser(url,html?:"",preloadJs?:"",header,getSource()?.usertocken?:"")
    }

}