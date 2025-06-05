package book.model

import book.util.GSON
import book.util.help.RuleBigDataHelp
import book.webBook.analyzeRule.RuleDataInterface

object BookType {
    const val default = 0           // 0 文本
    const val audio = 1             // 1 音频
    const val image = 2            // 2 图片
    const val file = 3               // 3 只提供下载服务的网站
    const val local = "loc_book"
}

interface BaseBook: RuleDataInterface {
    var name: String
    var author: String
    var bookUrl: String
    var kind: String?
    var wordCount: String?
    var variable: String?
    var infoHtml: String?
    var tocHtml: String?

//    fun getKindList(): List<String> {
//        val kindList = arrayListOf<String>()
//        wordCount?.let {
//            if (it.isNotBlank()) kindList.add(it)
//        }
//        kind?.let {
//            val kinds = it.splitNotBlank(",", "\n")
//            kindList.addAll(kinds)
//        }
//        return kindList
//    }

    override fun putVariable(key: String, value: String?): Boolean {
        //println("book put variable $key $value")
        super.putVariable(key, value)
        variable = GSON.toJson(variableMap)
        return true
    }

    fun putCustomVariable(value: String?) {
        putVariable("custom", value)
    }

    fun getCustomVariable(): String {
        return getVariable("custom")
    }

    override fun putBigVariable(key: String, value: String?) {
        if(userid.isEmpty()) return
        RuleBigDataHelp.putBookVariable(bookUrl,userid, key, value)
    }

    override fun getBigVariable(key: String): String? {
        if(userid.isEmpty()) return ""
        return RuleBigDataHelp.getBookVariable(bookUrl,userid, key)
    }
}