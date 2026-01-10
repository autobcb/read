package book.util.help

import book.appCtx
import book.util.FileUtils
import book.util.MD5Utils
import book.util.MyCache
import java.io.File
import java.util.WeakHashMap

object RuleBigDataHelp {

    private val ruleDataDir = FileUtils.createFolderIfNotExist(appCtx.externalFiles, "ruleData")
    private val bookData = FileUtils.createFolderIfNotExist(ruleDataDir, "book")
    private val rssData = FileUtils.createFolderIfNotExist(ruleDataDir, "rss")
    private val sourceData = FileUtils.createFolderIfNotExist(ruleDataDir, "source")
    
    // 文件操作锁映射，使用文件路径作为键，确保同一文件的并发安全，不同文件可以并行操作
    // 使用WeakHashMap避免内存泄漏，当锁对象不再被引用时会自动回收
    private val fileLocks = WeakHashMap<String, Any>()
    private val lockMapLock = Any()
    
    /**
     * 获取指定文件路径的锁对象
     */
    private fun getFileLock(filePath: String): Any {
        synchronized(lockMapLock) {
            return fileLocks.getOrPut(filePath) { Any() }
        }
    }

    fun putSourceVariable(sourcekey: String,userid :String, key: String, value: String?) {
        if(userid.isEmpty()) {
            //println("putSourceVariable but userid is empty")
            return
        }
        val md5SourceKey = MD5Utils.md5Encode(sourcekey)
        val md5Key = MD5Utils.md5Encode(key)
        val valueFilePath = FileUtils.getPath(sourceData,userid, md5SourceKey, "$md5Key.txt")
        
        if (value == null) {
            // 删除操作，只需要锁定要删除的文件
            synchronized(getFileLock(valueFilePath)) {
                FileUtils.delete(valueFilePath, true)
            }
        } else {
            // 写入操作，需要锁定要写入的文件
            synchronized(getFileLock(valueFilePath)) {
                val valueFile = FileUtils.createFileIfNotExist(sourceData,userid, md5SourceKey, "$md5Key.txt")
                valueFile.writeText(value)
            }
            
            // 写入sourcekey.txt，需要单独锁定这个文件
            val bookUrlFilePath = FileUtils.getPath(sourceData,userid, md5SourceKey, "sourcekey.txt")
            synchronized(getFileLock(bookUrlFilePath)) {
                val bookUrlFile = File(bookUrlFilePath)
                if (!bookUrlFile.exists()) {
                    bookUrlFile.writeText(sourcekey)
                }
            }
        }
    }

    fun getSourceVariable(sourcekey: String,userid :String, key: String?): String? {
        if(userid.isEmpty()) {
            //println("getSourceVariable but userid is empty")
            return null
        }
        val md5SourceKey = MD5Utils.md5Encode(sourcekey)
        val md5Key = MD5Utils.md5Encode(key)
        val filePath = FileUtils.getPath(sourceData,userid, md5SourceKey, "$md5Key.txt")
        
        synchronized(getFileLock(filePath)) {
            val file = File(filePath)
            if (file.exists()) {
                val str=file.readText()
                return str
            }
            return null
        }
    }

    fun putBookVariable(bookUrl: String,userid :String, key: String, value: String?) {
        if(userid.isEmpty() || bookUrl.isEmpty()) {
            //println("putBookVariable but userid is empty $key->$value")
            return
        }
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        //println("putBookVariable $bookUrl,$userid,$key,$value,$md5BookUrl")
        val md5Key = MD5Utils.md5Encode(key)
        val valueFilePath = FileUtils.getPath(bookData,userid, md5BookUrl, "$md5Key.txt")
        
        if (value == null) {
            // 删除操作，只需要锁定要删除的文件
            synchronized(getFileLock(valueFilePath)) {
                FileUtils.delete(valueFilePath, true)
            }
        } else {
            // 写入操作，需要锁定要写入的文件
            synchronized(getFileLock(valueFilePath)) {
                val valueFile = FileUtils.createFileIfNotExist(bookData,userid, md5BookUrl, "$md5Key.txt")
                valueFile.writeText(value)
            }
            
            // 写入bookUrl.txt，需要单独锁定这个文件
            val bookUrlFilePath = FileUtils.getPath(bookData,userid, md5BookUrl, "bookUrl.txt")
            synchronized(getFileLock(bookUrlFilePath)) {
                val bookUrlFile = File(bookUrlFilePath)
                if (!bookUrlFile.exists()) {
                    bookUrlFile.writeText(bookUrl)
                }
            }
        }
    }

