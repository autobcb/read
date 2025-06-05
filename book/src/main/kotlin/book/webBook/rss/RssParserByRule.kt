package book.webBook.rss

import book.model.RssArticle
import book.model.RssSource
import book.webBook.DebugLog
import book.webBook.exception.NoStackTraceException
import  book.util.NetworkUtils
import book.webBook.analyzeRule.RuleData
import  book.webBook.analyzeRule.AnalyzeRule
import java.util.Locale

object RssParserByRule {

    @Throws(Exception::class)
    suspend fun parseXML(
        sortName: String,
        sortUrl: String,
        redirectUrl: String,
        body: String?,
        rssSource: RssSource,
        ruleData: RuleData,
        debugLogger: DebugLog? = null,
    ): Pair<MutableList<RssArticle>, String?> {
        val sourceUrl = rssSource.sourceUrl
        var nextUrl: String? = null
        if (body.isNullOrBlank()) {
            throw NoStackTraceException("访问网站失败:$rssSource.sourceUrl")
        }
        debugLogger?.log(sourceUrl, "≡获取成功:$sourceUrl")
        if(debugLogger != null){
            debugLogger.log("列表源码Qwq${body}");
        }
        var ruleArticles = rssSource.ruleArticles
        if (ruleArticles.isNullOrBlank()) {
            debugLogger?.log(sourceUrl, "⇒列表规则为空, 使用默认规则解析")
            return RssParserDefault.parseXML(sortName, body, sourceUrl,rssSource.userid?:"",debugLogger)
        } else {
            val articleList = mutableListOf<RssArticle>()
            val analyzeRule = AnalyzeRule(ruleData,debugLogger, rssSource)
            analyzeRule.setContent(body).setBaseUrl(sortUrl)
            analyzeRule.setRedirectUrl(redirectUrl)
            var reverse = false
            if (ruleArticles.startsWith("-")) {
                reverse = true
                ruleArticles = ruleArticles.substring(1)
            }
            debugLogger?.log(sourceUrl, "┌获取列表")
            val collections = analyzeRule.getElements(ruleArticles)
            debugLogger?.log(sourceUrl, "└列表大小:${collections.size}")
            if (!rssSource.ruleNextPage.isNullOrEmpty()) {
                debugLogger?.log(sourceUrl, "┌获取下一页链接")
                if (rssSource.ruleNextPage!!.uppercase(Locale.getDefault()) == "PAGE") {
                    nextUrl = sortUrl
                } else {
                    nextUrl = analyzeRule.getString(rssSource.ruleNextPage)
                    if (nextUrl.isNotEmpty()) {
                        nextUrl = NetworkUtils.getAbsoluteURL(sortUrl, nextUrl)
                    }
                }
                debugLogger?.log(sourceUrl, "└$nextUrl")
            }
            val ruleTitle = analyzeRule.splitSourceRule(rssSource.ruleTitle)
            val rulePubDate = analyzeRule.splitSourceRule(rssSource.rulePubDate)
            val ruleDescription = analyzeRule.splitSourceRule(rssSource.ruleDescription)
            val ruleImage = analyzeRule.splitSourceRule(rssSource.ruleImage)
            val ruleLink = analyzeRule.splitSourceRule(rssSource.ruleLink)
            val variable = ruleData.getVariable()
            for ((index, item) in collections.withIndex()) {
                getItem(
                    sourceUrl,rssSource.userid?:"", item, analyzeRule, variable, index == 0,
                    ruleTitle, rulePubDate, ruleDescription, ruleImage, ruleLink,debugLogger
                )?.let {
                    it.sort = sortName
                    it.origin = sourceUrl
                    it.userid=rssSource.userid?:""
                    articleList.add(it)
                }
            }
            if (reverse) {
                articleList.reverse()
            }
            return Pair(articleList, nextUrl)
        }
    }

    private fun getItem(
        sourceUrl: String,
        userid: String,
        item: Any,
        analyzeRule: AnalyzeRule,
        variable: String?,
        log: Boolean,
        ruleTitle: List<AnalyzeRule.SourceRule>,
        rulePubDate: List<AnalyzeRule.SourceRule>,
        ruleDescription: List<AnalyzeRule.SourceRule>,
        ruleImage: List<AnalyzeRule.SourceRule>,
        ruleLink: List<AnalyzeRule.SourceRule>,
        debugLogger: DebugLog? = null,
    ): RssArticle? {
        val rssArticle = RssArticle(variable = variable, userid = userid,)
        analyzeRule.ruleData = rssArticle
        analyzeRule.setContent(item)
        debugLogger?.log(sourceUrl, "┌获取标题", log)
        rssArticle.title = analyzeRule.getString(ruleTitle)
        debugLogger?.log(sourceUrl, "└${rssArticle.title}", log)
        debugLogger?.log(sourceUrl, "┌获取时间", log)
        rssArticle.pubDate = analyzeRule.getString(rulePubDate)
        debugLogger?.log(sourceUrl, "└${rssArticle.pubDate}", log)
        debugLogger?.log(sourceUrl, "┌获取描述", log)
        if (ruleDescription.isEmpty()) {
            rssArticle.description = null
            debugLogger?.log(sourceUrl, "└描述规则为空，将会解析内容页", log)
        } else {
            rssArticle.description = analyzeRule.getString(ruleDescription)
            debugLogger?.log(sourceUrl, "└${rssArticle.description}", log)
        }
        debugLogger?.log(sourceUrl, "┌获取图片url", log)
        rssArticle.image = analyzeRule.getString(ruleImage, isUrl = true)
        debugLogger?.log(sourceUrl, "└${rssArticle.image}", log)
        debugLogger?.log(sourceUrl, "┌获取文章链接", log)
        rssArticle.link = NetworkUtils.getAbsoluteURL(sourceUrl, analyzeRule.getString(ruleLink))
        debugLogger?.log(sourceUrl, "└${rssArticle.link}", log)
        if (rssArticle.title.isBlank()) {
            return null
        }
        return rssArticle
    }
}