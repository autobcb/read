package web.response

class JsonResponse (val isSuccess:Boolean, val errorMsg:String){


    var data:Any? = null

    constructor(isSuccess:Boolean):this(isSuccess, errorMsg = if (isSuccess) SUCCESS else "")


    fun Data(data:Any?) : JsonResponse {
        this.data = data
        return this
    }
}