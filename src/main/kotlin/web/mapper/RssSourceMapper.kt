package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import web.model.BookSource
import web.model.RssSource

interface RssSourceMapper : BaseMapper<RssSource> {
    @Select("SELECT * FROM rss_source  order by sourceorder,source_url asc")
    fun getallSourcelist(): List<RssSource>?

    @Select("SELECT * FROM rss_source where enabled = true order by sourceorder,source_url asc")
    fun getEnabledSourcelist(): List<RssSource>?

    @Select("SELECT * FROM rss_source WHERE source_url = #{source_url} LIMIT 1")
    fun getRssSource(@Param("source_url") source_url: String): RssSource?

    @Update("UPDATE rss_source set sourceorder= #{sourceorder} WHERE source_url = #{source_url}")
    fun changeorder(@Param("source_url") source_url: String,@Param("sourceorder") sourceorder: Int):Int

    @Update("UPDATE rss_source set enabled= #{enabled} WHERE source_url = #{source_url}")
    fun changeEnabled(@Param("source_url") source_url: String,@Param("enabled") enabled: Boolean):Int

    @Update("UPDATE rss_source set source_group= #{group} WHERE source_url = #{source_url}")
    fun changegroup(@Param("source_url") source_url: String, @Param("group") group: String):Int
}