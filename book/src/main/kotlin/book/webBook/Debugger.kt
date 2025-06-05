package book.WBook

import book.model.Book
import book.model.BookChapter
import book.model.RssArticle
import book.model.RssSource
import book.util.HtmlFormatter
import book.util.isAbsUrl
import book.webBook.DebugLog
import book.webBook.WBook
import book.webBook.rss.Rss
import book.webBook.sortUrls
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.util.*

private val logger: Logger = LoggerFactory.getLogger(Debugger::class.java)
class Debugger(val logMsg: (String) -> Unit) : DebugLog {
    private val debugTimeFormat = SimpleDateFormat("[mm:ss.SSS]", Locale.getDefault())
    private var startTime: Long = System.currentTimeMillis()
    private var debugSource: String? = null

    fun log(
        sourceUrl: String?,
        msg: String?
    ) {
        log(sourceUrl, msg, false)
    }

    override fun log(message: String) {
        val time = debugTimeFormat.format(Date(System.currentTimeMillis() - startTime))
        logMsg("$time $message")
    }


    override fun log(
        sourceUrl: String?,
        msg: String?,
        isHtml: Boolean
    ) {
        if (sourceUrl == null || msg == null) return
        logger.info("sourceUrl: {}, msg: {}", sourceUrl, msg)
        var printMsg = msg

        if (isHtml) {
            printMsg = HtmlFormatter.format(msg)
        }
        val time = debugTimeFormat.format(Date(System.currentTimeMillis() - startTime))
        printMsg = "$time $printMsg"
        logMsg(printMsg)
    }

    suspend fun startDebug( rssSource: RssSource) {
        debugSource = rssSource.sourceUrl
        log(debugSource, "︾开始解析")
        val sort = rssSource.sortUrls().first()
        kotlin.runCatching {
            Rss(debugLogger = this@Debugger).getArticles( sort.first, sort.second, rssSource, 1)
        }.onSuccess {
                if (it.first.isEmpty()) {
                    log(debugSource, "⇒列表页解析成功，为空")
                    log(debugSource, "︽解析完成")
                } else {
                    val ruleContent = rssSource.ruleContent
                    if (!rssSource.ruleArticles.isNullOrBlank() && rssSource.ruleDescription.isNullOrBlank()) {
                        log(rssSource.sourceUrl, "︽列表页解析完成")
                        if (ruleContent.isNullOrEmpty()) {
                            log(debugSource, "⇒内容规则为空，默认获取整个网页")
                        } else {
                            rssContentDebug( it.first[0], ruleContent, rssSource)
                        }
                    } else {
                        log(debugSource, "⇒存在描述规则，不解析内容页")
                        log(debugSource, "︽解析完成")
                    }
                }
            }
            .onFailure {
                log(debugSource, it.message)
            }
    }

    private suspend fun rssContentDebug(
        rssArticle: RssArticle,
        ruleContent: String,
        rssSource: RssSource
    ) {
        log(debugSource, "︾开始解析内容页")
        kotlin.runCatching {
            Rss(debugLogger = this@Debugger).getContent( rssArticle, ruleContent, rssSource)
        }.onSuccess {
                log(debugSource, it)
                log(debugSource, "︽内容页解析完成")
        } .onFailure {
                log(debugSource, it.message)
            }
    }



    suspend fun startDebug(WBook: WBook, key: String) {
        val bookSource = WBook.bookSource
        WBook.debugLogger = this@Debugger
        WBook.bookSource.debugLog=this@Debugger
        startTime = System.currentTimeMillis()
        when {
            key.isAbsUrl() -> {
                val book = Book()
                book.origin = bookSource.bookSourceUrl
                book.bookUrl = key
                log(bookSource.bookSourceUrl, "⇒开始访问详情页:$key")
                infoDebug(WBook, book)
            }
            key.contains("::") -> {
                val url = key.substringAfter("::")
                log(bookSource.bookSourceUrl, "⇒开始访问发现页:$url")
                exploreDebug(WBook, url)
            }
            key.startsWith("++") -> {
                val url = key.substring(2)
                val book = Book()
                book.origin = bookSource.bookSourceUrl
                book.tocUrl = url
                log(bookSource.bookSourceUrl, "⇒开始访目录页:$url")
                tocDebug(WBook, book)
            }
            key.startsWith("--") -> {
                val url = key.substring(2)
                val book = Book()
                book.origin = bookSource.bookSourceUrl
                log(bookSource.bookSourceUrl, "⇒开始访正文页:$url")
                val chapter = BookChapter()
                chapter.title = "调试"
                chapter.url = url
                contentDebug(WBook, book, chapter, null)
            }
            else -> {
                log(bookSource.bookSourceUrl, "⇒开始搜索关键字:$key")
                searchDebug(WBook, key)
            }
        }
    }

