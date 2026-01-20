package web.cron

import book.model.BookSource
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import kotlinx.coroutines.*
import web.model.Booklist
import kotlinx.coroutines.sync.Semaphore
import org.noear.solon.annotation.Inject
import org.noear.solon.scheduling.annotation.Scheduled
import org.slf4j.LoggerFactory
import web.notification.Book
import web.util.mapper.mapper
import web.util.read.updatebook
import java.util.concurrent.TimeUnit

@Scheduled(fixedRate = 1000 * 60 * 60)
class BookJob : Runnable {
    @Inject(value = "\${admin.cron:true}", autoRefreshed = true)
    var cron: Boolean = true

    @Inject(value = "\${admin.update:false}", autoRefreshed = true)
    var update: Boolean = false

    private val logger = LoggerFactory.getLogger(BookJob::class.java)

    companion object {
        private var isupdatebookcron = false
    }

    override fun run() = runBlocking {
        runCatching {
            val t=System.currentTimeMillis()-60*60*24*1000*30
            mapper.get().sgreadMapper.deltimeout(t)
        }
        if (!cron || !update) {
            return@runBlocking
        }
        if (isupdatebookcron) {
            logger.info("定时更新任务已经在执行中")
            return@runBlocking
        }
        logger.info("更新书本信息")
        isupdatebookcron = true
        val semaphore = Semaphore(5)

        withTimeoutOrNull(TimeUnit.HOURS.toMillis(2)) { // 整体任务超时2小时
            val booklist = mapper.get().booklistMapper.selectList(QueryWrapper<Booklist>())
            logger.info("需要更新的书籍数量:${booklist.size}")

            coroutineScope {
                val jobs = mutableListOf<Job>()
                runCatching {
                    booklist.forEach {
                        runCatching {
                            if (it.origin != "loc_book" && (it.latestChapterTime
                                    ?: 0) < System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000
                            ) {
                                val user = mapper.get().usersMapper.getUser(it.userid!!)
                                if (user != null) {
                                    val source = if (user.source == 2) {
                                        mapper.get().userBookSourceMapper.getBookSource(it.origin ?: "", user.id ?: "")
                                            ?.toBaseSource()
                                    } else {
                                        mapper.get().bookSourceMapper.getBookSource(it.origin ?: "")?.toBaseSource()
                                    }
                                    if (source != null) {
                                        val s = BookSource.fromJson(source.json).getOrNull() ?: BookSource()
                                        if (s.phonehttp != true) {
                                            val book = it
                                            val userForUpdate = user // 复用已获取的用户
                                            val job = launch {
                                                semaphore.acquire() // 获取信号量
                                                try {
                                                    logger.info("更新${book.name}")
                                                    withTimeoutOrNull(TimeUnit.MINUTES.toMillis(1)) { // 单本书更新超时1分钟
                                                        runCatching {
                                                            updatebook(book, source, userForUpdate)
                                                            Book.sendNotification(userForUpdate)
                                                        }.onFailure { e ->
                                                            logger.error("更新${book.name}失败:${e.message}", e)
                                                        }
                                                    } ?: logger.warn("更新${book.name}超时")
                                                } finally {
                                                    semaphore.release() // 释放信号量
                                                    logger.info("完成更新${book.name}")
                                                }
                                            }
                                            jobs.add(job)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                jobs.joinAll()
            }
        } ?: logger.error("定时更新任务整体超时")

        logger.info("完成更新书本信息")
        isupdatebookcron = false
    }
}