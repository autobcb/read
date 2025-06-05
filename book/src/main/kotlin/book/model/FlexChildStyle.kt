package book.model


data class FlexChildStyle(
    val layout_flexGrow: Float = 0F,
    val layout_flexShrink: Float = 1F,
    val layout_alignSelf: String = "auto",
    val layout_flexBasisPercent: Float = -1F,
    val layout_wrapBefore: Boolean = false,
) {

    fun alignSelf(): Int {
        return when (layout_alignSelf) {
            "auto" -> -1
            "flex_start" -> 0
            "flex_end" -> 1
            "center" -> 2
            "baseline" -> 3
            "stretch" -> 4
            else -> -1
        }
    }



    companion object {
        val defaultStyle = FlexChildStyle()
    }

}
