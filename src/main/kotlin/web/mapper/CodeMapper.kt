package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import web.model.Code

interface CodeMapper: BaseMapper<Code> {

    @Select("SELECT * FROM code WHERE code = #{code} LIMIT 1")
    fun getCode(@Param("code") code: String): Code?
}