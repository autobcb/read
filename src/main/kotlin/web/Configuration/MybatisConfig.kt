package web.Configuration

import javax.sql.DataSource
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean

@org.springframework.context.annotation.Configuration
class MybatisConfig {

    @Autowired
    lateinit var dataSource: DataSource

    @Bean
    fun sqlSessionFactory( dataSource: DataSource):MybatisSqlSessionFactoryBean{
        val sqlSessionFactory = MybatisSqlSessionFactoryBean()
        sqlSessionFactory.setDataSource(dataSource)
        return sqlSessionFactory
    }
}


