package web.Util.hex

import java.security.MessageDigest

fun MD5(input:String) :String {
    val digest = MessageDigest.getInstance("MD5")
    val result = digest.digest(input.toByteArray())
    val stringBuilder = StringBuilder()

    //转成16进制
    result.forEach {
        val value = it
        val hex = value.toInt() and (0xFF)
        val hexStr = Integer.toHexString(hex)
        //println(hexStr)
        if(hexStr.length == 1){
            stringBuilder.append(0).append(hexStr)
        } else {
            stringBuilder.append(hexStr)
        }
    }

    return stringBuilder.toString()
}