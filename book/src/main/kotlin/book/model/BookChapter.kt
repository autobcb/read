package book.model

import book.util.GSON
import book.util.NetworkUtils
import book.util.fromJsonObject
import book.util.help.CacheManager
import book.util.help.RuleBigDataHelp
import book.webBook.analyzeRule.AnalyzeUrl
import book.webBook.analyzeRule.RuleDataInterface
import com.google.gson.annotations.Expose

data class BookChapter(
    var url: String = "",               // 章节地址
    var title: String = "",              // 章节标题
    var isVolume: Boolean = false,      // 是否是卷名
    var baseUrl: String = "",           //用来拼接相对url
    var bookUrl: String = "",           // 书籍地址
    var index: Int = 0,                 // 章节序号
    var resourceUrl: String? = null,    // 音频真实URL
    var wordCount: String? = null,      // 本章节字数
    var isPay: Boolean = false,         // 是否已购买
    var isVip: Boolean = false,         // 是否VIP
    var tag: String? = null,            //
    var start: Long? = null,            // 章节起始位置
    var end: Long? = null,               // 章节终止位置
    var startFragmentId: String? = null,  //EPUB书籍当前章节的fragmentId
    var endFragmentId: String? = null,    //EPUB书籍下一章节的fragmentId
    @Expose(serialize = false, deserialize = false)
    var variable: String? = null ,       //变量
    var lastCheckTime: Long? = null,
    override var userid: String = ""
): RuleDataInterface {



    @delegate:Expose(serialize = false, deserialize = false)
    override val variableMap: HashMap<String, String> by lazy {
        GSON.fromJsonObject<HashMap<String, String>>(variable).getOrNull() ?: hashMapOf()
    }


    override fun putVariable(key: String, value: String?): Boolean {
       // println("chapterput: $key: $value")
        if (super.putVariable(key, value)) {
            variable = GSON.toJson(variableMap)
        }
        return true
    }



    override fun hashCode() = url.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other is BookChapter) {
            return other.url == url
        }
        return false
    }

    fun getAbsoluteURL():String{
        //println("目录url$url")
        val urlMatcher = AnalyzeUrl.paramPattern.matcher(url)
        val urlBefore = if(urlMatcher.find())url.substring(0,urlMatcher.start()) else url
        val urlAbsoluteBefore = NetworkUtils.getAbsoluteURL(baseUrl,urlBefore)
        return if(urlBefore.length == url.length) urlAbsoluteBefore else urlAbsoluteBefore + ',' + url.substring(urlMatcher.end())
    }


    override fun putBigVariable(key: String, value: String?) {
        if(userid.isEmpty()) return;
        RuleBigDataHelp.putChapterVariable(bookUrl,userid, url, key, value)
    }

    override fun getBigVariable(key: String): String? {
        if(userid.isEmpty()) return ""
        return RuleBigDataHelp.getChapterVariable(bookUrl,userid, url, key)
    }

}