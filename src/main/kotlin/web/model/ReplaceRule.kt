package web.model


import com.baomidou.mybatisplus.annotation.TableId
import org.dromara.autotable.annotation.AutoTable
import org.dromara.autotable.annotation.ColumnType
import org.dromara.autotable.annotation.Index
import org.dromara.autotable.annotation.PrimaryKey
import web.util.hash.Md5

@AutoTable(value = "replace_rule")
class ReplaceRule {

    @TableId
    @PrimaryKey
    var id : String? =null

    @Index
    var userid : String? =null
    //名称
    var name: String = ""
    //分组
    var groupname: String? = null
    //替换内容
    @ColumnType(value = "LONGTEXT")
    var pattern: String = ""
    //替换为
    @ColumnType(value = "LONGTEXT")
    var replacement: String = ""
    //作用范围
    @ColumnType(value = "LONGTEXT")
    var scope: String? = null
    //作用于标题
    var scopeTitle: Boolean = false
    //作用于正文
    var scopeContent: Boolean = true
    //排除范围
    @ColumnType(value = "LONGTEXT")
    var excludeScope: String? = null
    //是否启用
    var isEnabled: Boolean = true
    //是否正则
    var isRegex: Boolean = true
    //超时时间
    var timeoutMillisecond: Long = 3000L
    //排序
    var ruleorder: Int = Int.MIN_VALUE


    fun getValidTimeoutMillisecond(): Long {
        if (timeoutMillisecond <= 0) {
            return 3000L
        }
        return timeoutMillisecond
    }

    fun create(userid:String,name:String):ReplaceRule{
        this.userid = userid
        this.name = name
        this.id = Md5(userid+name)
        return this
    }
}