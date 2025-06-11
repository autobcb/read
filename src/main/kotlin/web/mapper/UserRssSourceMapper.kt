package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import web.model.UserRssSource

interface UserRssSourceMapper : BaseMapper<UserRssSource> {


    @Select("SELECT * FROM user_rss_source  WHERE userid = #{userid} order by sourceorder,source_url asc")
    fun getallSourcelist(@Param("userid") userid: String): List<UserRssSource>


    @Select("SELECT * FROM user_rss_source WHERE source_url = #{source_url} and userid = #{userid} LIMIT 1")
    fun getRssSource(@Param("source_url") source_url: String, @Param("userid") userid: String): UserRssSource?

    @Update("UPDATE user_rss_source set sourceorder= #{sourceorder} WHERE id = #{id}")
    fun changeorder(@Param("id") id: String, @Param("sourceorder") sourceorder: Int): Int

    @Update("UPDATE user_rss_source set enabled= #{enabled} WHERE id = #{id}")
    fun changeEnabled(@Param("id") id: String, @Param("enabled") enabled: Boolean):Int

    @Delete("Delete  FROM user_rss_source WHERE source_url = #{source_url} and userid = #{userid}")
    fun delRssSource(@Param("source_url") source_url: String,@Param("userid") userid: String): Int

    @Update("UPDATE user_rss_source set source_group= #{group} WHERE id = #{id}")
    fun changegroup(@Param("id") id: String, @Param("group") group: String):Int
}