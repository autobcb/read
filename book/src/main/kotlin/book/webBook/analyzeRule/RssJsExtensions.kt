package book.webBook.analyzeRule

import book.app.App
import book.model.BaseSource
import book.webBook.DebugLog
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class RssJsExtensions( private val source: BaseSource? = null,): JsExtensions {

    override  var debugLog: DebugLog? =null
    override fun getSource(): BaseSource? {
        return source
    }

    override val logger: Logger
        get() =  LoggerFactory.getLogger(RssJsExtensions::class.java)



    fun searchBook(key: String) {
        //SearchActivity.start(activity, key)
        App.searchBook(key,"",getSource()?.usertocken?:"")
    }

    fun searchBook(key: String,url: String) {
        //SearchActivity.start(activity, key)
        App.searchBook(key,url,getSource()?.usertocken?:"")
    }

    fun addBook(bookUrl: String) {
        App.addBook(bookUrl,getSource()?.usertocken?:"")
        //activity.showDialogFragment(AddToBookshelfDialog(bookUrl))
    }
}