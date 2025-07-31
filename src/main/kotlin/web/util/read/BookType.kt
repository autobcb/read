package web.util.read


import web.model.Booklist
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.slf4j.LoggerFactory
import web.model.BaseSource
import web.model.Users
import java.lang.Thread.sleep
import kotlin.concurrent.thread

object BookType {
    private var ma:MutableMap<String, Deferred<Booklist>> = mutableMapOf()
    private val mutex = Mutex()
    private val logger = LoggerFactory.getLogger(BookType::class.java)


    fun getBookInfo(bookUrl: String,user: Users): Booklist? = runBlocking{
        val key = "url:${bookUrl},${user.id}"
        val deferred = ma[key]
        if (deferred == null){
            null
        }else{
            deferred.await()
        }
    }

    fun UpdateBookInfo(accessToken: String, user: Users, source: BaseSource,mybook:Booklist): Booklist = runBlocking {
        val key = "url:${mybook.bookUrl},${user.id}"
        // 使用安全调用和 Elvis 操作符替代 !!
        var deferred = ma[key]
        if (deferred == null) {
           mutex.withLock {
                // 双重检查锁定，避免竞态条件
                deferred = ma[key] ?: async { updateBookInfo(accessToken, user, source, mybook) }
                ma[key] = deferred
            }
        }
        // 使用 val 代替 var，提高不可变性
        val book = runCatching {
            deferred!!.await()
        }.getOrElse { mybook }
        book
    }

    private fun updateBookInfo(accessToken: String, user: Users, source: BaseSource,mybook:Booklist) :Booklist{
        runCatching {
            val  re = BookCatalog.getChapterlistandBook(accessToken,user,source,mybook.bookUrl?:" ")
            if(re != null){
                val list=re.first
                if (list.isNotEmpty()){
                    mybook.lastCheckCount=list.size
                    mybook.latestChapterTitle=list.last().title
                }
                mybook.type=re.second.type
            }
        }
        thread {
            val key = "url:${mybook.bookUrl},${user.id}"
            runBlocking {
                sleep(1000*30)
                logger.info("del $key")
                remove(key)
            }
        }
        return mybook
    }

    @Suppress("DeferredResultUnused")
    private suspend fun remove(key:String){
        mutex.withLock {
            ma.remove(key)
        }
    }

}