package book.webBook



import book.app.App
import book.model.*
import book.util.AppPattern.JS_PATTERN
import book.util.help.cookieJarHeader
import book.util.http.ConcurrentRateLimiter
import book.util.http.SSLHelper
import book.util.http.StrResponse
import book.util.printOnDebug
import book.webBook.analyzeRule.AnalyzeUrl
import book.webBook.exception.NoStackTraceException
import com.script.ScriptException
import okhttp3.Response
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.mozilla.javascript.WrappedException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.net.ConnectException
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

private val logger: Logger = LoggerFactory.getLogger(WBook::class.java)

class WBook (val bookSource: BookSource, val debugLog: Boolean = true, var debugLogger: DebugLog? = null,val userid:String?=null,val usertocken:String?=null){

    constructor(bookSourceString: String, _userid:String, _usertocken:String?=null, debugLog: Boolean = true) : this(BookSource.fromJson(bookSourceString).getOrNull() ?: BookSource(), debugLog,userid=_userid,usertocken=_usertocken)

    init {
        bookSource.userid = userid
        bookSource.usertocken = usertocken
        bookSource.debugLog = debugLogger
    }

    val sourceUrl: String
        get() = bookSource.bookSourceUrl


    val debugger: DebugLog?
        get() {
            if (debugLogger != null) {
                return debugLogger
            }
            if (debugLog) {
                return Debug
            }
            return null
        }


    suspend fun searchBook(key: String, page: Int? = 1): List<SearchBook> {
        val variableBook =  SearchBook()
        return bookSource.searchUrl?.let {searchUrl->
            val analyzeUrl = AnalyzeUrl(
                mUrl = searchUrl,
                key = key,
                page = page,
                baseUrl = bookSource.bookSourceUrl,
                source = bookSource,
                ruleData = variableBook,
                headerMapF = bookSource.getHeaderMap(true),debugLog = debugger
            )
            var res = analyzeUrl.getStrResponseAwait()

            bookSource.loginCheckJs?.let { checkJs ->
                if (checkJs.isNotBlank()) {
                    res = analyzeUrl.evalJS(checkJs, res) as StrResponse
                }
            }

            if(debugger != null){
                debugger?.log("搜索源码Qwq${res.body}");
            }

            checkRedirect(bookSource, res)


            BookList.analyzeBookList(
                res.body,
                bookSource,
                analyzeUrl,
                res.url,
                variableBook,
                true,
                debugLog = debugger
            ).map {
                it.tocHtml = ""
                it.infoHtml = ""
                it
            }.also {list->
                list.forEach{book->
                    book.variableMap.forEach {
                        book.putVariable(it.key, it.value)
                    }
                }
            }
        }?:arrayListOf()
    }

    /**
     * 发现
     */
    suspend fun exploreBook(
        url: String,
        page: Int? = 1
    ): List<SearchBook> {
        val variableBook =  SearchBook()
        val analyzeUrl = AnalyzeUrl(
            mUrl = url,
            page = page,
            baseUrl = bookSource.bookSourceUrl,
            source = bookSource,
            ruleData = variableBook,
            headerMapF = bookSource.getHeaderMap(true),debugLog = debugger
        )
        var res = analyzeUrl.getStrResponseAwait()
        //检测书源是否已登录
        bookSource.loginCheckJs?.let { checkJs ->
            if (checkJs.isNotBlank()) {
                res = analyzeUrl.evalJS(checkJs, result = res) as StrResponse
            }
        }
        if(debugger != null){
            debugger?.log("发现源码Qwq${res.body}");
        }
        checkRedirect(bookSource, res)
        return BookList.analyzeBookList(
            res.body,
            bookSource,
            analyzeUrl,
            res.url,
            variableBook,
            false,
            debugLog = debugger
        ).also {list->
            list.forEach{book->
                book.variableMap.forEach {
                    book.putVariable(it.key, it.value)
                }
            }
        }
    }

    /**
     * 书籍信息
     */
    suspend fun getBookInfo(book: Book, canReName: Boolean = true): Book {
        book.type = bookSource.bookSourceType
        if (!book.infoHtml.isNullOrEmpty()) {
            book.userid=userid?:""
            BookInfo.analyzeBookInfo(
                book,
                book.infoHtml,
                bookSource,
                book.bookUrl,
                book.bookUrl,
                canReName
            )
            return book
        } else {
            return getBookInfo(book.bookUrl, canReName)
        }
    }

    /**
     * 书籍信息
     */
    suspend fun getBookInfo(bookUrl: String, canReName: Boolean = true): Book {
        val book = Book()
        book.bookUrl = bookUrl
        book.userid=userid?:""
        book.origin = bookSource.bookSourceUrl
        book.originName = bookSource.bookSourceName
        book.originOrder = bookSource.customOrder
        book.type = bookSource.bookSourceType
        val analyzeUrl = AnalyzeUrl(
            mUrl = book.bookUrl,
            baseUrl = bookSource.bookSourceUrl,
            source = bookSource,
            ruleData = book,
            headerMapF = bookSource.getHeaderMap(true),debugLog = debugger
        )
        var res = analyzeUrl.getStrResponseAwait()
        //检测书源是否已登录
        bookSource.loginCheckJs?.let { checkJs ->
            if (checkJs.isNotBlank()) {
                res = analyzeUrl.evalJS(checkJs, result = res) as StrResponse
            }
        }
        if(debugger != null){
            debugger?.log("书籍源码Qwq${res.body}");
        }
        checkRedirect(bookSource, res)
        BookInfo.analyzeBookInfo(book, res.body, bookSource, book.bookUrl, res.url, canReName, debugLog = debugger)
        book.tocHtml = null
        return book
    }

