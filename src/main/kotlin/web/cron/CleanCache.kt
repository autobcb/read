package web.cron

import book.appCtx
import book.model.Cache
import book.util.FileUtils
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.noear.solon.annotation.Inject
import org.noear.solon.scheduling.annotation.Scheduled
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

@Scheduled(fixedRate = 1000 * 60*60*8)
class CleanCache : Runnable {

    @Inject(value = "\${admin.cron:true}", autoRefreshed=true)
    var cron:Boolean=true

    companion object {
        private var isdo = false
        private val cachefile = FileUtils.createFolderIfNotExist(appCtx.externalFiles, "cache","cache")
        val logger: Logger = LoggerFactory.getLogger(CleanCache::class.java)
    }

    override fun run() = runBlocking{
        if(!cron){
            return@runBlocking
        }
        if (isdo) {
            return@runBlocking
        }
        isdo=true
        kotlin.runCatching {
            cachefile.walk().maxDepth(1).forEach {
                if(it.isDirectory && it.name != "cache"){
                    checkcahce(it)
                }
            }
        }
        isdo=false
    }

    fun checkcahce(file: File) {
        kotlin.runCatching {
            file.walk().maxDepth(1).forEach {
                if(it.isFile && it.extension == "txt"){
                   runCatching {
                       val content = it.readText()
                       if(content.isBlank()){
                           logger.info("cache :${it.name} is blank delete")
                           it.delete()
                       }else{
                           runCatching {
                               val cache = Gson().fromJson(content, Cache::class.java)
                               if (cache.deadline != 0.toLong() && System.currentTimeMillis() > cache.deadline) {
                                   logger.info("cache :${it.name} is timeout delete")
                                   it.delete()
                               }
                           }.onFailure {e->
                               kotlin.runCatching {
                                   logger.info("cache :${it.name} is error delete")
                                   it.delete()
                               }
                           }
                       }
                   }
                }
            }
        }
    }
}