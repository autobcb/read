package web.util.hash

fun Sha512(srcStr: String): String {
    return hash("SHA-512", srcStr)
}