package book.util

object Base64 {

    const val DEFAULT = 0
    const val NO_PADDING = 1
    const val NO_WRAP = 2
    const val CRLF = 4
    const val URL_SAFE = 8
    const val NO_CLOSE = 16

    @JvmStatic
    fun encodeToString(str: String): String{
        return encodeToString(str.toByteArray(), NO_WRAP)
    }

    @JvmStatic
    fun  encodeToString(data: ByteArray, flags: Int): String {
       return book.util.crypto.Base64.encodeToString(data, flags)
    }

    @JvmStatic
    fun encode(data: ByteArray?, flags: Int): ByteArray {
        return book.util.crypto.Base64.encode(data, flags)
    }

    @JvmStatic
    fun decode(str: String, flags: Int): ByteArray {
        return book.util.crypto.Base64.decode(str, flags)
    }

    @JvmStatic
    fun decode(str: ByteArray?, flags: Int): ByteArray {
        return book.util.crypto.Base64.decode(str, flags)
    }
}
