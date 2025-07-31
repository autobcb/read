package web.cron

import org.noear.solon.annotation.Inject
import org.noear.solon.scheduling.annotation.Scheduled
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes

@Suppress("unused")
@Scheduled(fixedRate = 1000 * 60*60*24)
class JsonFileJob : Runnable{
    val filepath="storage/assets/json"
    @Inject(value = "\${admin.cron:true}", autoRefreshed=true)
    var cron:Boolean=true

    override fun run(){
        if(!cron){
            return
        }
        val file= File(filepath)
        file.walk().maxDepth(1).forEach {
            if(it.isFile && it.extension == "json"){
                kotlin.runCatching {
                    val attributes = Files.readAttributes(it.toPath(), BasicFileAttributes::class.java)
                    val creationTime = attributes.creationTime()
                    val instant = creationTime.toInstant()
                    val time=(System.currentTimeMillis()-instant.toEpochMilli())/(60*60*24*1000)
                    if(time > 7){
                        it.delete()
                    }
                }
            }
        }
    }
}