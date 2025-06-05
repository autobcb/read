package web.util.mail

import org.noear.solon.annotation.Bean
import org.noear.solon.annotation.Configuration
import org.noear.solon.annotation.Inject


@Configuration
class MailConf {

    @Inject(value = "\${smtp.host:}", autoRefreshed=true)
    var host:String=""

    @Inject(value = "\${smtp.protocols:TLSv1.2}", autoRefreshed=true)
    var protocols:String=""

    @Inject(value = "\${smtp.port:}", autoRefreshed=true)
    var port:String=""

    @Inject(value = "\${smtp.account:}", autoRefreshed=true)
    var account:String=""

    @Inject(value = "\${smtp.password:}", autoRefreshed=true)
    var password:String=""

    @Inject(value = "\${smtp.personal:}", autoRefreshed=true)
    var personal:String=""

    @Inject(value = "\${smtp.codesubject:}", autoRefreshed=true)
    var codesubject:String=""

    @Inject(value = "\${smtp.codetext:}", autoRefreshed=true)
    var codetext:String=""

    companion object{
        var conf: MailConf?=null
        fun get(): MailConf {
            return conf!!
        }
    }


    @Bean
    fun init(){
        conf =this
    }

}