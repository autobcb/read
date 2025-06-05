package web.util.hash

fun Md5(srcStr: String): String {
    return hash("MD5", srcStr)
}

fun Md5(srcStr: ByteArray): ByteArray {
    return hash("MD5", srcStr)
}

