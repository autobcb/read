package web.Mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import web.Model.Usertocken

interface UsertockenMapper: BaseMapper<Usertocken> {

    @Select("SELECT * FROM usertocken WHERE userid = #{id}")
    fun getUsertockens(@Param("id") id: String): List<Usertocken>?

    @Delete("Delete  FROM usertocken WHERE userid = #{id}")
    fun delUsertockens(@Param("id") id: String): Int

}