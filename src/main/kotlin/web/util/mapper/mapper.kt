package web.util.mapper



import org.noear.solon.annotation.Bean
import org.noear.solon.annotation.Configuration
import org.noear.solon.annotation.Inject
import org.noear.solon.data.cache.CacheService
import web.service.*

@Configuration
class mapper {

    @Inject
    lateinit var booklistService: BooklistService


    @Inject
    lateinit var bookSourceService: BookSourceService


    @Inject
    lateinit var userBookSourceService: UserBookSourceService

    @Inject
    lateinit var usersService: UsersService


    @Inject
    lateinit var bookCacheService: BookCacheService

    @Inject
    lateinit var cacheService: CacheService



    companion object{
        var mapper:mapper?=null
        fun get():mapper{
            return mapper!!
        }
    }


    @Bean
    fun init(){
        mapper =this
    }
}