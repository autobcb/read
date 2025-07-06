package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import web.model.BookSource

interface BookSourceMapper : BaseMapper<BookSource> {
    @Select("SELECT * FROM book_source WHERE book_source_url = #{bookSourceUrl}  LIMIT 1")
    fun getBookSource(@Param("bookSourceUrl") bookSourceUrl: String): BookSource?

    @Select("SELECT * FROM book_source WHERE book_source_url like  concat('%',#{bookSourceUrl},'%') order by sourceorder  ,book_source_url asc  LIMIT 1")
    fun getBookSourcelike(@Param("bookSourceUrl") bookSourceUrl: String): BookSource?

    @Select("SELECT * FROM book_source WHERE enabled= #{enabled} order by sourceorder  ,book_source_url asc")
    fun getBookSourcelist(@Param("enabled") enabled: Boolean): List<BookSource>?

    @Select("SELECT * FROM book_source  order by sourceorder ,book_source_url asc")
    fun getallBookSourcelist(): List<BookSource>?

    @Update("UPDATE book_source set book_source_group= #{group} WHERE book_source_url = #{bookSourceUrl}")
    fun changegroup(@Param("bookSourceUrl") bookSourceUrl: String, @Param("group") group: String):Int

    @Update("UPDATE book_source set enabled= #{enabled} WHERE book_source_url = #{bookSourceUrl}")
    fun changeEnabled(@Param("bookSourceUrl") bookSourceUrl: String,@Param("enabled") enabled: Boolean):Int

    @Update("UPDATE book_source set enabled_explore= #{enabled} WHERE book_source_url = #{bookSourceUrl}")
    fun changeenabledExplore(@Param("bookSourceUrl") bookSourceUrl: String,@Param("enabled") enabled: Boolean):Int

    @Update("UPDATE book_source set sourceorder= #{sourceorder} WHERE book_source_url = #{bookSourceUrl}")
    fun changeorder(@Param("bookSourceUrl") bookSourceUrl: String,@Param("sourceorder") sourceorder: Int):Int


}