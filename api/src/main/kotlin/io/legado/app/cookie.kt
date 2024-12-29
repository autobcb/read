package io.legado.app

class Cookiemodel{
    var host=""
    var value=""
}

object androidId{
    var androidId=""
}

object CookieList{
    var value:List<Cookiemodel> = listOf()
    fun getlist(): List<Cookiemodel> = value
    fun set(cookieList: List<Cookiemodel>) {
        value = cookieList
    }
    fun get(key: String):String = run{
        value.forEach{
            if (it.host.equals(key) || ("http://"+it.host).equals(key) || ("https://"+it.host).equals(key)){
                return@run it.value
            }
        }
        return ""
    }
}