package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import web.model.BackGround

interface BackGroundMapper : BaseMapper<BackGround>{

    @Delete("Delete  FROM back_ground WHERE userid = #{id}")
    fun delUserGround(@Param("id") id: String): Int

    @Select("SELECT * FROM back_ground WHERE userid = #{id} ")
    fun getlistbyuserid(@Param("id") id: String ): List<BackGround>
}