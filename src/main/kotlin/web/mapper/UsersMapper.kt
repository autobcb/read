package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.*
import org.noear.solon.data.annotation.Cache
import web.model.Users

@Mapper
interface  UsersMapper : BaseMapper<Users> {


    @Select("SELECT * FROM users WHERE id = #{id} LIMIT 1")
    fun getUser(@Param("id") id: String): Users?

    @Select("SELECT * FROM users")
    fun getAllUser(): List<Users>

    @Select("SELECT * FROM users WHERE username = #{username} LIMIT 1" )
    fun getUserByusername(@Param("username") username: String): Users?

    @Select("SELECT * FROM users WHERE email = #{email}" )
    fun getUserByemail(@Param("email") email: String): List<Users>

    @Update("UPDATE users set password = #{password}   WHERE id = #{id}")
    fun changepass(@Param("id") id: String,@Param("password") password: String):Int

    @Delete("Delete booklist,users,usertocken  FROM booklist,users,usertocken WHERE booklist.userid = #{id} and usertocken.userid = #{id} and users.id = #{id}")
    fun delUserall(@Param("id") id: String): Int

    @Update("<script>  UPDATE users set email = #{user.email}  , phone = #{user.phone} , updatetime = #{user.updatetime} ," +
            "<if test=\"user.password != null\">" +
            "password = #{user.password}," +
            "</if> " +
            "allow_up_txt = #{user.AllowUpTxt} ,allow_img = #{user.AllowImg} , comment = #{user.comment}  ,allow_cache = #{user.AllowCache} ,source  = #{user.source} " +
            "WHERE id = #{user.id}</script>")
    fun updateinfo(@Param("user") user: Users):Int


    @Delete("DROP TABLE users")
    fun Drop(): Int
}