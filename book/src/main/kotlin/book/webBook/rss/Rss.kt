package book.webBook.rss

import book.model.RssArticle
import book.model.RssSource
import book.util.NetworkUtils
import book.util.http.StrResponse
import book.webBook.DebugLog
import book.webBook.analyzeRule.AnalyzeRule
import book.webBook.analyzeRule.AnalyzeUrl
import book.webBook.analyzeRule.RuleData
import kotlin.coroutines.coroutineContext

class Rss (var debugLogger: DebugLog? = null){



    suspend fun getArticles(
        sortName: String,
        sortUrl: String,
        rssSource: RssSource,
        page: Int,
    ): Pair<MutableList<RssArticle>, String?> {
        val ruleData = RuleData()
        val analyzeUrl = AnalyzeUrl(
            sortUrl,
            page = page,
            source = rssSource,
            ruleData = ruleData,
            hasLoginHeader = false,
            debugLog = debugLogger
        )
        val res = analyzeUrl.getStrResponseAwait()
        checkRedirect(rssSource, res)
        return RssParserByRule.parseXML(sortName, sortUrl, res.url, res.body, rssSource, ruleData,debugLogger=debugLogger)
    }


    suspend fun getContent(
        rssArticle: RssArticle,
        ruleContent: String,
        rssSource: RssSource,
    ): String {
        val analyzeUrl = AnalyzeUrl(
            rssArticle.link,
            baseUrl = rssArticle.origin,
            source = rssSource,
            ruleData = rssArticle,
            hasLoginHeader = false,
            debugLog = debugLogger
        )
        val res = analyzeUrl.getStrResponseAwait()
        checkRedirect(rssSource, res)
        debugLogger?.log(rssSource.sourceUrl, "≡获取成功:${rssSource.sourceUrl}")
        if(debugLogger != null){
            debugLogger?.log("正文源码Qwq${res.body}");
        }
        val analyzeRule = AnalyzeRule(rssArticle,debugLogger, rssSource)
        analyzeRule.setContent(res.body)
            .setBaseUrl(NetworkUtils.getAbsoluteURL(rssArticle.origin, rssArticle.link))
            .setCoroutineContext(coroutineContext)
            .setRedirectUrl(res.url)
        return analyzeRule.getString(ruleContent)
    }

    /**
     * 检测重定向
     */
    private fun checkRedirect(rssSource: RssSource, response: StrResponse) {
        response.raw.priorResponse?.let {
            if (it.isRedirect) {
                debugLogger?.log(rssSource.sourceUrl, "≡检测到重定向(${it.code})")
                debugLogger?.log(rssSource.sourceUrl, "┌重定向后地址")
                debugLogger?.log(rssSource.sourceUrl, "└${response.url}")
            }
        }
    }
}