package web.cron

import book.util.GSON
import kotlinx.coroutines.runBlocking
import org.noear.solon.annotation.Inject
import org.noear.solon.scheduling.annotation.Scheduled
import org.slf4j.LoggerFactory
import web.controller.HomeController
import java.net.HttpURLConnection
import java.net.URL

@Scheduled(fixedRate = 1000 * 30*60)
class CodeJob : Runnable{

    @Inject(value = "\${admin.code:}", autoRefreshed=true)
    var mycode:String=""

    private val logger = LoggerFactory.getLogger(CodeJob::class.java)
    override fun run(): Unit = runBlocking{
        if (mycode.isBlank()) return@runBlocking
        runCatching {
            val data=postRequest("http://app.qread.xyz/appapi/NeedCode","key="+mycode)
            val jd= GSON.fromJson<Map<String, Any>>(data, Map::class.java)
            val  code=jd["code"] as Double
            if (code == 0.0){
                logger.info("需要邀请码")
                HomeController.needcode=true
            }else{
                logger.info("不需要邀请码")
                HomeController.needcode=false
            }
        }.onFailure {
            it.printStackTrace()
        }
    }



    fun postRequest(url: String, jsonData: String): String {
        val connection = URL(url).openConnection() as HttpURLConnection
        return connection.run {
            requestMethod = "POST"
            setRequestProperty("Content-Type", " application/x-www-form-urlencoded")
            doOutput = true
            outputStream.bufferedWriter().use { it.write(jsonData) }
            inputStream.bufferedReader().use { it.readText() }
        }
    }
}