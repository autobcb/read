package web.util.hash

fun Sha384(srcStr: String): String {
    return hash("SHA-384", srcStr)
}
