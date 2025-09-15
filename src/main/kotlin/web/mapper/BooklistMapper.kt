package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import web.model.Booklist

interface BooklistMapper : BaseMapper<Booklist> {
    @Select("SELECT * FROM booklist WHERE userid = #{id} and book_url = #{url}  LIMIT 1")
    fun getbook(@Param("id") id: String ,@Param("url") url: String): Booklist?

    @Select("SELECT * FROM booklist WHERE userid = #{id}  ")
    fun getbooklistbyuserid(@Param("id") id: String ): List<Booklist>?

    @Select("SELECT * FROM booklist WHERE userid = #{id} and name = #{name}  ")
    fun getbooklistbyuseridandname(@Param("id") id: String ,@Param("name") name: String ): List<Booklist>?

    @Select("SELECT * FROM booklist WHERE userid = #{id}  and name = #{name}  and author = #{author}  and type = #{type}")
    fun getbooklistbynametype(@Param("id") id: String ,@Param("name") name: String ,@Param("author") author: String ,@Param("type") type: Int): List<Booklist>

    @Update("UPDATE booklist set latest_chapter_title= #{latest_chapter_title}  ,latest_chapter_time = #{latest_chapter_time},  last_check_time = #{last_check_time} ,last_check_count= #{last_check_count} ,total_chapter_num = #{total_chapter_num}  WHERE id = #{id}")
    fun updatetime(@Param("id") id: String,@Param("latest_chapter_title") latest_chapter_title: String
                             ,@Param("latest_chapter_time") latest_chapter_time: Long
                             ,@Param("last_check_time") last_check_time: Long
                             ,@Param("last_check_count") last_check_count: Int
                             ,@Param("total_chapter_num") total_chapter_num: Int):Int

    @Update("UPDATE booklist set last_check_time = #{last_check_time} ,last_check_count= #{last_check_count}   WHERE id = #{id}")
    fun updatetimefail(@Param("id") id: String
                   ,@Param("last_check_time") last_check_time: Long
                   ,@Param("last_check_count") last_check_count: Int):Int

    @Update("UPDATE booklist set dur_chapter_time =#{dur_chapter_time}, readchapter = #{readchapter}, dur_chapter_title = #{dur_chapter_title} ,dur_chapter_index= #{dur_chapter_index} ,dur_chapter_pos= #{dur_chapter_pos}  WHERE id = #{id}")
    fun updatepos(@Param("id") id: String
                  ,@Param("dur_chapter_title") dur_chapter_title: String
                       ,@Param("dur_chapter_index") dur_chapter_index: Int
                       ,@Param("dur_chapter_pos") dur_chapter_pos: Double,@Param("dur_chapter_time") dur_chapter_time:Long,@Param("readchapter") readchapter:String):Int

    @Update("UPDATE booklist set type =#{type} WHERE id = #{id}")
    fun changetype(@Param("id") id: String,@Param("type") type: Int):Int

    @Update("UPDATE booklist set bookgroup =#{bookgroup} WHERE id = #{id}")
    fun changebookgroup(@Param("id") id: String,@Param("bookgroup") bookgroup: String):Int

    @Update("UPDATE booklist set bookgroup = '' WHERE userid = #{id} and bookgroup = #{bookgroup}")
    fun delbookgroup(@Param("id") id: String,@Param("bookgroup") bookgroup: String):Int

    @Delete("Delete  FROM booklist WHERE userid = #{id}")
    fun delUserbooks(@Param("id") id: String): Int

    @Update("UPDATE booklist set bookgroup = #{newbookgroup} WHERE userid = #{id} and bookgroup = #{bookgroup}")
    fun upbookgroup(@Param("id") id: String,@Param("bookgroup") bookgroup: String,@Param("newbookgroup") newbookgroup: String):Int

    @Update("UPDATE  booklist set custom_cover_url = #{custom_cover_url} ,name = #{name} ,author = #{author} ,custom_intro = #{custom_intro} WHERE id = #{id}")
    fun upbookinfo(@Param("id") id: String,@Param("name") name: String,@Param("author") author: String,@Param("custom_cover_url") custom_cover_url: String,@Param("custom_intro") custom_intro: String):Int

    @Update("UPDATE booklist set use_replace_rule = #{useReplaceRule} WHERE id = #{id} ")
    fun uprule(@Param("id") id: String,@Param("useReplaceRule") useReplaceRule: Boolean):Int

    @Update("UPDATE booklist set origin =#{neworigin} WHERE origin = #{origin} and userid = #{id}")
    fun updatebysource(@Param("id") id: String,@Param("origin") origin: String,@Param("neworigin") neworigin: String):Int
}