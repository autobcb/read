package web.Response

class JsonResponse (val isSuccess:Boolean, val errorMsg:String){

    var data:Any? = null

    fun Data(data:Any?) : JsonResponse {
        this.data = data
        return this
    }
}