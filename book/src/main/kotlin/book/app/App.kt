package book.app

import book.util.http.JsonpResponse
import book.util.http.StrResponse

object App  {
    var startBrowserAwait =fun  (urlStr: String,title: String,tocken:String,header:String): StrResponse {
        return StrResponse(urlStr,"")
    }
    var webview =fun  (html: String?, url: String?, js: String?,tocken:String,header:String):StrResponse{
        return StrResponse(url?:"", "")
    }
    var webviewbody =fun  (html: String?, url: String?, js: String?,tocken:String,header:String,body:String):StrResponse{
        return StrResponse(url?:"", "")
    }
    var toast =fun  (str: String,tocken:String){

    }
    var getVerificationCode =fun  (imgurl:String,tocken:String):String{
        return ""
    }

    var getVerificationCodeusePhone =fun  (imgurl:String, header:String,tocken:String):String{
        return ""
    }

    var getWebViewUA =fun  (tocken:String):String{
        return ""
    }
    var log =fun  (str: String,tocken:String){

    }
    var get =fun  (url: String?, header:String,tocken:String,move:Boolean):JsonpResponse{
        return JsonpResponse().also {
            it.url = url?:""
            it.method = "get"
            it.statusCode = 403
        }
    }

    var post =fun  (url: String?,body:String, header:String,tocken:String,move:Boolean):JsonpResponse{
        return JsonpResponse().also {
            it.url = url?:""
            it.method = "get"
            it.statusCode = 403
        }
    }

    var head =fun  (url: String?, header:String,tocken:String,move:Boolean):JsonpResponse{
        return JsonpResponse().also {
            it.url = url?:""
            it.method = "head"
            it.statusCode = 403
        }
    }

    var openurl= fun (url :String, mimeType:String?,tocken:String){

    }

}

