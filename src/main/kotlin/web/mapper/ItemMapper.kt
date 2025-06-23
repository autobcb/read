package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import web.model.Item

interface ItemMapper : BaseMapper<Item> {

    @Delete("Delete  FROM item WHERE userid = #{id}")
    fun delUserItem(@Param("id") id: String): Int

    @Select("SELECT * FROM item WHERE userid = #{id} order by name asc ")
    fun getlistbyuserid(@Param("id") id: String ): List<Item>

    @Select("SELECT * FROM item WHERE userid = #{id} and name = #{name} LIMIT 1 ")
    fun getbyname(@Param("id") id: String,@Param("name") name: String ): Item?
}