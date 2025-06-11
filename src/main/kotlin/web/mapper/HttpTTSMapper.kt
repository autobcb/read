package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import web.model.HttpTts

interface HttpTTSMapper: BaseMapper<HttpTts> {

    @Select("SELECT * FROM http_tts WHERE id = #{id} and userid = #{userid} LIMIT 1")
    fun gettts(@Param("id") id: String,@Param("userid") userid: String): HttpTts?


    @Select("SELECT * FROM http_tts  WHERE userid = #{userid} and name = #{name}")
    fun getttsbyname(@Param("userid") userid: String, @Param("name") name: String): List<HttpTts>

    @Select("SELECT * FROM http_tts  WHERE userid = #{userid} order by name ")
    fun getalltts(@Param("userid") userid: String): List<HttpTts>
}