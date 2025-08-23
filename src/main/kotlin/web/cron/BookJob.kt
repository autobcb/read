package web.cron

import book.model.BookSource
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore
import org.noear.solon.annotation.Inject
import org.noear.solon.scheduling.annotation.Scheduled
import org.slf4j.LoggerFactory
import web.notification.Book
import web.util.mapper.mapper
import web.util.read.updatebook

@Scheduled(fixedRate = 1000 * 60*60)
class BookJob: Runnable {
    @Inject(value = "\${admin.cron:true}", autoRefreshed=true)
    var cron:Boolean=true

    private val logger = LoggerFactory.getLogger(BookJob::class.java)
    companion object {
        private var isupdatebookcron = false
    }
    override fun run() = runBlocking{
        if(!cron){
            return@runBlocking
        }
        if (isupdatebookcron) {
            return@runBlocking
        }
        val semaphore = Semaphore(5)
        val jobs = mutableListOf<Job>()
        logger.info("更新书本信息")
        isupdatebookcron = true
        runCatching {
            val booklist = mapper.get().booklistMapper.selectList(QueryWrapper())
            booklist.forEach {
                runCatching {
                    if(it.origin != "loc_book" && it.latestChapterTime?:0 > System.currentTimeMillis() - 7*24*60*60*1000){
                        val user = mapper.get().usersMapper.getUser(it.userid!!)
                        if (user != null) {
                            val source = if(user.source == 2){
                                mapper.get().userBookSourceMapper.getBookSource(it.origin?:"",user.id?:"")?.toBaseSource()
                            }else{
                                mapper.get().bookSourceMapper.getBookSource(it.origin?:"")?.toBaseSource()
                            }
                            if (source != null ) {
                                val s=BookSource.fromJson(source.json).getOrNull()?: BookSource()
                                if(s.phonehttp != true){
                                    val book=it
                                    launch{
                                        semaphore.acquire()
                                        logger.info("更新${book.name}")
                                        runCatching {
                                            val user = mapper.get().usersMapper.getUser(book.userid?:"admin")
                                            updatebook(book, source,user!!)
                                            Book.sendNotification(user)
                                        }
                                        logger.info("完成更新${book.name}")
                                        semaphore.release()
                                    }.let {
                                        jobs.add(it)
                                    }
                                }

                            }
                        }

                    }
                }
            }
        }
        jobs.joinAll()
        logger.info("完成更新书本信息")
        isupdatebookcron = false
    }





}