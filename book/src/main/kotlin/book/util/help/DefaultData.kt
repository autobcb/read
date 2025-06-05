package book.util.help

import book.model.TxtTocRule
import book.util.GSON
import book.util.fromJsonArray

object DefaultData {
    const val txtTocRuleFileName = "txtTocRule.json"

    val txtTocRules: List<TxtTocRule> by lazy {
        val json = String(DefaultData::class.java.getResource("/defaultData/${txtTocRuleFileName}").readBytes())
        GSON.fromJsonArray<TxtTocRule>(json).getOrNull() ?: emptyList()
    }

    // val rssSources by lazy {
    //     val json = String(
    //         File("defaultData${File.separator}rssSources.json")
    //             .readBytes()
    //     )
    //     GSON.fromJsonArray<RssSource>(json)!!
    // }
}