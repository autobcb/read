package web.Mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import web.Model.Booklist

interface BooklistMapper : BaseMapper<Booklist> {
    @Select("SELECT * FROM booklist WHERE userid = #{id} and book_url = #{url}")
    fun getbook(@Param("id") id: String ,@Param("url") url: String): Booklist?

    @Select("SELECT * FROM booklist WHERE userid = #{id}")
    fun getbooklistbyuserid(@Param("id") id: String ): List<Booklist>?

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

    @Delete("Delete  FROM booklist WHERE userid = #{id}")
    fun delUserbooks(@Param("id") id: String): Int
}