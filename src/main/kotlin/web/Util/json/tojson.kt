package web.Util.json

import com.fasterxml.jackson.databind.ObjectMapper


var mapper = ObjectMapper()

fun tojson(obj:Any)=run{
   mapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(obj)
}