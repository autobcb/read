package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import web.model.UserBookSource


interface UserBookSourceMapper : BaseMapper<UserBookSource> {
    @Select("SELECT * FROM user_book_source WHERE book_source_url = #{bookSourceUrl} and userid = #{userid} LIMIT 1")
    fun getBookSource(@Param("bookSourceUrl") bookSourceUrl: String,@Param("userid") userid: String): UserBookSource?

    @Select("SELECT * FROM user_book_source WHERE enabled= #{enabled} and userid = #{userid} order by sourceorder asc")
    fun getBookSourcelist(@Param("enabled") enabled: Boolean,@Param("userid") userid: String): List<UserBookSource>?

    @Select("SELECT * FROM user_book_source  WHERE userid = #{userid} order by sourceorder asc")
    fun getallBookSourcelist(@Param("userid") userid: String): List<UserBookSource>?

    @Update("UPDATE user_book_source set book_source_group= #{group} WHERE id = #{id}")
    fun changegroup(@Param("id") id: String, @Param("group") group: String):Int


    @Update("UPDATE user_book_source set enabled= #{enabled} WHERE id = #{id}")
    fun changeEnabled(@Param("id") id: String, @Param("enabled") enabled: Boolean):Int

    @Update("UPDATE user_book_source set enabled_explore= #{enabled} WHERE id = #{id}")
    fun changeenabledExplore(@Param("id") id: String, @Param("enabled") enabled: Boolean):Int

    @Update("UPDATE user_book_source set sourceorder= #{sourceorder} WHERE id = #{id}")
    fun changeorder(@Param("id") id: String, @Param("sourceorder") sourceorder: Int):Int

    @Delete("Delete  FROM user_book_source WHERE userid = #{userid}")
    fun delUserSource(@Param("userid") userid: String): Int

    @Delete("Delete  FROM user_book_source WHERE book_source_url = #{bookSourceUrl} and userid = #{userid}")
    fun delBookSource(@Param("bookSourceUrl") bookSourceUrl: String,@Param("userid") userid: String): Int

}