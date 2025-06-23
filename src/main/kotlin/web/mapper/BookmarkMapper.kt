package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import web.model.Bookmark

interface BookmarkMapper: BaseMapper<Bookmark> {

    @Delete("Delete  FROM bookmark WHERE userid = #{id}")
    fun delUserBookmar(@Param("id") id: String): Int

    @Select("SELECT * FROM bookmark WHERE userid = #{id} and boolurl = #{boolurl} order by createtime DESC")
    fun getbybook(@Param("id") id: String,@Param("boolurl") boolurl: String): List<Bookmark>


}