    /**
     * 目录
     */
    suspend fun getChapterList(
        book: Book
    ): List<BookChapter> {
        book.userid=userid?:""
        book.type = bookSource.bookSourceType
        return if (book.bookUrl == book.tocUrl && !book.tocHtml.isNullOrEmpty()) {
            BookChapterList.analyzeChapterList(
                book,
                book.tocHtml,
                bookSource,
                book.tocUrl,
                book.tocUrl,
                debugLog = debugger
            )
        } else {
            val analyzeUrl = AnalyzeUrl(
                mUrl = book.tocUrl,
                baseUrl = book.bookUrl,
                source = bookSource,
                ruleData = book,
                headerMapF = bookSource.getHeaderMap(true),debugLog = debugger
            )
            var res = analyzeUrl.getStrResponseAwait()
            //检测书源是否已登录
            bookSource.loginCheckJs?.let { checkJs ->
                if (checkJs.isNotBlank()) {
                    res = analyzeUrl.evalJS(checkJs, result = res) as StrResponse
                }
            }
            if(debugger != null){
                debugger?.log("目录源码Qwq${res.body}");
            }
            checkRedirect(bookSource, res)
            return BookChapterList.analyzeChapterList(book, res.body, bookSource, book.tocUrl, res.url, debugLog = debugger)
        }
    }

    /**
     * 章节内容
     */
    suspend fun getBookContent(
        book: Book,
        bookChapter: BookChapter,
        // bookChapterUrl:String,
        nextChapterUrl: String? = null
    ): String {
        book.userid=userid?:""
        bookChapter.userid=userid?:""
        //println(bookSource.ruleContent?.content)
        //println(GSON.toJson(bookChapter))
        if (bookSource.getContentRule().content.isNullOrEmpty()) {
            debugger?.log(bookSource.bookSourceUrl, "⇒正文规则为空,使用章节链接: ${bookChapter.url}")
            return bookChapter.url
        }
        if (bookChapter.isVolume && bookChapter.url.startsWith(bookChapter.title)) {
            debugger?.log(bookSource.bookSourceUrl, "⇒一级目录正文不解析规则")
            return bookChapter.tag ?: ""
        }
//        val body = if (book != null && bookChapter.url == book.bookUrl && !book.tocHtml.isNullOrEmpty()) {
//            book.tocHtml
//        } else {
        logger.info("bookChapterUrl: {}", bookChapter.url, bookChapter.getAbsoluteURL())
        val analyzeUrl = AnalyzeUrl(
            mUrl = bookChapter.getAbsoluteURL(),
            baseUrl = book.tocUrl,
            source = bookSource,
            ruleData = book,
            chapter = bookChapter,
            headerMapF = bookSource.getHeaderMap(true),debugLog = debugger
        )
        var res = analyzeUrl.getStrResponseAwait(
            jsStr = bookSource.getContentRule().webJs,
            sourceRegex = bookSource.getContentRule().sourceRegex,
        )
        //检测书源是否已登录
        bookSource.loginCheckJs?.let { checkJs ->
            if (checkJs.isNotBlank()) {
                res = analyzeUrl.evalJS(checkJs, result = res) as StrResponse
            }
        }
        if(debugger != null){
            debugger?.log("正文源码Qwq${res.body}");
        }
        checkRedirect(bookSource, res)
        return BookContent.analyzeContent(
            res.body,
            book,
            bookChapter,
            bookSource,
            bookChapter.url,
            res.url,
            nextChapterUrl,
            debugLog = debugger
        )
    }

   companion object{
       suspend fun  getSpeakStream(
           httpTts: HttpTTS,
           speakText: String,
           speechRate:Int): InputStream {
           try {
               val analyzeUrl = AnalyzeUrl(
                   httpTts.url,
                   speakText = speakText.trim().replace(" ","").replace("\\s+".toRegex(), " ").replace("\"","").replace("'",""),
                   speakSpeed = speechRate,
                   source = httpTts,
                   readTimeout = 300 * 1000L,
                   debugLog = null
               )
               var response = analyzeUrl.getResponseAwait()
               val checkJs = httpTts.loginCheckJs
               if (checkJs?.isNotBlank() == true) {
                   response = analyzeUrl.evalJS(checkJs, response) as Response
               }
               response.headers["Content-Type"]?.let { contentType ->
                   val ct = httpTts.contentType
                   if (contentType == "application/json") {
                       throw NoStackTraceException(response.body!!.string())
                   } else if (ct?.isNotBlank() == true) {
                       if (!contentType.matches(ct.toRegex())) {
                           throw NoStackTraceException("TTS服务器返回错误：" + response.body!!.string())
                       }
                   }
               }
               response.body!!.byteStream().let { stream ->
                   return stream
               }
           } catch (e: Exception) {
               e.printStackTrace()
               App.log("TTS出错：" + e.message,httpTts.usertocken!!)
               throw e
           }
       }
   }

    /**
     * 检测重定向
     */
    private fun checkRedirect(bookSource: BookSource, response: StrResponse) {
        response.raw.priorResponse?.let {
            if (it.isRedirect) {
                if(debugger != null){
                    debugger?.log(bookSource.bookSourceUrl, "≡检测到重定向(${it.code})")
                    debugger?.log(bookSource.bookSourceUrl, "┌重定向后地址")
                    debugger?.log(bookSource.bookSourceUrl, "└${response.url}")
                }
            }
        }
    }
}