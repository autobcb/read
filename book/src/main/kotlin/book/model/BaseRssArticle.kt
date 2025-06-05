package book.model

import book.util.GSON
import book.util.help.RuleBigDataHelp
import book.webBook.analyzeRule.RuleDataInterface

interface BaseRssArticle : RuleDataInterface {

    var origin: String
    var link: String

    var variable: String?

    override fun putVariable(key: String, value: String?): Boolean {
        if (super.putVariable(key, value)) {
            variable = GSON.toJson(variableMap)
        }
        return true
    }

    override fun putBigVariable(key: String, value: String?) {
        RuleBigDataHelp.putRssVariable(origin,userid, link, key, value)
    }

    override fun getBigVariable(key: String): String? {
        return RuleBigDataHelp.getRssVariable(origin,userid, link, key)
    }

}