package web.util.read

import book.model.Book
import book.model.BookChapter
import book.webBook.WBook
import book.webBook.localBook.LocalBook
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import org.slf4j.LoggerFactory
import web.controller.api.ReadController.Companion.getBookContentbycache
import web.controller.api.ReadController.Companion.getChapterListbycache
import web.model.BaseSource
import web.model.BookCache
import web.model.Users
import web.util.mapper.mapper
import kotlin.concurrent.thread

object Bookcache {


    private var ma:MutableMap<String, Deferred<Any>> = mutableMapOf()
    private val mutex = Mutex()

    private val logger = LoggerFactory.getLogger(Bookcache::class.java)

    val semaphore = Semaphore(5)

    fun  addcache(cache: BookCache)  {
        val id=cache.id!!
        if( !ma.containsKey(id)) {
            thread { addcache1(cache) }
        }else{
            logger.info("${cache.name} is already added")
        }
    }

    private  fun addcache1(cache: BookCache) =runBlocking{
        val id=cache.id!!
        mutex.withLock {
            ma[id]= async{
                runCatching {
                    semaphore.acquire()
                    cache(cache)
                    semaphore.release()
                }.apply {  removecache(id)   }
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


    suspend fun cache(cache: BookCache)  {
        val mutex = Mutex()
        //val zx=(cache.num ?: 0) < (cache.totalChapterNum ?: 0)
        val user= mapper.get().usersMapper.getUser(cache.userid!!) ?: return
        val book= mapper.get().booklistMapper.selectById(cache.bookid) ?: return
        var source:BaseSource? = null
        if(book.origin != "loc_book"){
            source = if(user.source == 2){
                mapper.get().userBookSourceMapper.getBookSource(book.origin?:"",user.id?:"")?.toBaseSource()
            }else{
                mapper.get().bookSourceMapper.getBookSource(book.origin?:"")?.toBaseSource()
            }
            if (source == null) return
        }
        cache.num=0
        val list = (cache.cacheindex?:"").split(",").toMutableSet()
        logger.info("缓存开始${book.name}")
        val (old,timeout)= getChapterListbycache(book.bookUrl?:" ",user.id!!)
        var chapterlist: List<BookChapter>? =old
        if(chapterlist == null || timeout){
            runCatching {
                chapterlist= getlist(book.bookUrl?:" ",source!!,user,"")
            }
            if(chapterlist == null){
                if(!old.isNullOrEmpty()){
                    chapterlist = old
                }else{
                    throw Exception("chapterlist is null")
                }
            }
        }
        for(i in 0..<(cache.totalChapterNum ?: 0)){
            val x=i
            if(list.contains(x.toString())){
                if (chapterlist[x].isVolume){
                    cache.num=(cache.num ?: 0)+1
                    continue
                }
                val  re=getBookContentbycache(book.bookUrl!!, x,user.id!!)?:""
                if(re.isNotEmpty()){
                    cache.num=(cache.num ?: 0)+1
                    continue
                }else{
                    list.remove(x.toString())
                }
            }
            if (chapterlist[x].isVolume){
                list.add(x.toString())
                cache.cacheindex= list.joinToString(",")
                cache.num=(cache.num ?: 0)+1
                mapper.get().bookCacheMapper.updateById(cache)
                continue
            }
            if(mapper.get().bookCacheMapper.selectById(cache.id) == null) {
                break
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
                        var (chapterlist,timeout) = getChapterListbycache(url,user.id!!)
                        if (chapterlist == null || timeout) {
                            chapterlist = getlist(url)
                        }
                        val b = Book.initLocalBook(url, url, "")
                        re= LocalBook.getContent(b, chapterlist[x]).toString()
                    }
                }
            }
            if ( re.length > 50 || book.origin == "loc_book"){
                mutex.withLock {
                    list.add(x.toString())
                    cache.cacheindex= list.joinToString(",")
                    cache.num=(cache.num ?: 0)+1
                    mapper.get().bookCacheMapper.updateById(cache)
                }
                logger.info("完成缓存${book.name},index:$x")
            }else{
                //println(re)
                logger.info("缓存失败${book.name},index:$x")
            }
            if(z && book.origin != "loc_book") delay(1000)
        }
        logger.info("缓存检测完成${book.name}")
        cache.cacheindex= list.joinToString(",")
        mapper.get().bookCacheMapper.updateById(cache)
       // mapper.get().bookCacheService.bookCacheMapper.updateById(cache).also { mapper.get().bookCacheService.cleancache(user.id) }
        logger.info("缓存完成${book.name}")
    }



    private suspend fun getBookContent(accessToken:String, user: Users, source: BaseSource, url:String, index:Int):String {
        var (chapterlist,_)= getChapterListbycache(url,user.id!!)
        if(chapterlist == null || index >= chapterlist.size ){
            chapterlist= getlist(url,source,user,accessToken)
        }
        val webBook = WBook(source.json,user.id!!,accessToken, false)
        val book = getbook(accessToken,user,source,url)
        val nexturl=if(index+1 < chapterlist.size) chapterlist[index+1].url else ""
        return webBook.getBookContent(book,chapterlist[index],nexturl)
    }

}