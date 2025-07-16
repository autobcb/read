package android.text


object TextUtils {

    @JvmStatic
    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.length == 0
    }

    @JvmStatic
    fun isDigitsOnly(str: CharSequence?): Boolean {
        if (isEmpty(str)) return false
        str!!.forEach {
            if (!Character.isDigit(it)) return false
        }
        return true
    }

    @JvmStatic
    fun equals(a: CharSequence?, b: CharSequence?): Boolean {
        if (a === b) return true
        if (a == null || b == null) return false
        return a.toString() == b.toString()
    }

    @JvmStatic
    fun getTrimmedLength(s: CharSequence?): Int {
        if (s == null) return 0
        var i = 0
        var len = s.length
        while (i < len && s[i] <= ' ') i++
        while (i < len && s[len - 1] <= ' ') len--
        return len - i
    }

    @JvmStatic
    fun join(delimiter: CharSequence, vararg tokens: Any?): String {
        if (tokens.isEmpty()) return ""
        val sb = StringBuilder()
        var firstTime = true
        for (token in tokens) {
            if (firstTime) {
                firstTime = false
            } else {
                sb.append(delimiter)
            }
            sb.append(token)
        }
        return sb.toString()
    }

    @JvmStatic
    fun split(text: String, expression: String): Array<String> {
        return text.split(expression.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    @JvmStatic
    fun htmlEncode(s: String): String {
        val sb = StringBuilder()
        s.forEach {
            when (it) {
                '"' -> sb.append("&quot;")
                '\'' -> sb.append("&apos;")
                '&' -> sb.append("&amp;")
                '<' -> sb.append("&lt;")
                '>' -> sb.append("&gt;")
                else -> sb.append(it)
            }
        }
        return sb.toString()
    }
}
