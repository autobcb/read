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
import kotlinx.coroutines.withTimeoutOrNull
import org.slf4j.LoggerFactory
import web.controller.api.ReadController.Companion.setChapterListbycache
import web.model.BaseSource
import web.model.Users

object BookCatalog {
    private var ma: MutableMap<String, Deferred<Pair<List<BookChapter>, Book>>> = mutableMapOf()
    private val mutex = Mutex()
    private val logger = LoggerFactory.getLogger(BookCatalog::class.java)


    fun getChapterlist(accessToken: String, user: Users, source: BaseSource, url: String): List<BookChapter> = runBlocking {
        logger.info("getChapterlist : $url")
        val key = "url:$url,${user.id}"
        // 获取或创建Deferred
        val deferred = ma[key] ?: mutex.withLock {
            ma[key] ?: async { getChapterList(accessToken, user, source, url) }.also { ma[key] = it }
        }
        // 执行并添加超时
        runCatching {
            // 在await()处添加5秒超时，避免影响其他共享同一个缓存的请求
            withTimeoutOrNull(120000) {
                deferred.await().first.also { setChapterListbycache(url, it, user.id!!) }
            } ?: emptyList()
        }.onSuccess {
            logger.info("$key 完成")
        }.onFailure {
            logger.error("书本目录获取失败:${it.message}")
            App.log("书本目录获取失败:${it.message}", accessToken)
            it.printStackTrace()
        }.getOrElse { emptyList() }.also { remove(key) }
    }

    fun getChapterlistandBook(accessToken: String, user: Users, source: BaseSource, url: String): Pair<List<BookChapter>, Book>? = runBlocking {
        logger.info("getChapterlist : $url")
        val key = "url:$url,${user.id}"
        // 获取或创建Deferred
        val deferred = ma[key] ?: mutex.withLock {
            ma[key] ?: async { getChapterList(accessToken, user, source, url) }.also { ma[key] = it }
        }
        // 执行并添加超时
        runCatching {
            // 在await()处添加5秒超时，避免影响其他共享同一个缓存的请求
            withTimeoutOrNull(90000) {
                deferred.await().also { setChapterListbycache(url, it.first, user.id!!) }
            }
        }.onSuccess {
            logger.info("$key 完成")
        }.onFailure {
            logger.error("书本目录获取失败:${it.message}")
            App.log("书本目录获取失败:${it.message}", accessToken)
            it.printStackTrace()
        }.getOrNull().also { remove(key) }
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