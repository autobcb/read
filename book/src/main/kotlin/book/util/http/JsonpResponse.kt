package book.util.http

import book.util.Base64
import org.jsoup.Connection
import org.jsoup.nodes.Document
import java.io.BufferedInputStream
import java.net.URL
import java.nio.charset.Charset
import java.util.*

class JsonpResponse : Connection.Response {

    var url: String = ""

    var method:String = "get"

    var headers:MutableMap<String,List<String>> = mutableMapOf()

    var body:String =""

    var statusCode:Int=200

    var statusMessage:String=""

    override fun url(): URL {
        return URL(url)
    }

    override fun url(url: URL): Connection.Response {
        return url.openConnection() as Connection.Response
    }

    override fun method(): Connection.Method {
        if(method.lowercase()=="get"){
            return Connection.Method.GET
        }else if(method.lowercase()=="post"){
            return Connection.Method.POST
        }else if(method.lowercase()=="delete"){
            return Connection.Method.DELETE
        }else if(method.lowercase()=="patch"){
            return Connection.Method.PATCH
        }else if(method.lowercase()=="head"){
            return Connection.Method.HEAD
        }else{
            throw Exception("method error")
        }
    }

    override fun method(method: Connection.Method): Connection.Response {
       return url(url()).method(method)
    }

    override fun header(name: String): String? {
        var value : String? = null
        headers.forEach{
            kotlin.runCatching {
                if(it.key.lowercase()==name.lowercase()){
                    value = it.value.first()
                }
            }
        }
        return  value
    }

    override fun header(name: String, value: String): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun headers(name: String): MutableList<String> {
        return  headers[name]?.toMutableList() ?: mutableListOf()
    }

    override fun headers(): MutableMap<String, String> {
        val myheader:MutableMap<String,String> = mutableMapOf()
        headers.forEach{
            kotlin.runCatching {
                myheader[it.key]=it.value.first()
            }
        }
       return myheader
    }

    override fun addHeader(name: String, value: String): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun hasHeader(name: String): Boolean {
        return  headers.containsKey(name)
    }

    override fun hasHeaderWithValue(name: String, value: String): Boolean {
        return  hasHeader(name) && header(name) == value
    }

    override fun removeHeader(name: String): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun multiHeaders(): MutableMap<String, MutableList<String>> {
        var  newh:MutableMap<String, MutableList<String>> = mutableMapOf()
        headers.forEach{
            newh[it.key]=it.value.toMutableList() ?: mutableListOf()
        }
        return  newh
    }

    override fun cookie(name: String): String? {
        return cookies()[name]
    }

    override fun cookie(name: String, value: String): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun hasCookie(name: String): Boolean {
        return  cookies().containsKey(name)
    }

    override fun removeCookie(name: String): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun cookies(): MutableMap<String, String> {
       var cookieMap=mutableMapOf<String,String>()
       headers.forEach {it1->
           if(it1.key.lowercase()=="set-cookie"){
              it1.value.forEach{
                  val pos = it.indexOf('=')
                  var value =if (pos != -1 && pos+1 != it.length ) it.substring(pos+1, it.length) else ""
                  val key = if (pos != -1 && pos != 0 ) it.substring(0, pos) else ""
                  if (value.isNotBlank() || value.trim { it <= ' ' } == "null") {
                      value=""
                  }
                  cookieMap[key.trim()] = value.trim { it <= ' ' }
              }
           }
       }
        return cookieMap
    }

    override fun statusCode(): Int {
        return statusCode
    }

    override fun statusMessage(): String {
        return statusMessage
    }

    override fun charset(): String? {
        return Charset.defaultCharset().name()
    }

    override fun charset(charset: String): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun contentType(): String? {
        var contentType : String? = null
        headers.forEach {
            if(it.key.lowercase()=="content-type"){
                contentType=it.value.first().toString()
            }
        }
       return contentType
    }

    override fun parse(): Document {
        TODO("Not yet implemented")
    }

    override fun body(): String {
        return body
    }

    override fun bodyAsBytes(): ByteArray {
        return body.toByteArray()
    }

    override fun bufferUp(): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun bodyStream(): BufferedInputStream {
        TODO("Not yet implemented")
    }
}

data class  MyResponse (
    var url: String = "",

    var method:String = "get",

    var headers:MutableMap<String,List<String>> = mutableMapOf(),

    var body:String ="",

    var statusCode:Int=200,

    var statusMessage:String="",
){
    fun  tojsonresponse():JsonpResponse{
        var response=JsonpResponse()
        response.url=url
        response.method=method
        response.headers=headers
        val mybody=Base64.decode(body,Base64.DEFAULT)
        var contentType=response.contentType()
        var encoding = "utf-8"
        if (contentType != null && contentType.contains("charset=")) {
            contentType.let {
                if (it.contains("charset=")) {
                    encoding = it.split("charset=")[1]
                        .trim()
                        .lowercase()
                }
            }
        }
        response.body=text(mybody,encoding)
        println("response:${response.body}" )

        response.statusCode=statusCode
        response.statusMessage=statusMessage
        return response
    }

    fun text(responseBytes: ByteArray ,charsetName: String? ): String {
        if(charsetName?.lowercase() == "gb2312".lowercase()) {
            return String(responseBytes,  Charset.forName("gbk"))
        }
        return String(responseBytes, Charset.forName(charsetName))
    }
}