package book.app

data class WebMessage (
    val msg: String,
    val url: String,
    val html: String = "",
    val title: String,
    val header:String="",
    val body:String="",
    val id: String,
    val urlregex: String="",
    val overrideUrlRegex: String="",
)

data class ToastMessage (
    val msg: String,
    val str: String,
)