package web.response

class JsonResponse (val isSuccess:Boolean, val errorMsg:String){


    var data:Any? = null

    constructor(isSuccess:Boolean):this(isSuccess, errorMsg = if (isSuccess) SUCCESS else "")


    fun Data(data:Any?) : JsonResponse {
        this.data = data
        return this
    }
}


class QJsonResponse (val code: Int, var msg:String){


    var data:Any? = null

    constructor(isSuccess:Boolean):this(if (isSuccess)0 else -1, msg = if (isSuccess) SUCCESS else "")


    fun Data(data:Any?) : QJsonResponse {
        this.data = data
        return this
    }

    fun Msg(msg:String) : QJsonResponse {
        this.msg = msg
        return this
    }
}