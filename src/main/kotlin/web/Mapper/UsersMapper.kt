package web.Mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import web.Model.Users

interface  UsersMapper : BaseMapper<Users> {
    @Select("SELECT * FROM users WHERE id = #{id}")
    fun getUser(@Param("id") id: String): Users?

    @Select("SELECT * FROM users WHERE username = #{username}" )
    fun getUserByusername(@Param("username") username: String): Users?

    @Update("UPDATE users set password = #{password}   WHERE id = #{id}")
    fun changepass(@Param("id") id: String
                       ,@Param("password") password: String):Int

    @Delete("Delete booklist,users,usertocken  FROM booklist,users,usertocken WHERE booklist.userid = #{id} and usertocken.userid = #{id} and users.id = #{id}")
    fun delUserall(@Param("id") id: String): Int
}