package book.app

data class WebMessage (
    val msg: String,
    val url: String,
    val title: String,
)

data class ToastMessage (
    val msg: String,
    val str: String,
)