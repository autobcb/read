package web

import io.legado.app.androidId
import org.dromara.autotable.springboot.EnableAutoTable
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.EnableTransactionManagement
import web.Properties.AdminProperties
import web.Util.LogFactory
import web.Util.cache.checkfile
import web.Util.read.Updatecookie

@MapperScan("web.Mapper")
@EnableAutoTable(basePackages = ["web.Model"])
@SpringBootApplication
@Component
@EnableTransactionManagement
@EnableConfigurationProperties(AdminProperties::class)
class SpringApplication : LogFactory() {

    @Autowired
    lateinit var adminProperties: AdminProperties

    @Bean
    fun commandLineRunner(): CommandLineRunner {
        return CommandLineRunner { args ->
            run {
                checkfile()
                Updatecookie()
                androidId.androidId=adminProperties.androidid
            }
        }
    }
}
