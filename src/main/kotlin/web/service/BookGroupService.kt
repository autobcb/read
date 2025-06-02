package web.service

import com.google.gson.reflect.TypeToken
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Configuration
import org.noear.solon.annotation.Inject
import org.noear.solon.data.cache.CacheService
import web.mapper.BookGroupMapper
import web.model.BookGroup

@Configuration
open class BookGroupService {

    @Inject
    private lateinit var cache: CacheService

    @Db("db")
    @Inject
    lateinit  var bookGroupMapper: BookGroupMapper

    private fun getlistkey(userid: String):String{
        return "bookgroup$userid"
    }

    fun getGroupbyuserid(id: String? ): List<BookGroup>{
        if(id.isNullOrBlank()) return listOf()
        val t= object : TypeToken<List<BookGroup>>() {}.type
        return cache.getOrStore(getlistkey(id), t,300) {
            bookGroupMapper.getGroupbyuserid( id )
        }
    }

    fun getGroupbyName(id: String , bookgroup: String): BookGroup?{
        cleancache(id)
        return bookGroupMapper.getGroupbyName(id, bookgroup)
    }

    fun delUsergroup(id: String): Int{
        cleancache(id)
        return bookGroupMapper.delUsergroup(id)
    }

    fun cleancache(userid: String?){
        if(userid.isNullOrBlank()) return
        cache.remove(getlistkey(userid))
    }
}