package web.service

import com.google.gson.reflect.TypeToken
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Configuration
import org.noear.solon.annotation.Inject
import org.noear.solon.data.cache.CacheService
import web.mapper.BookSourceMapper
import web.model.BookSource


@Configuration
open class BookSourceService {
    @Inject
    private lateinit var cache: CacheService

    @Db("db")
    @Inject
    lateinit  var booksSourceMapper: BookSourceMapper


    private fun getlistkey(enabled: Boolean):String{
        return "sourcelist$enabled"
    }

    private fun getalllistkey():String{
        return "sourcealllist"
    }

    fun getBookSource( bookSourceUrl: String): BookSource?{
        return booksSourceMapper.getBookSource(bookSourceUrl)
    }

    fun getBookSourcelike( bookSourceUrl: String): BookSource?{
        return booksSourceMapper.getBookSourcelike(bookSourceUrl)
    }

    fun getBookSourcelist( enabled: Boolean): List<BookSource>?{
        val t= object : TypeToken<List<BookSource>>() {}.type
        return cache.getOrStore(getlistkey(enabled), t,600) {
            booksSourceMapper.getBookSourcelist(enabled)
        }
    }

    fun getallBookSourcelist(): List<BookSource>?{
        val t= object : TypeToken<List<BookSource>>() {}.type
        return cache.getOrStore(getalllistkey(), t,600) {
            booksSourceMapper.getallBookSourcelist()
        }
    }

    fun changegroup( bookSourceUrl: String?, group: String?):Int{
        cleancache()
        return booksSourceMapper.changegroup(bookSourceUrl?:"", group?:"")
    }

    fun changeEnabled( bookSourceUrl: String, enabled: Boolean):Int{
        cleancache()
        return booksSourceMapper.changeEnabled(bookSourceUrl, enabled)
    }

    fun changeenabledExplore( bookSourceUrl: String, enabled: Boolean):Int{
        cleancache()
        return booksSourceMapper.changeenabledExplore(bookSourceUrl, enabled)
    }

    fun changeorder(bookSourceUrl: String,sourceorder: Int):Int{
        cleancache()
        return booksSourceMapper.changeorder(bookSourceUrl, sourceorder)
    }

    fun cleancache(){
        cache.remove(getalllistkey())
        cache.remove(getlistkey(true))
        cache.remove(getlistkey(false))
    }
}