package web.util.mapper



import org.noear.solon.annotation.Bean
import org.noear.solon.annotation.Configuration
import org.noear.solon.annotation.Inject
import org.noear.solon.data.cache.CacheService
import web.mapper.BookCacheMapper
import web.mapper.BookSourceMapper
import web.mapper.BooklistMapper
import web.mapper.RssSourceMapper
import web.mapper.SgreadMapper
import web.mapper.UserBookSourceMapper
import web.mapper.UserRssSourceMapper
import web.mapper.UsersMapper

@Configuration
class mapper {

    @Inject
    lateinit var booklistMapper: BooklistMapper


    @Inject
    lateinit var bookSourceMapper: BookSourceMapper

    @Inject
    lateinit var sgreadMapper: SgreadMapper


    @Inject
    lateinit var userBookSourceMapper: UserBookSourceMapper

    @Inject
    lateinit var usersMapper: UsersMapper


    @Inject
    lateinit var bookCacheMapper: BookCacheMapper

    @Inject
    lateinit var cacheService: CacheService

    @Inject
    lateinit var rssSourceMapper: RssSourceMapper


    @Inject
    lateinit var userRssSourceMapper: UserRssSourceMapper

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