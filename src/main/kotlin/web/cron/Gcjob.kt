package web.cron

import org.noear.solon.scheduling.annotation.Scheduled

@Scheduled(fixedRate = 1000 * 60*60*24)
class Gcjob: Runnable  {

    override fun run(){
        System.gc()
    }
}