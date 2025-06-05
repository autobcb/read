package web.util.hash

fun Sha1(srcStr: String): String {
    return hash("SHA-1", srcStr)
}
