package book.model

data class TxtTocRule(
    // @PrimaryKey
    var id: Long = System.currentTimeMillis(),
    var name: String = "",
    var rule: String = "",
    var serialNumber: Int = -1,
    var enable: Boolean = true
)