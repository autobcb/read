package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import web.model.Booklist
import web.model.Sgread

interface SgreadMapper: BaseMapper<Sgread>  {

    @Select("SELECT * FROM sqread WHERE userid = #{id} and book_url = #{url}  LIMIT 1")
    fun getbook(@Param("id") id: String ,@Param("url") url: String): Booklist?

    @Delete("Delete  FROM sqread WHERE t < #{t}")
    fun deltimeout(@Param("t") t: Long): Int
}