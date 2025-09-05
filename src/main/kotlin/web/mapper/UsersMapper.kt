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

    @Select("SELECT * FROM users WHERE username = #{username} or email = #{username} " )
    fun getUserByusernameoremail(@Param("username") username: String): List<Users>

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
            "allowcheck = #{user.Allowcheck} ,allow_up_txt = #{user.AllowUpTxt} ,allow_img = #{user.AllowImg} , comment = #{user.comment}  ,allow_cache = #{user.AllowCache} ,source  = #{user.source} " +
            "WHERE id = #{user.id}</script>")
    fun updateinfo(@Param("user") user: Users):Int


    @Update("UPDATE users set tssmd5 = #{tssmd5}   WHERE id = #{id}")
    fun updatettsmd5(@Param("id") id: String,@Param("tssmd5") tssmd5: String): Int

    @Update("UPDATE users set replacemd5 = #{replacemd5}   WHERE id = #{id}")
    fun updatereplacemd5(@Param("id") id: String,@Param("replacemd5") replacemd5: String): Int

    @Update("UPDATE users set bookmd5 = #{bookmd5}   WHERE id = #{id}")
    fun updatebookmd5(@Param("id") id: String,@Param("bookmd5") bookmd5: String): Int

    @Update("UPDATE users set sourcemd5 = #{sourcemd5}   WHERE id = #{id} ")
    fun updatesourcemd5(@Param("id") id: String,@Param("sourcemd5") sourcemd5: String): Int

    @Update("UPDATE users set rssmd5 = #{rssmd5}   WHERE id = #{id} ")
    fun updaterssmd5(@Param("id") id: String,@Param("rssmd5") rssmd5: String): Int

    @Update("UPDATE users set groundmd5 = #{groundmd5}   WHERE id = #{id} ")
    fun updategroundmd5(@Param("id") id: String,@Param("groundmd5") groundmd5: String): Int

    @Update("UPDATE users set sourcemd5 = #{sourcemd5}   WHERE source != 2")
    fun updatesourcemd52(@Param("sourcemd5") sourcemd5: String): Int

    @Update("UPDATE users set rssmd5 = #{rssmd5}   WHERE   source != 2 ")
    fun updaterssmd52(@Param("rssmd5") rssmd5: String): Int

    @Select("SELECT * FROM users WHERE   source != 2 ")
    fun getSourceUser(): List<Users>

    @Delete("DROP TABLE users")
    fun Drop(): Int


    @Update("UPDATE users set source = #{source}   WHERE  id = #{id} ")
    fun updatesource(@Param("id") id: String, @Param("source") source: Int): Int


    @Update("UPDATE users set allow_up_txt = #{allow_up_txt}   WHERE  id = #{id} ")
    fun updateallow_up_txt(@Param("id") id: String, @Param("allow_up_txt") allow_up_txt: Boolean): Int


    @Update("UPDATE users set allow_img = #{allow_img}   WHERE  id = #{id} ")
    fun updateallow_img(@Param("id") id: String, @Param("allow_img") allow_img: Boolean): Int


    @Update("UPDATE users set allowcheck = #{allowcheck}   WHERE  id = #{id} ")
    fun updateallowcheck(@Param("id") id: String, @Param("allowcheck") allowcheck: Boolean): Int
}