package book.util.help

import book.appCtx
import book.util.FileUtils
import book.util.MD5Utils
import book.util.getFile
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.io.File

object RuleBigDataHelp {

    private val ruleDataDir = FileUtils.createFolderIfNotExist(appCtx.externalFiles, "ruleData")
    private val bookData = FileUtils.createFolderIfNotExist(ruleDataDir, "book")
    private val rssData = FileUtils.createFolderIfNotExist(ruleDataDir, "rss")
    private val sourceData = FileUtils.createFolderIfNotExist(ruleDataDir, "source")


    fun putSourceVariable(sourcekey: String,userid :String, key: String, value: String?) {
        if(userid.isEmpty()) return
        val md5SourceKey = MD5Utils.md5Encode(sourcekey)
        val md5Key = MD5Utils.md5Encode(key)
        if (value == null) {
            FileUtils.delete(FileUtils.getPath(sourceData,userid, md5SourceKey, "$md5Key.txt"), true)
        } else {
            val valueFile = FileUtils.createFileIfNotExist(sourceData,userid, md5SourceKey, "$md5Key.txt")
            valueFile.writeText(value)
            val bookUrlFile = File(FileUtils.getPath(sourceData,userid, md5SourceKey, "sourcekey.txt"))
            if (!bookUrlFile.exists()) {
                bookUrlFile.writeText(sourcekey)
            }
        }
    }

    fun getSourceVariable(sourcekey: String,userid :String, key: String?): String? {
        if(userid.isEmpty()) return null
        val md5SourceKey = MD5Utils.md5Encode(sourcekey)
        val md5Key = MD5Utils.md5Encode(key)
        val file = File(FileUtils.getPath(sourceData,userid, md5SourceKey, "$md5Key.txt"))
        if (file.exists()) {
            return file.readText()
        }
        return null
    }

    fun putBookVariable(bookUrl: String,userid :String, key: String, value: String?) {
        if(userid.isEmpty() || bookUrl.isEmpty()) return
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        //println("putBookVariable $bookUrl,$userid,$key,$value,$md5BookUrl")
        val md5Key = MD5Utils.md5Encode(key)
        if (value == null) {
            FileUtils.delete(FileUtils.getPath(bookData,userid, md5BookUrl, "$md5Key.txt"), true)
        } else {
            val valueFile = FileUtils.createFileIfNotExist(bookData,userid, md5BookUrl, "$md5Key.txt")
            valueFile.writeText(value)
            val bookUrlFile = File(FileUtils.getPath(bookData,userid, md5BookUrl, "bookUrl.txt"))
            if (!bookUrlFile.exists()) {
                bookUrlFile.writeText(bookUrl)
            }
        }
    }

    fun getBookVariable(bookUrl: String,userid :String, key: String?): String? {
        if(userid.isEmpty() || bookUrl.isEmpty()) return null
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        val md5Key = MD5Utils.md5Encode(key)
        val file = File(FileUtils.getPath(bookData,userid, md5BookUrl, "$md5Key.txt"))
        if (file.exists()) {
            return file.readText()
        }
        return null
    }


    fun putChapterVariable(bookUrl: String,userid :String, chapterUrl: String, key: String, value: String?) {
        if(userid.isEmpty() || bookUrl.isEmpty()) return
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        val md5ChapterUrl = MD5Utils.md5Encode(chapterUrl)
        val md5Key = MD5Utils.md5Encode(key)
        if (value == null) {
            FileUtils.delete(FileUtils.getPath(bookData,userid, md5BookUrl, md5ChapterUrl, "$md5Key.txt"))
        } else {
            val valueFile =
                FileUtils.createFileIfNotExist(bookData,userid, md5BookUrl, md5ChapterUrl, "$md5Key.txt")
            valueFile.writeText(value)
            val bookUrlFile = File(FileUtils.getPath(bookData,userid, md5BookUrl, "bookUrl.txt"))
            if (!bookUrlFile.exists()) {
                bookUrlFile.writeText(bookUrl)
            }
        }
    }

    fun getChapterVariable(bookUrl: String,userid :String, chapterUrl: String, key: String): String? {
        if(userid.isEmpty() || bookUrl.isEmpty()) return null
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        val md5ChapterUrl = MD5Utils.md5Encode(chapterUrl)
        val md5Key = MD5Utils.md5Encode(key)
        val file = File(FileUtils.getPath(bookData,userid, md5BookUrl, md5ChapterUrl, "$md5Key.txt"))
        if (file.exists()) {
            return file.readText()
        }
        return null
    }

    fun putRssVariable(origin: String,userid :String, link: String, key: String, value: String?) {
        if(userid.isEmpty()) return
        val md5Origin = MD5Utils.md5Encode(origin)
        val md5Link = MD5Utils.md5Encode(link)
        val md5Key = MD5Utils.md5Encode(key)
        val filePath = FileUtils.getPath(rssData,userid, md5Origin, md5Link, "$md5Key.txt")
        if (value == null) {
            FileUtils.delete(filePath)
        } else {
            val valueFile = FileUtils.createFileIfNotExist(filePath)
            valueFile.writeText(value)
            val originFile = File(FileUtils.getPath(rssData,userid, md5Origin, "origin.txt"))
            if (!originFile.exists()) {
                originFile.writeText(origin)
            }

            val linFile = File(FileUtils.getPath(rssData,userid, md5Origin, md5Link, "origin.txt"))
            if (!linFile.exists()) {
                linFile.writeText(link)
            }



        }
    }

    fun getRssVariable(origin: String,userid :String, link: String, key: String): String? {
        if(userid.isEmpty()) return null
        val md5Origin = MD5Utils.md5Encode(origin)
        val md5Link = MD5Utils.md5Encode(link)
        val md5Key = MD5Utils.md5Encode(key)
        val filePath = FileUtils.getPath(rssData,userid, md5Origin, md5Link, "$md5Key.txt")
        val file = File(filePath)
        if (file.exists()) {
            return file.readText()
        }
        return null
    }
}