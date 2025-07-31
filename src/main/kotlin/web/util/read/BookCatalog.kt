package web.util.read


import book.app.App
import book.model.Book
import book.model.BookChapter
import book.webBook.WBook
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.slf4j.LoggerFactory
import web.controller.api.ReadController.Companion.setChapterListbycache
import web.model.BaseSource
import web.model.Users

object BookCatalog {
    private var ma:MutableMap<String, Deferred<Pair<List<BookChapter>, Book>>> = mutableMapOf()
    private val mutex = Mutex()
    private val logger = LoggerFactory.getLogger(BookCatalog::class.java)


    fun getChapterlist(accessToken: String, user: Users, source: BaseSource, url: String): List<BookChapter> = runBlocking {
        logger.info("getChapterlist : $url")
        val key = "url:$url,${user.id}"
        // 使用安全调用和 Elvis 操作符替代 !!
        var deferred = ma[key]
        if (deferred == null) {
            mutex.withLock {
                // 双重检查锁定，避免竞态条件
                deferred = ma[key] ?: async { getChapterList(accessToken, user, source, url) }
                ma[key] = deferred
            }
        }
        // 使用 val 代替 var，提高不可变性
        val list = runCatching {
            deferred!!.await().first.also {  setChapterListbycache(url,it,user.id!!) }
        }.onSuccess {
            logger.info("$key 完成")
        }.onFailure {
            logger.error("书本目录获取失败:${it.message}")
            App.log("书本目录获取失败:${it.message}", accessToken)
            it.printStackTrace()
        }.getOrElse { emptyList() }
        remove(key)
        list
    }

    fun getChapterlistandBook(accessToken: String, user: Users, source: BaseSource, url: String): Pair<List<BookChapter>, Book>? = runBlocking {
        logger.info("getChapterlist : $url")
        val key = "url:$url,${user.id}"
        // 使用安全调用和 Elvis 操作符替代 !!
        var deferred = ma[key]
        if (deferred == null) {
            mutex.withLock {
                // 双重检查锁定，避免竞态条件
                deferred = ma[key] ?: async { getChapterList(accessToken, user, source, url) }
                ma[key] = deferred
            }
        }
        // 使用 val 代替 var，提高不可变性
        val result = runCatching {
            deferred!!.await().also {  setChapterListbycache(url,it.first,user.id!!) }
        }.onSuccess {
            logger.info("$key 完成")
        }.onFailure {
            logger.error("书本目录获取失败:${it.message}")
            App.log("书本目录获取失败:${it.message}", accessToken)
            it.printStackTrace()
        }.getOrNull()
        remove(key)
        result
    }


    private suspend fun getChapterList(accessToken:String, user: Users, source: BaseSource, url:String):Pair<List<BookChapter>, Book>{
        val book = getbook(accessToken,user,source,url)
        val webBook = WBook(source.json,user.id!!,accessToken, false)
        return Pair(webBook.getChapterList(book),book)
    }


    @Suppress("DeferredResultUnused")
    private suspend fun remove(key:String){
        mutex.withLock {
           ma.remove(key)
        }
    }
}