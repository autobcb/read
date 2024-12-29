package web.Cron

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import web.Model.Booklist
import web.Util.LogFactory
import web.Util.mapper
import web.Util.read.Updatecookie
import web.Util.read.updatebook
import kotlin.math.log

@Component
@EnableScheduling
class cron : LogFactory() {

    companion object {
        private var isupdatebookcron = false
    }

    @Scheduled(cron = "0 0/30 * * * ? ")
    fun updatebookcron() = runBlocking {
        if (isupdatebookcron) {
            return@runBlocking
        }
        logger.info("更新书本信息")
        isupdatebookcron = true
        runCatching {
            var booklist = mapper.get().booklistMapper.selectList(QueryWrapper<Booklist>())
            booklist.forEach {
                runCatching {
                    if(it.origin != "loc_book"){
                        var source = mapper.get().bookSourcemapper.getBookSource(it.origin)
                        if (source != null ) {
                            updatebook(it, source)
                        }
                    }
                }
            }
        }
        isupdatebookcron = false
    }

    @Scheduled(cron = "0 0/1 * * * ? ")
    fun readcookie()  {
        logger.info("更新cookie")
        Updatecookie()
    }
}