package web.util.hash

import java.security.MessageDigest

fun hash(algorithm: String, srcStr: String): String {
    try {
        val result = StringBuilder()
        val md = MessageDigest.getInstance(algorithm)
        val bytes = md.digest(srcStr.toByteArray(charset("utf-8")))
        for (b in bytes) {
            val hex = Integer.toHexString(b.toInt() and 0xFF)
            if (hex.length == 1)
                result.append("0")
            result.append(hex)
        }
        return result.toString()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }

}

fun hash(algorithm: String, bytes: ByteArray): ByteArray {
    try {
        val md = MessageDigest.getInstance(algorithm)
        md.update(bytes)
        return md.digest()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }

}