    fun getBookVariable(bookUrl: String,userid :String, key: String?): String? {
        if(userid.isEmpty() || bookUrl.isEmpty()) {
           // println("getBookVariable but userid is empty")
            return null
        }
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        val md5Key = MD5Utils.md5Encode(key)
        val filePath = FileUtils.getPath(bookData,userid, md5BookUrl, "$md5Key.txt")
        
        synchronized(getFileLock(filePath)) {
            val file = File(filePath)
            if (file.exists()) {
                return file.readText()
            }
            return null
        }
    }


    fun putChapterVariable(bookUrl: String,userid :String, chapterUrl: String, key: String, value: String?) {
        if(userid.isEmpty() || bookUrl.isEmpty())  {
           // println("putChapterVariable but userid is empty")
            return
        }
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        val md5ChapterUrl = MD5Utils.md5Encode(chapterUrl)
        val md5Key = MD5Utils.md5Encode(key)
        val valueFilePath = FileUtils.getPath(bookData,userid, md5BookUrl, md5ChapterUrl, "$md5Key.txt")
        
        if (value == null) {
            // 删除操作，只需要锁定要删除的文件
            synchronized(getFileLock(valueFilePath)) {
                FileUtils.delete(valueFilePath)
            }
        } else {
            // 写入操作，需要锁定要写入的文件
            synchronized(getFileLock(valueFilePath)) {
                val valueFile =
                    FileUtils.createFileIfNotExist(bookData,userid, md5BookUrl, md5ChapterUrl, "$md5Key.txt")
                valueFile.writeText(value)
            }
            
            // 写入bookUrl.txt，需要单独锁定这个文件
            val bookUrlFilePath = FileUtils.getPath(bookData,userid, md5BookUrl, "bookUrl.txt")
            synchronized(getFileLock(bookUrlFilePath)) {
                val bookUrlFile = File(bookUrlFilePath)
                if (!bookUrlFile.exists()) {
                    bookUrlFile.writeText(bookUrl)
                }
            }
        }
    }

    fun getChapterVariable(bookUrl: String,userid :String, chapterUrl: String, key: String): String? {
        if(userid.isEmpty() || bookUrl.isEmpty())  {
           // println("getChapterVariable but userid is empty")
            return null
        }
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        val md5ChapterUrl = MD5Utils.md5Encode(chapterUrl)
        val md5Key = MD5Utils.md5Encode(key)
        val filePath = FileUtils.getPath(bookData,userid, md5BookUrl, md5ChapterUrl, "$md5Key.txt")
        
        synchronized(getFileLock(filePath)) {
            val file = File(filePath)
            if (file.exists()) {
                return file.readText()
            }
            return null
        }
    }

    fun putRssVariable(origin: String,userid :String, link: String, key: String, value: String?) {
        if(userid.isEmpty()) return
        val md5Origin = MD5Utils.md5Encode(origin)
        val md5Link = MD5Utils.md5Encode(link)
        val md5Key = MD5Utils.md5Encode(key)
        val valueFilePath = FileUtils.getPath(rssData,userid, md5Origin, md5Link, "$md5Key.txt")
        
        if (value == null) {
            // 删除操作，只需要锁定要删除的文件
            synchronized(getFileLock(valueFilePath)) {
                FileUtils.delete(valueFilePath)
            }
        } else {
            // 写入操作，需要锁定要写入的文件
            synchronized(getFileLock(valueFilePath)) {
                val valueFile = FileUtils.createFileIfNotExist(valueFilePath)
                valueFile.writeText(value)
            }
            
            // 写入origin.txt，需要单独锁定这个文件
            val originFilePath = FileUtils.getPath(rssData,userid, md5Origin, "origin.txt")
            synchronized(getFileLock(originFilePath)) {
                val originFile = File(originFilePath)
                if (!originFile.exists()) {
                    originFile.writeText(origin)
                }
            }

            // 写入linFile，需要单独锁定这个文件
            val linFilePath = FileUtils.getPath(rssData,userid, md5Origin, md5Link, "origin.txt")
            synchronized(getFileLock(linFilePath)) {
                val linFile = File(linFilePath)
                if (!linFile.exists()) {
                    linFile.writeText(link)
                }
            }
        }
    }

    fun getRssVariable(origin: String,userid :String, link: String, key: String): String? {
        if(userid.isEmpty()) return null
        val md5Origin = MD5Utils.md5Encode(origin)
        val md5Link = MD5Utils.md5Encode(link)
        val md5Key = MD5Utils.md5Encode(key)
        val filePath = FileUtils.getPath(rssData,userid, md5Origin, md5Link, "$md5Key.txt")
        
        synchronized(getFileLock(filePath)) {
            val file = File(filePath)
            if (file.exists()) {
                return file.readText()
            }
            return null
        }
    }
}