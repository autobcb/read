package web.util.read

import book.model.Book
import book.webBook.WBook
import book.webBook.exception.ConcurrentException
import book.webBook.localBook.LocalBook
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import org.slf4j.LoggerFactory
import web.controller.api.ReadController.Companion.getBookContentbycache
import web.controller.api.ReadController.Companion.getBookbycache
import web.controller.api.ReadController.Companion.getChapterListbycache
import web.controller.api.ReadController.Companion.setBookContentbycache
import web.controller.api.ReadController.Companion.setBookbycache
import web.controller.api.ReadController.Companion.setChapterListbycache
import web.model.BaseSource
import web.model.Users
import web.util.mapper.mapper
import java.lang.Thread.sleep

object Bookcache {

    private var ma:MutableMap<String, Deferred<Any>> = mutableMapOf()
    private val mutex = Mutex()

    private val logger = LoggerFactory.getLogger(Bookcache::class.java)

    private val semaphore = Semaphore(10)

    private val semaphore2 = Semaphore(50)

     fun  addcache(key:String) = runBlocking {
        var deferred: Deferred<Any>?
         mutex.withLock {
            deferred= ma[key]
        }
        if(deferred == null) {
            mutex.withLock {
                deferred=async{
                   runCatching {
                       cache(key)
                   }.apply {  removecache(key)   }
                }
                ma[key]= deferred!!
            }
        }
    }

    @Suppress("DeferredResultUnused")
    private suspend fun  removecache(key:String) {
        logger.info("缓存完成remove $key")
        mutex.withLock {
            ma.remove(key)
        }
    }

    fun cache(id:String) = runBlocking {
        val cache=mapper.get().bookCacheService.bookCacheMapper.selectById(id)
        if(cache != null ) {
            var zx=(cache.num ?: 0) < (cache.totalChapterNum ?: 0)
            val user= mapper.get().usersService.getUser(cache.userid) ?: return@runBlocking
            val book= mapper.get().booklistService.booklistMapper.selectById(cache.bookid) ?: return@runBlocking
            var source:BaseSource? = null
            if(book.origin != "loc_book"){
                source = if(user.source == 2){
                    mapper.get().userBookSourceService.getBookSource(book.origin?:"",user.id?:"")?.toBaseSource()
                }else{
                    mapper.get().bookSourceService.getBookSource(book.origin?:"")?.toBaseSource()
                }
                if (source == null) return@runBlocking
            }

            val jobs = mutableListOf<Job>()
            cache.num=0
            val list = (cache.cacheindex?:"").split(",").toMutableSet()
            logger.info("缓存开始${book.name}")
            for(i in 0..<(cache.totalChapterNum ?: 0)){
                val x=i
                if(list.contains(x.toString())){
                    var re=""
                   runCatching{
                       semaphore2.acquire()
                        re=getBookContentbycache(book.bookUrl!!, x,user.id!!)?:""
                    }.let {
                       semaphore2.release()
                   }
                    if(re.isNotEmpty()){
                        cache.num=(cache.num ?: 0)+1
                        continue
                    }else{
                        list.remove(x.toString())
                    }
                }
                launch{
                    semaphore.acquire()
                    if(mapper.get().bookCacheService.bookCacheMapper.selectById(id) == null) {
                        semaphore.release()
                        return@launch
                    }
                    var z=false
                    var re=""
                    runCatching {
                        re=getBookContentbycache(book.bookUrl!!, x,user.id!!)?:""
                        if(re.isEmpty()){
                            z=true
                            if(book.origin != "loc_book"){
                                re=getBookContent("",user,source!!,book.bookUrl?:" ",x)
                            }else{
                                val url=book.bookUrl?:" "
                                var chapterlist = getChapterListbycache(url,user.id!!)
                                if (chapterlist == null) {
                                    chapterlist = getlist(url).also {
                                        setChapterListbycache(url, it,user.id!!)
                                    }
                                }
                                val b = Book.initLocalBook(url, url, "")
                                re= LocalBook.getContent(b, chapterlist[x]).toString().also { setBookContentbycache(url,it,x,user.id!!) }
                            }
                        }
                    }
                    if ( re.length > 50 || book.origin == "loc_book"){
                        mutex.withLock {
                            list.add(x.toString())
                            cache.cacheindex= list.joinToString(",")
                            cache.num=(cache.num ?: 0)+1
                            if(zx) mapper.get().bookCacheService.bookCacheMapper.updateById(cache).also { mapper.get().bookCacheService.cleancache(user.id) }
                        }
                        logger.info("完成缓存${book.name},index:$x")
                    }else{
                        println(re)
                        logger.info("缓存失败${book.name},index:$x")
                    }
                    if(z && book.origin != "loc_book") sleep(1000)
                    semaphore.release()
                }.let {
                    jobs.add(it)
                }
            }
            logger.info("缓存检测完成${book.name}")
            mutex.withLock {
                cache.cacheindex= list.joinToString(",")
                mapper.get().bookCacheService.bookCacheMapper.updateById(cache).also { mapper.get().bookCacheService.cleancache(user.id) }
            }
            zx=true
            jobs.joinAll()
            mapper.get().bookCacheService.bookCacheMapper.updateById(cache).also { mapper.get().bookCacheService.cleancache(user.id) }
            logger.info("缓存完成${book.name}")
        }
    }

    private suspend fun getBookContent(accessToken:String, user: Users, source: BaseSource, url:String, index:Int):String {
        var chapterlist= getChapterListbycache(url,user.id!!)
        if(chapterlist == null){
            chapterlist= getlist(url,source,user.id!!,accessToken).also{
                setChapterListbycache(url,it,user.id!!)
            }
        }
        val webBook = WBook(source.json,user.id!!,accessToken, false)
        val book= getBookbycache(url,user.id!!).let {
            it ?: getbook(webBook, url)!!.also { book -> setBookbycache(url,book,user.id!!) }
        }
        val systembook=mapper.get().booklistService.getbook(user.id!!,url)
        if(systembook!=null){
            book.durChapterIndex=systembook.durChapterIndex?:0
        }
        val nexturl=if(index+1 < chapterlist.size) chapterlist[index+1].url else ""
        return webBook.getBookContent(book,chapterlist[index],nexturl).also { setBookContentbycache(url,it,index,user.id!!) }
    }

    private fun getbook(webBook: WBook, url:String): Book?= runBlocking{
        var book: Book?=null
        runCatching {
            book= webBook.getBookInfo(url,canReName = true)
        }.onFailure {
            if(it is ConcurrentException){
                logger.info("getbook 并发原因？？？？")
                delay(1000)
                book=getbook(webBook,url)
            }
        }
        book
    }
}