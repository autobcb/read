package book.util

import java.text.SimpleDateFormat

object AppConst {


    const val UA_NAME = "User-Agent"

    val userAgent: String by lazy {
        UserAgentHelper.getRandomUserAgent()
    }

    val defaultuserAgent: String by lazy {
        UserAgentHelper.getAndroidUserAgent()
    }


    val TIME_FORMAT: SimpleDateFormat by lazy {
        SimpleDateFormat("HH:mm")
    }

    val timeFormat: SimpleDateFormat by lazy {
        SimpleDateFormat("HH:mm")
    }

    val dateFormat: SimpleDateFormat by lazy {
        SimpleDateFormat("yyyy/MM/dd HH:mm")
    }

    val fileNameFormat: SimpleDateFormat by lazy {
        SimpleDateFormat("yy-MM-dd-HH-mm-ss")
    }

    val keyboardToolChars: List<String> by lazy {
        arrayListOf(
            "@", "&", "|", "%", "/", ":", "[", "]", "{", "}", "<", ">", "\\", "$", "#", "!", ".",
            "href", "src", "textNodes", "xpath", "json", "css", "id", "class", "tag"
        )
    }

}