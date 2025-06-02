package web.service

import com.google.gson.reflect.TypeToken
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Configuration
import org.noear.solon.annotation.Inject
import org.noear.solon.data.cache.CacheService
import web.mapper.BooklistMapper
import web.model.Booklist

@Configuration
open  class BooklistService {

    @Inject
    private lateinit var cache: CacheService

    @Db("db")
    @Inject
    lateinit  var booklistMapper: BooklistMapper

    private fun getlistkey(userid: String):String{
        return "booklist$userid"
    }

    fun getbook( id: String,  url: String): Booklist?{
        return booklistMapper.getbook(id, url)
    }

    fun getbooklistbyuserid( id: String?): List<Booklist>?{
        if(id.isNullOrBlank()) return listOf()
        val t= object : TypeToken<List<Booklist>>() {}.type
        return cache.getOrStore(getlistkey(id), t,300) {
            booklistMapper.getbooklistbyuserid( id )
        }
    }

    fun getbooklistbynametype( id: String , name: String ,author: String ,type: Int): List<Booklist>{
        return booklistMapper.getbooklistbynametype(id ,name,author,type)
    }

    fun updatetime( id: String,userid: String?, latest_chapter_title: String
                   , latest_chapter_time: Long
                   ,last_check_time: Long
                   , last_check_count: Int
                   , total_chapter_num: Int):Int{
        cleancache(userid)
        return booklistMapper.updatetime(  id,latest_chapter_title,latest_chapter_time,last_check_time,last_check_count,total_chapter_num)
    }

    fun updatetimefail( id: String,userid: String?
                       ,last_check_time: Long
                       , last_check_count: Int):Int{
        cleancache(userid)
        return booklistMapper.updatetimefail( id,last_check_time,last_check_count)
    }

    fun updatepos( id: String,userid: String?
                  , dur_chapter_title: String
                  , dur_chapter_index: Int
                  , dur_chapter_pos: Double, dur_chapter_time:Long,readchapter:String):Int{
        cleancache(userid)
        return booklistMapper.updatepos(id,dur_chapter_title,dur_chapter_index,dur_chapter_pos,dur_chapter_time,readchapter)
    }

    fun changetype( id: String,userid: String?,type: Int):Int{
        cleancache(userid)
        return booklistMapper.changetype(id, type)
    }

    fun changebookgroup( id: String,userid: String?,bookgroup: String):Int{
        cleancache(userid)
        return booklistMapper.changebookgroup( id, bookgroup )
    }

    fun delbookgroup( id: String,userid: String?,bookgroup: String):Int{
        cleancache(userid)
        return booklistMapper.delbookgroup( id, bookgroup )
    }

    fun delUserbooks( id: String): Int{
        cleancache(id)
        return booklistMapper.delUserbooks( id )
    }

    fun upbookgroup( id: String,bookgroup: String,newbookgroup: String):Int{
        cleancache(id)
        return booklistMapper.upbookgroup( id, bookgroup , newbookgroup )
    }

    fun upbookinfo( id: String,userid: String?, name: String, author: String,custom_cover_url: String, custom_intro: String):Int{
        cleancache(userid)
        return booklistMapper.upbookinfo( id, name, author, custom_cover_url, custom_intro)
    }

    fun uprule( id: String,userid: String?,useReplaceRule: Boolean):Int{
        cleancache(userid)
        return booklistMapper.uprule(id,useReplaceRule)
    }
    
    fun cleancache(userid: String?){
        if(userid.isNullOrBlank()) return
        cache.remove(getlistkey(userid))
    }
}