package web.config

import book.app.App
import book.app.ToastMessage
import book.app.WebMessage
import book.util.AppConst
import book.util.GSON
import book.util.http.MyResponse
import book.util.http.StrResponse
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.noear.solon.annotation.Bean
import org.noear.solon.annotation.Configuration
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import web.controller.api.ApiWebSocket
import web.util.cache.checkfile
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Configuration
class InitConfig {

    val logger: Logger = LoggerFactory.getLogger(InitConfig::class.java)

    class StartBrowserRe{
        var url: String = ""
        var  html: String = ""
    }



    @Bean
    fun cookieinit() {
        checkfile()
        App.startBrowserAwait=fun (urlStr: String, title: String, tocken:String, header:String):StrResponse = runBlocking{
            if(urlStr.isBlank())  return@runBlocking  StrResponse(urlStr,"")
            val socket=ApiWebSocket.get(tocken)
            if(socket!=null){
                val id= UUID.randomUUID().toString()
                logger.info("startBrowser ,url: $urlStr ,title: $title, tocken: $tocken ")
                socket.send(Gson().toJson(WebMessage(msg = "startBrowser", url = urlStr,title=title,id=id, header = header )))
                kotlin.runCatching {
                    val rez= GSON.fromJson(ApiWebSocket.WaitForResponse(id), StartBrowserRe::class.java)
                    return@runBlocking  StrResponse(rez.url,rez.html)
                }
            }
            return@runBlocking  StrResponse(urlStr,"")
        }

        App.webview=fun (html: String?, url: String?, js: String?, tocken:String, header:String,urlregex:String,overrideUrlRegex:String):StrResponse = runBlocking{
            if(url.isNullOrBlank() && html.isNullOrBlank())  return@runBlocking  StrResponse(url?:"","")
            val socket=ApiWebSocket.get(tocken)
            if(socket!=null){
                val id= UUID.randomUUID().toString()
                //logger.info("webview ,url: $url ,js: $js, html:$html, tocken: $tocken ")
                socket.send(Gson().toJson(WebMessage(msg = "webview", url = url?:"",title=js?:"", html = html?:"" ,id=id ,header=header,urlregex=urlregex,overrideUrlRegex=overrideUrlRegex)))
                return@runBlocking  StrResponse(url?:"",ApiWebSocket.WaitForResponse(id)?:"")
            }
            return@runBlocking  StrResponse(url?:"","")
        }
        App.webviewbody=fun (html: String?, url: String?, js: String?, tocken:String, header:String, body:String,urlregex:String,overrideUrlRegex:String):StrResponse = runBlocking{
            if(url.isNullOrBlank() && html.isNullOrBlank())  return@runBlocking  StrResponse(url?:"","")
            val socket=ApiWebSocket.get(tocken)
            if(socket!=null){
                val id= UUID.randomUUID().toString()
                //logger.info("webview ,url: $url ,js: $js, html:$html, tocken: $tocken ")
                socket.send(Gson().toJson(WebMessage(msg = "webview", url = url?:"",title=js?:"", html = html?:"" ,id=id ,header=header,body=body,urlregex=urlregex,overrideUrlRegex=overrideUrlRegex)))
                return@runBlocking  StrResponse(url?:"",ApiWebSocket.WaitForResponse(id)?:"")
            }
            return@runBlocking  StrResponse(url?:"","")
        }
        App.getVerificationCode= fun (url : String, tocken:String)  = runBlocking {
            val socket=ApiWebSocket.get(tocken)
            if(socket!=null){
                val id= UUID.randomUUID().toString()
                socket.send(Gson().toJson(WebMessage(
                    msg = "getVerificationCode", url = url , id = id,
                    title = "getVerificationCode",
                )))
                return@runBlocking ApiWebSocket.WaitForResponse(id)?:""
            }
            ""
        }
        App.getVerificationCodeusePhone= fun (url : String, header:String, tocken:String)  = runBlocking {
            val socket=ApiWebSocket.get(tocken)
            if(socket!=null){
                val id= UUID.randomUUID().toString()
                socket.send(Gson().toJson(WebMessage(
                    msg = "getVerificationCodeusePhone", url = url , id = id,
                    title = "getVerificationCodeusePhone",header = header,
                )))
                return@runBlocking ApiWebSocket.WaitForResponse(id)?:""
            }
            ""
        }
        App.toast = fun (str : String, tocken:String)  = runBlocking {
            val socket=ApiWebSocket.get(tocken)
            if(socket!=null){
                logger.info("toast:$str")
                socket.send(Gson().toJson(ToastMessage(msg = "toast", str=str )))
            }
        }
        App.log = fun (str : String, tocken:String)   {
            runBlocking{
                ApiWebSocket.get(tocken)?.send(Gson().toJson(ToastMessage(msg = "log", str="${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}\n$str" )))
            }
        }
        App.getWebViewUA=fun ( tocken:String) = runBlocking{
            val socket=ApiWebSocket.get(tocken)
            if(socket!=null){
                val id= UUID.randomUUID().toString()
                socket.send(Gson().toJson(WebMessage(
                    msg = "getWebViewUA", url = "", id = id,
                    title = "getWebViewUA",
                )))
                return@runBlocking ApiWebSocket.WaitForResponse(id)?:""
            }
            AppConst.defaultuserAgent
        }

        App.head=fun (url: String?, header:String,tocken:String,move:Boolean) = runBlocking{
            val socket=ApiWebSocket.get(tocken)
            if(socket!=null){
                val id= UUID.randomUUID().toString()
                //logger.info("webview ,url: $url ,js: $js, html:$html, tocken: $tocken ")
                socket.send(Gson().toJson(WebMessage(
                    msg = "head", url = url ?: "", id = id, header = header,
                    title ="$move"
                )))
                val json=ApiWebSocket.WaitForResponse(id)?:""
                kotlin.runCatching {
                    val re= GSON.fromJson(json, MyResponse::class.java)
                    return@runBlocking  re.tojsonresponse()
                }
            }
            return@runBlocking MyResponse().also {
                it.url = url?:""
                it.method = "head"
                it.statusCode = 403
            }.tojsonresponse()
        }

        App.get=fun (url: String?, header:String,tocken:String,move:Boolean) = runBlocking{
            val socket=ApiWebSocket.get(tocken)
            if(socket!=null){
                val id= UUID.randomUUID().toString()
                //logger.info("webview ,url: $url ,js: $js, html:$html, tocken: $tocken ")
                socket.send(Gson().toJson(WebMessage(
                    msg = "get", url = url ?: "", id = id, header = header,
                    title ="$move"
                )))
                val json=ApiWebSocket.WaitForResponse(id)?:""
                kotlin.runCatching {
                    val re= GSON.fromJson(json, MyResponse::class.java)
                    return@runBlocking  re.tojsonresponse()
                }
            }
            return@runBlocking MyResponse().also {
                it.url = url?:""
                it.method = "get"
                it.statusCode = 403
            }.tojsonresponse()
        }

        App.post=fun (url: String?,body:String, header:String,tocken:String,move:Boolean) = runBlocking{
            val socket=ApiWebSocket.get(tocken)
            if(socket!=null){
                val id= UUID.randomUUID().toString()
                //logger.info("webview ,url: $url ,js: $js, html:$html, tocken: $tocken ")
                socket.send(Gson().toJson(WebMessage(msg = "post", url = url?:"",id=id ,header=header,body=body,title = "$move")))
                val json=ApiWebSocket.WaitForResponse(id)?:""
                kotlin.runCatching {
                    val re= GSON.fromJson(json, MyResponse::class.java)
                    return@runBlocking  re.tojsonresponse()
                }
            }
            return@runBlocking MyResponse().also {
                it.url = url?:""
                it.method = "post"
                it.statusCode = 403
            }.tojsonresponse()
        }

        App.openurl = fun (url : String, mimeType:String?, tocken:String)  = runBlocking {
            val socket=ApiWebSocket.get(tocken)
            if(socket!=null){
                logger.info("openurl:$url")
                socket.send(Gson().toJson(WebMessage(
                    msg = "openurl", url = url, title = mimeType ?: "",
                    id = ""
                )))
            }
        }

        App.searchBook = fun (key :String, sourceurl:String?,tocken:String)  = runBlocking {
            if(key.isEmpty()){
                App.toast("调用searchBook时关键词不能为空",tocken)
                return@runBlocking
            }
            val socket=ApiWebSocket.get(tocken)
            if(socket!=null ){
                logger.info("searchBook:$key")
                socket.send(Gson().toJson(WebMessage(
                    msg = "searchBook", url = sourceurl?:"", title = key ,
                    id = ""
                )))
            }
        }

        App.addBook = fun (bookurl:String?,tocken:String)  = runBlocking {
            if(bookurl.isNullOrBlank()){
                App.toast("调用addBook时bookurl不能为空",tocken)
                return@runBlocking
            }
            val socket=ApiWebSocket.get(tocken)
            if(socket!=null ){
                logger.info("addBook:$bookurl")
                socket.send(Gson().toJson(WebMessage(
                    msg = "addBook", url = bookurl, title = "" ,
                    id = ""
                )))
            }
        }

       App.noticy = fun (str:String,id:String,tocken:String){
           val socket=ApiWebSocket.get(tocken)
           if(socket!=null ){
               socket.send(Gson().toJson(WebMessage(
                   msg = "noticy", url = id, title = str ,
                   id = ""
               )))
           }
       }


    }


}