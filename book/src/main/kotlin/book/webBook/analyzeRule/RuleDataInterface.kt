package book.webBook.analyzeRule

interface RuleDataInterface {


    val variableMap: HashMap<String, String>

    var userid: String

    fun putVariable(key: String, value: String?): Boolean {
        //println("putVariable key: $key value: $value")
        val keyExist = variableMap.contains(key)
        return when {
            value == null -> {
                variableMap.remove(key)
                putBigVariable(key, null)
                keyExist
            }

           /* value.length < 10000 -> {
                putBigVariable(key, value)
                variableMap[key] = value
                true
            }*/

            else -> {
                variableMap[key] = value
                putBigVariable(key, value)
                keyExist
            }
        }
    }

    fun getVariable(key: String): String {
        return( variableMap[key] ?: getBigVariable(key) ?: "")
    }

    fun putBigVariable(key: String, value: String?)

    fun getBigVariable(key: String): String?


}