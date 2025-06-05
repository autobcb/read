package web.util.validation

import java.util.regex.Pattern


/**
 * 手机号码正则判断
 */
fun isPhoneNum(phone: String?): Boolean {
    val compile = Pattern.compile("^(13|14|15|16|17|18|19)\\d{9}$")
    val matcher = compile.matcher(phone?:"")
    return matcher.matches()
}
//支持13、14、15、16、17、18、19开头后面任意搭9位
