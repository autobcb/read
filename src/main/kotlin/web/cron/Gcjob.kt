package web.cron

import org.noear.solon.scheduling.annotation.Scheduled

@Suppress("unused")
@Scheduled(fixedRate = 1000 * 60*60*2)
class Gcjob: Runnable  {

    override fun run(){
        System.gc()
    }
}