package web.cron

import book.appCtx
import book.util.FileUtils
import kotlinx.coroutines.runBlocking
import org.noear.solon.annotation.Inject
import org.noear.solon.scheduling.annotation.Scheduled
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import web.util.mapper.mapper
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes

@Scheduled(fixedRate = 1000 * 60*60*24)
class CleanBookCache : Runnable {
    @Inject(value = "\${admin.cron:true}", autoRefreshed=true)
    var cron:Boolean=true


    companion object {
        private var isdo = false
        private val cachefile = FileUtils.createFolderIfNotExist(appCtx.externalFiles, "cache","book")
        private val cache2file = FileUtils.createFolderIfNotExist(appCtx.externalFiles, "ruleData","book")
        val logger: Logger = LoggerFactory.getLogger(CleanBookCache::class.java)
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
            cachefile.walk().maxDepth(1).forEach {
                if(it.isDirectory && it.name != "book"){
                    checkcahce(it)
                }
            }
        }

        kotlin.runCatching {
            cache2file.walk().maxDepth(1).forEach {
                if(it.isDirectory && it.name != "book"){
                    checkcahce2(it)
                }
            }
        }
        isdo =false
    }

    fun checkcahce2(file: File) {
        kotlin.runCatching {
            file.walk().maxDepth(1).forEach {
                if(it.isDirectory && it.name != file.name){
                    kotlin.runCatching {
                        val attributes = Files.readAttributes(it.toPath(), BasicFileAttributes::class.java)
                        val creationTime = attributes.creationTime()
                        val instant = creationTime.toInstant()
                        val time=(System.currentTimeMillis()-instant.toEpochMilli())/(60*60*24*1000)
                        if(time > 3){
                            checkbook2(file.name,it)
                        }
                    }
                }
            }
        }
    }

    fun checkbook2(userid:String,file: File) {
        val bookUrlFile = File(FileUtils.getPath(file, "bookUrl.txt"))
        if (bookUrlFile.exists()) {
            runCatching {
                val book=mapper.get().booklistMapper.getbook(userid,bookUrlFile.readText())
                if(book == null) {
                    logger.info("bookcache: Book not found: ${bookUrlFile.readText()} clean")
                    FileUtils.delete(file, true)
                }
            }
        }else{
            FileUtils.delete(file, true)
        }
    }

    fun checkcahce(file: File) {
        kotlin.runCatching {
            file.walk().maxDepth(1).forEach {
                if(it.isDirectory && it.name != file.name){
                    kotlin.runCatching {
                        val attributes = Files.readAttributes(it.toPath(), BasicFileAttributes::class.java)
                        val creationTime = attributes.creationTime()
                        val instant = creationTime.toInstant()
                        val time=(System.currentTimeMillis()-instant.toEpochMilli())/(60*60*24*1000)
                        if(time > 3){
                            checkbook(file.name,it)
                        }
                    }
                }
            }
        }
    }

    fun checkbook(userid:String,file: File) {
        val bookUrlFile = File(FileUtils.getPath(file, "bookUrl.txt"))
        if (bookUrlFile.exists()) {
            runCatching {
                val book=mapper.get().booklistMapper.getbook(userid,bookUrlFile.readText())
                if(book == null) {
                    logger.info("bookcache: Book not found: ${bookUrlFile.readText()} clean")
                    FileUtils.delete(file, true)
                }else{
                    val chapterFile = File(FileUtils.getPath(file, "chapter.txt"))
                    checkchapter(chapterFile)
                    val contentFile = File(FileUtils.getPath(file, "content"))
                    checkcontent(contentFile)
                }
            }
        }else{
            FileUtils.delete(file, true)
        }
    }

    fun  checkchapter(file: File){
        if(!file.exists())return
        runCatching {
            val attributes = Files.readAttributes(file.toPath(), BasicFileAttributes::class.java)
            val creationTime = attributes.creationTime()
            val instant = creationTime.toInstant()
            val time=(System.currentTimeMillis()-instant.toEpochMilli())/(60*60*24*1000)
            if(time > 1){
                logger.info("bookcache: chapterFile ${file.path} is timeout clean")
                file.delete()
            }
        }
    }

    fun  checkcontent(file: File){
        if(!file.exists()) return
        if(!file.isDirectory) {
            file.delete()
            return
        }
        file.walk().maxDepth(1).forEach {
            if(it.isFile){
                val attributes = Files.readAttributes(it.toPath(), BasicFileAttributes::class.java)
                val creationTime = attributes.creationTime()
                val instant = creationTime.toInstant()
                val time=(System.currentTimeMillis()-instant.toEpochMilli())/(60*60*24*1000)
                if(time > 30){
                    logger.info("bookcache: content ${it.path} is timeout clean")
                    it.delete()
                }
            }
        }
    }
}