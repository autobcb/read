package web.util

import book.appCtx
import book.model.Book
import book.model.BookChapter
import book.util.FileUtils
import book.util.MD5Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object BigDataHelp {
    private val ruleDataDir = FileUtils.createFolderIfNotExist(appCtx.externalFiles, "cache")
    private val bookData = FileUtils.createFolderIfNotExist(ruleDataDir, "book")

    fun putChapterList(bookUrl: String,userid :String, value: List<BookChapter>?) {
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        val file = "chapter.txt"
        if (value == null) {
            FileUtils.delete(FileUtils.getPath(bookData,userid, md5BookUrl, file), true)
        } else {
            val valueFile = FileUtils.createFileIfNotExist(bookData,userid, md5BookUrl, file)
            valueFile.writeText(Gson().toJson(value))
            val bookUrlFile = File(FileUtils.getPath(bookData,userid, md5BookUrl, "bookUrl.txt"))
            if (!bookUrlFile.exists()) {
                bookUrlFile.writeText(bookUrl)
            }
        }
    }

    fun  getChapterList(bookUrl: String,userid :String):List<BookChapter>?{
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        val file = "chapter.txt"
        val valueFile = FileUtils.createFileIfNotExist(bookData,userid, md5BookUrl, file)
        var list:List<BookChapter>?= null
        if (valueFile.exists()) {
            kotlin.runCatching {
                val type = object : TypeToken<List<BookChapter>?>() {}.type
                val l:List<BookChapter>?=Gson().fromJson(valueFile.readText(),type)
                list = l
            }
        }
        return list
    }

    fun  putBookContent(bookUrl: String,userid :String,index: Int, value: String?){
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        val file = "$index.txt"
        if (value == null) {
            FileUtils.delete(FileUtils.getPath(bookData,userid, md5BookUrl,"content", file), true)
        } else {
            val valueFile = FileUtils.createFileIfNotExist(bookData,userid, md5BookUrl,"content", file)
            valueFile.writeText(value)
            val bookUrlFile = File(FileUtils.getPath(bookData,userid, md5BookUrl, "bookUrl.txt"))
            if (!bookUrlFile.exists()) {
                bookUrlFile.writeText(bookUrl)
            }
        }
    }

    fun  getBookContent(bookUrl: String,userid :String,index: Int):String?{
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        val file = "$index.txt"
        val valueFile = FileUtils.createFileIfNotExist(bookData,userid, md5BookUrl,"content", file)
        if (valueFile.exists()) {
           return  valueFile.readText()
        }
        return null
    }


    fun  putBookInfo(bookUrl: String,userid :String, value: Book?){
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        val file = "info.txt"
        if (value == null) {
            FileUtils.delete(FileUtils.getPath(bookData,userid, md5BookUrl, file), true)
        } else {
            val valueFile = FileUtils.createFileIfNotExist(bookData,userid, md5BookUrl, file)
            valueFile.writeText(Gson().toJson(value))
            val bookUrlFile = File(FileUtils.getPath(bookData,userid, md5BookUrl, "bookUrl.txt"))
            if (!bookUrlFile.exists()) {
                bookUrlFile.writeText(bookUrl)
            }
        }
    }

    fun  getBookInfo(bookUrl: String,userid :String):Book?{
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        val file = "info.txt"
        val valueFile = FileUtils.createFileIfNotExist(bookData,userid, md5BookUrl, file)
        var book:Book?= null
        if (valueFile.exists()) {
            kotlin.runCatching {
                val type = object : TypeToken<Book?>() {}.type
                val l:Book?=Gson().fromJson(valueFile.readText(),type)
                book = l
            }
        }
        return book
    }

    fun  removeAllBookContent(bookUrl: String,userid :String){
        val md5BookUrl = MD5Utils.md5Encode(bookUrl)
        FileUtils.delete(FileUtils.getPath(bookData,userid, md5BookUrl,"content"), true)
    }
}