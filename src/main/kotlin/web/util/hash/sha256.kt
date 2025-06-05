package web.util.hash

fun Sha256(srcStr: String): String {
    return hash("SHA-256", srcStr)
}