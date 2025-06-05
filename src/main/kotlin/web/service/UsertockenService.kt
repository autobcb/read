package web.service


import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.*
import org.noear.solon.data.annotation.Cache
import org.noear.solon.data.cache.CacheService
import web.mapper.UsertockenMapper
import web.model.Usertocken

@Configuration
open class UsertockenService {

    @Inject
    private lateinit var cache: CacheService

    @Db("db")
    @Inject
    lateinit var usertockenMapper: UsertockenMapper
    @Cache(key="tocken_\${id}", seconds = 60)
    fun getUsertocken( id: String?): Usertocken?{
        if(id.isNullOrBlank()) return null
        val key = "tocken_:$id"
        return cache.getOrStore(key, Usertocken::class.java,60) {
            usertockenMapper.getUsertocken(id)
        }
    }

    fun getUsertockens( id: String): List<Usertocken>?{
        return usertockenMapper.getUsertockens(id)
    }

    fun delUsertockens(id: String): Int{
        return usertockenMapper.delUsertockens(id)
    }

    fun cleancache(id: String?){
        if(id.isNullOrBlank()) return
        cache.remove("tocken_:$id")
    }
}