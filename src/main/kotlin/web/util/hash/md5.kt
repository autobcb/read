package web.util.hash

import java.security.MessageDigest

fun Md5(srcStr: String): String {
    return hash("MD5", srcStr)
}

fun Md5(srcStr: ByteArray): ByteArray {
    return hash("MD5", srcStr)
}

@OptIn(ExperimentalStdlibApi::class)
fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(this.toByteArray())
    return digest.toHexString()
}