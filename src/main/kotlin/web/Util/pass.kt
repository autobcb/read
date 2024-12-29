package web.Util

import web.Util.hex.sha256

private val key="asdbhyuszkjsdiux"

fun passsign(password :String):String= run{
    sha256(password, key)
}