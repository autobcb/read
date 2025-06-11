package web.cron

import book.appCtx
import book.util.FileUtils
import kotlinx.coroutines.runBlocking
import org.noear.solon.annotation.Inject
import org.noear.solon.scheduling.annotation.Scheduled
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes

@Scheduled(fixedRate = 1000 * 60*60*48)
class CleanAssets : Runnable {

    @Inject(value = "\${admin.cron:true}", autoRefreshed=true)
    var cron:Boolean=true

    companion object {
        private var isdo = false
        private val codesfile = FileUtils.createFolderIfNotExist(appCtx.externalFiles, "assets","codes")
        private val shareJsfile = FileUtils.createFolderIfNotExist(appCtx.externalFiles, "cache","shareJs")
        private val logsfile = FileUtils.createFolderIfNotExist("logs")
        val logger: Logger = LoggerFactory.getLogger(CleanAssets::class.java)
    }

    override fun run() = runBlocking{
        if(!cron){
            return@runBlocking
        }
        if (isdo) {
            return@runBlocking
        }
        isdo =true
        kotlin.runCatching {
            codesfile.walk().maxDepth(1).forEach {
                if(it.isFile){
                    kotlin.runCatching {
                        val attributes = Files.readAttributes(it.toPath(), BasicFileAttributes::class.java)
                        val creationTime = attributes.creationTime()
                        val instant = creationTime.toInstant()
                        val time=(System.currentTimeMillis()-instant.toEpochMilli())/(60*60*24*1000)
                        if(time > 1){
                            CleanCache2.logger.info("clean ${it.path}")
                            it.delete()
                        }
                    }
                }
            }
        }
        isdo =false
    }
}