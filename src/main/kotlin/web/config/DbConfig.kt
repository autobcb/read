package web.config


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.solon.annotation.Db
import org.dromara.autotable.core.AutoTableBootstrap
import org.dromara.autotable.core.AutoTableGlobalConfig
import org.dromara.autotable.core.config.PropertyConfig
import org.dromara.autotable.core.dynamicds.SqlSessionFactoryManager
import org.noear.solon.annotation.Bean
import org.noear.solon.annotation.Configuration
import web.model.*
import web.mapper.UsersMapper


@Configuration
open class DbConfig {
    companion object{
        private val models= arrayOf(Booklist::class.java,BookSource::class.java
         ,Usertocken::class.java,Code::class.java,Users::class.java,BookGroup::class.java,BookCache::class.java
            ,UserBookSource::class.java,ReplaceRule::class.java,HttpTts::class.java,UserRssSource::class.java, BackGround::class.java,RssSource::class.java,
           )
    }

    @Bean
    open fun autotable(@Db("db") sqlSessionFactory: SqlSessionFactory,@Db("db") usersMapper: UsersMapper){
        var users:List<Users> = listOf()
        runCatching {
            usersMapper.selectList(QueryWrapper<Users>().last("LIMIT 1"))
        }.onFailure {
            runCatching {
                users=usersMapper.getAllUser()
                usersMapper.Drop()
            }
        }
        SqlSessionFactoryManager.setSqlSessionFactory(sqlSessionFactory)
        val autoTableProperties = PropertyConfig()
        autoTableProperties.enable = true
        autoTableProperties.modelClass= models
        AutoTableGlobalConfig.setAutoTableProperties(autoTableProperties)
        AutoTableBootstrap.start()
        users.forEach{
            usersMapper.insert(it)
        }
    }


}