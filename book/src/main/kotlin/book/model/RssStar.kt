package book.model

import book.util.GSON
import book.util.fromJsonObject
import book.util.help.CacheManager
import com.google.gson.annotations.Expose

data class RssStar(
    override var origin: String = "",
    var sort: String = "",
    var title: String = "",
    var starTime: Long = 0,
    override var link: String = "",
    var pubDate: String? = null,
    var description: String? = null,
    var content: String? = null,
    var image: String? = null,
    var group: String = "默认分组",
    @Expose(serialize = false, deserialize = false)
    override var variable: String? = null,
    override var userid: String ="",
) : BaseRssArticle {

    @delegate:Expose(serialize = false, deserialize = false)
    override val variableMap: HashMap<String, String> by lazy {
        GSON.fromJsonObject<HashMap<String, String>>(variable).getOrNull() ?: hashMapOf()
    }

    fun toRssArticle() = RssArticle(
        origin = origin,
        sort = sort,
        title = title,
        link = link,
        pubDate = pubDate,
        description = description,
        content = content,
        image = image,
        group = group,
        variable = variable,
        userid = userid,
    )
}
