package web.util.validation

import java.util.regex.Pattern

/**
 * 正则表达式 判断邮箱格式是否正确
 */
fun isEmail(email: String?): Boolean {
    val str =
        "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
    val p = Pattern.compile(str)
    val m = p.matcher(email?:"")
    return m.matches()
}
