package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import web.model.BookCache
import web.model.Booklist

interface BookCacheMapper : BaseMapper<BookCache>{

    @Delete("Delete  FROM book_cache WHERE userid = #{id}")
    fun delUserCache(@Param("id") id: String): Int


    @Delete("Delete  FROM book_cache WHERE userid = #{id} and bookid = #{bookid}")
    fun delBookCache(@Param("id") id: String ,@Param("bookid") bookid: String): Int

    @Select("SELECT * FROM book_cache WHERE userid = #{id} and bookid = #{bookid} LIMIT 1")
    fun getCache(@Param("id") id: String ,@Param("bookid") bookid: String): BookCache?


    @Update("UPDATE book_cache set total_chapter_num = #{total_chapter_num}  WHERE id = #{id}")
    fun updatetime(@Param("id") id: String,@Param("total_chapter_num") total_chapter_num: Int):Int


    @Select("SELECT * FROM book_cache WHERE userid = #{id} ")
    fun getlistbyuserid(@Param("id") id: String ): List<BookCache>

}