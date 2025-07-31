package web.cron

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import kotlinx.coroutines.runBlocking
import org.noear.solon.annotation.Inject
import org.noear.solon.scheduling.annotation.Scheduled
import org.slf4j.LoggerFactory
import web.util.mapper.mapper

@Scheduled(fixedRate = 1000 * 60*60)
class RssJob: Runnable {

    @Inject(value = "\${admin.cron:true}", autoRefreshed = true)
    var cron: Boolean = true

   // private val logger = LoggerFactory.getLogger(RssJob::class.java)

    companion object {
        private var isupdatecron = false
    }

    override fun run() = runBlocking {
        if (!cron) {
            return@runBlocking
        }
        if (isupdatecron) {
            return@runBlocking
        }
        val users=mapper.get().usersService.usersMapper.selectList(QueryWrapper())
        for (user in users) {
            if (user.source == 2) {
                runCatching {
                    val sources=mapper.get().userRssSourceService.getallSourcelist(user.id!!)
                    if( sources.isNotEmpty()) {
                        var order =0
                        for (source in sources) {
                            if(source.sourceorder != order) mapper.get().userRssSourceService.changeorder(source.id?:"",user.id!!,order)
                            order++
                        }
                    }
                }
            }
        }

        val sources=mapper.get().rssSourceService.getallSourcelist()
        if(sources != null && sources.isNotEmpty()) {
            var order =0
            for (source in sources) {
                if(source.sourceorder != order) mapper.get().rssSourceService.changeorder(source.sourceUrl,order)
                order++
            }
        }
    }
}