    private suspend fun exploreDebug(WBook: WBook, url: String) {
        WBook.debugLogger = this@Debugger
        log("︾开始解析发现页")
        runCatching {
            WBook.exploreBook(url, 1)
        }.onSuccess { exploreBooks ->
            exploreBooks.let {
                if (exploreBooks.isNotEmpty()) {
                    log(WBook.sourceUrl, "︽发现页解析完成")
                    infoDebug(WBook, exploreBooks[0].toBook())
                } else {
                    log(WBook.sourceUrl, "︽未获取到书籍")
                }
            }
        }.onFailure {
            log(WBook.sourceUrl, "Error: " + it.localizedMessage)
            throw it
        }
    }

    private suspend fun searchDebug(WBook: WBook, key: String) {
        WBook.debugLogger = this@Debugger
        log(msg = "︾开始解析搜索页")
        runCatching {
            WBook.searchBook(key, 1)
        }.onSuccess { searchBooks ->
            searchBooks.let {
                if (searchBooks.isNotEmpty()) {
                    log(WBook.sourceUrl, "︽搜索页解析完成")
                    infoDebug(WBook, searchBooks[0].toBook())
                } else {
                    log(WBook.sourceUrl, "︽未获取到书籍")
                }
            }
        }.onFailure {
            log(WBook.sourceUrl, "Error: " + it.localizedMessage)
            throw it
        }
    }

    private suspend fun infoDebug(WBook: WBook, book: Book) {
        WBook.debugLogger = this@Debugger
        log(msg = "︾开始解析详情页")
        runCatching { WBook.getBookInfo(book.bookUrl) }
            .onSuccess {
                log(WBook.sourceUrl, "︽详情页解析完成")
                tocDebug(WBook, it)
            }
            .onFailure {
                log(WBook.sourceUrl, "Error: " + it.localizedMessage)
                throw it
            }
    }

    private suspend fun tocDebug(WBook: WBook, book: Book) {
        WBook.debugLogger = this@Debugger
        log(msg = "︾开始解析目录页")
        runCatching {
            WBook.getChapterList(book)
        }
            .onSuccess { chapterList ->
                chapterList?.let {
                    if (it.isNotEmpty()) {
                        log(WBook.sourceUrl, "︽目录页解析完成")
                        for( i in 0..it.size - 1){
                            if(it[i].isVolume) continue
                            log(WBook.sourceUrl, "开始获取正文：${ it[i].url}")
                            val nextChapterUrl = if (it.size > i+1) it[i+1].url else null
                            contentDebug(WBook, book, it[i], nextChapterUrl)
                            break
                        }

                    } else {
                        log(WBook.sourceUrl, "︽目录列表为空")
                    }
                }
            }
            .onFailure {
                log(WBook.sourceUrl, "Error: " + it.localizedMessage)
                throw it
            }
    }

    private suspend fun contentDebug(
        webBook: WBook,
        book: Book,
        bookChapter: BookChapter,
        nextChapterUrl: String?
    ) {
        webBook.debugLogger = this@Debugger
        log(webBook.sourceUrl, "︾开始解析正文页")
        runCatching { webBook.getBookContent(book, bookChapter, nextChapterUrl) }
            .onSuccess {
                log(webBook.sourceUrl, "︽正文页解析完成")
            }
            .onFailure {
                log(webBook.sourceUrl, "Error: " + it.localizedMessage)
            }
    }
}