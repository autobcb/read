package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import web.model.BookGroup

interface BookGroupMapper : BaseMapper<BookGroup> {


    @Select("SELECT * FROM book_group WHERE userid = #{id} ")
    fun getGroupbyuserid(@Param("id") id: String ): List<BookGroup>

    @Select("SELECT * FROM book_group WHERE bookgroup = #{bookgroup} and userid = #{id} LIMIT 1" )
    fun getGroupbyName(@Param("id") id: String ,@Param("bookgroup") bookgroup: String): BookGroup?

    @Delete("Delete  FROM book_group WHERE userid = #{id}")
    fun delUsergroup(@Param("id") id: String): Int

}