package web.cron

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import org.noear.solon.annotation.Inject
import org.noear.solon.scheduling.annotation.Scheduled
import org.slf4j.LoggerFactory
import web.util.mapper.mapper
import web.util.read.Bookcache

@Suppress("unused")
@Scheduled(fixedRate = 1000 * 60)
class CacheJob: Runnable {
    private val logger = LoggerFactory.getLogger(BookJob::class.java)
    companion object {
        private var isupdatebookcron = false
    }

    @Inject(value = "\${admin.cron:true}", autoRefreshed=true)
    var cron:Boolean=true


    override fun run() {
        return;
        if(!cron){
           return
        }
        if (isupdatebookcron) {
            return
        }

        isupdatebookcron = true
        logger.info("Cache job started")
        val caches = mapper.get().bookCacheMapper.selectList(QueryWrapper())
        for (cache in caches) {
            Bookcache.addcache(cache)
        }
        logger.info("Cache job end")
        isupdatebookcron = false
    }
}