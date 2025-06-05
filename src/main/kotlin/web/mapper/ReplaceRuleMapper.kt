package web.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update
import web.model.ReplaceRule

interface ReplaceRuleMapper: BaseMapper<ReplaceRule> {

    @Select("SELECT * FROM replace_rule WHERE id = #{id} and userid = #{userid} LIMIT 1")
    fun getrule(@Param("id") id: String,@Param("userid") userid: String): ReplaceRule?


    @Select("SELECT * FROM replace_rule  WHERE userid = #{userid} and name = #{name}")
    fun getrulebyname(@Param("userid") userid: String,@Param("name") name: String): List<ReplaceRule>

    @Select("SELECT * FROM replace_rule  WHERE is_enabled= true and userid = #{userid} and (scope like #{name} or scope = '' or scope is null) and exclude_scope not like #{name} ")
    fun getrulebybookname(@Param("userid") userid: String,@Param("name") name: String): List<ReplaceRule>

    @Select("SELECT * FROM replace_rule  WHERE userid = #{userid} order by ruleorder asc")
    fun getallrule(@Param("userid") userid: String): List<ReplaceRule>

    @Update("UPDATE replace_rule set ruleorder= #{ruleorder} WHERE id = #{id}")
    fun changeorder(@Param("id") id: String, @Param("ruleorder") ruleorder: Int):Int

    @Update("UPDATE replace_rule set is_enabled= #{enabled} WHERE id = #{id}")
    fun changeEnabled(@Param("id") id: String, @Param("enabled") enabled: Boolean):Int

    @Delete("Delete  FROM replace_rule WHERE userid = #{id}")
    fun delUserrule(@Param("id") id: String): Int
}