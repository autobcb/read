package book.util

object Base64 {

    const val DEFAULT = 0
    const val NO_PADDING = 1
    const val NO_WRAP = 2
    const val CRLF = 4
    const val URL_SAFE = 8
    const val NO_CLOSE = 16

    fun  encodeToString(data: ByteArray, flags: Int): String {
       return book.util.crypto.Base64.encodeToString(data, flags)
    }

    fun encode(data: ByteArray?, flags: Int): ByteArray {
        return book.util.crypto.Base64.encode(data, flags)
    }


    fun decode(str: String, flags: Int): ByteArray {
        return book.util.crypto.Base64.decode(str, flags)
    }

    fun decode(str: ByteArray?, flags: Int): ByteArray {
        return book.util.crypto.Base64.decode(str, flags)
    }
}
