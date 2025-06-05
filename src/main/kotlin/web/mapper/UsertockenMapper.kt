package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.noear.solon.data.annotation.Cache
import web.model.Users
import web.model.Usertocken

@Mapper
interface UsertockenMapper: BaseMapper<Usertocken> {


    @Select("SELECT * FROM usertocken WHERE id = #{id} LIMIT 1")
    fun getUsertocken(@Param("id") id: String): Usertocken?

    @Select("SELECT * FROM usertocken WHERE userid = #{id}")
    fun getUsertockens(@Param("id") id: String): List<Usertocken>?

    @Delete("Delete  FROM usertocken WHERE userid = #{id}")
    fun delUsertockens(@Param("id") id: String): Int

}