package web.Mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import web.Model.BookSource

interface BookSourceMapper : BaseMapper<BookSource> {
    @Select("SELECT * FROM book_source WHERE book_source_url = #{bookSourceUrl}")
    fun getBookSource(@Param("bookSourceUrl") bookSourceUrl: String): BookSource?

    @Select("SELECT * FROM book_source WHERE enabled= #{enabled}")
    fun getBookSourcelist(@Param("enabled") enabled: Boolean): List<BookSource>?


    @Update("UPDATE book_source set enabled= #{enabled} WHERE book_source_url = #{bookSourceUrl}")
    fun changeEnabled(@Param("bookSourceUrl") bookSourceUrl: String,@Param("enabled") enabled: Boolean):Int

    @Update("UPDATE book_source set book_source.enabled_explore= #{enabled} WHERE book_source_url = #{bookSourceUrl}")
    fun changeenabledExplore(@Param("bookSourceUrl") bookSourceUrl: String,@Param("enabled") enabled: Boolean):Int

}