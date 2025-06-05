package web.service

import com.google.gson.reflect.TypeToken
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Configuration
import org.noear.solon.annotation.Inject
import org.noear.solon.data.cache.CacheService
import web.mapper.BookCacheMapper
import web.model.BookCache

@Configuration
open class BookCacheService {

    @Inject
    private lateinit var cache: CacheService

    @Db("db")
    @Inject
    lateinit  var bookCacheMapper: BookCacheMapper

    private fun getlistkey(userid: String):String{
        return "bookcache$userid"
    }

    fun delUserCache( id: String): Int{
        cleancache(id)
        return bookCacheMapper.delUserCache(id);
    }

    fun delBookCache( id: String , bookid: String): Int{
        cleancache(id)
        return bookCacheMapper.delBookCache(id,bookid)
    }

    fun getCache( id: String , bookid: String): BookCache?{
        return bookCacheMapper.getCache(id, bookid)
    }

    fun updatetime( id: String,userid: String?, total_chapter_num: Int):Int{
        cleancache(userid)
        return bookCacheMapper.updatetime(id,total_chapter_num)
    }

    fun getlistbyuserid( id: String? ): List<BookCache>{
        if(id.isNullOrBlank()) return listOf()
        val t= object : TypeToken<List<BookCache>>() {}.type
        return cache.getOrStore(getlistkey(id), t,300) {
            bookCacheMapper.getlistbyuserid(id)
        }
    }

    fun cleancache(userid: String?){
        if(userid.isNullOrBlank()) return
        cache.remove(getlistkey(userid))
    }

}