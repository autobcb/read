package book.model

data class Cache(
    // @PrimaryKey
    val key: String = "",
    var value: String? = null,
    var deadline: Long = 0L
)