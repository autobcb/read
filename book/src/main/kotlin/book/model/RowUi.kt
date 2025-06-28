package book.model
data class RowUi(
    var name: String,
    var type: String = "text",
    var action: String? = null,
    var style: FlexChildStyle? = null
) {

    override fun toString(): String {
        val hashCode = this.hashCode()
        val hexHash = Integer.toHexString(hashCode)
        val s="io.legado.app.data.entities.RowUi@"+hexHash
        return s
    }

    @Suppress("ConstPropertyName")
    object Type {

        const val text = "text"
        const val password = "password"
        const val button = "button"

    }

    fun style(): FlexChildStyle {
        return style ?: FlexChildStyle.defaultStyle
    }

}