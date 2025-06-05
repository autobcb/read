package web.cron

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import kotlinx.coroutines.runBlocking
import org.noear.solon.annotation.Inject
import org.noear.solon.scheduling.annotation.Scheduled
import org.slf4j.LoggerFactory
import web.util.mapper.mapper
import web.util.read.Bookcache
import kotlin.concurrent.thread

@Scheduled(fixedRate = 1000 * 60)
class CacheJob: Runnable {
    private val logger = LoggerFactory.getLogger(BookJob::class.java)
    companion object {
        private var isupdatebookcron = false
    }

    @Inject(value = "\${admin.cron:true}", autoRefreshed=true)
    var cron:Boolean=true

    override fun run() = runBlocking{
        if(!cron){
            return@runBlocking
        }
        if (isupdatebookcron) {
            return@runBlocking
        }
        logger.info("开始添加缓存")
        isupdatebookcron = true
        val caches = mapper.get().bookCacheService.bookCacheMapper.selectList(QueryWrapper())
        runCatching {
            caches.forEach {
                thread { Bookcache.addcache(it.id!!) }
            }
        }
        logger.info("已添加缓存完成")
        isupdatebookcron = false
    }
}