package web.model


import book.model.RssSource as rss
import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.google.gson.Gson
import org.dromara.autotable.annotation.AutoTable
import org.dromara.autotable.annotation.ColumnType
import org.dromara.autotable.annotation.Index
import org.dromara.autotable.annotation.PrimaryKey
import org.noear.snack.annotation.ONodeAttr
import web.util.hash.Md5
import java.time.LocalDateTime

@AutoTable(value = "user_rss_source")
class UserRssSource {
    @TableId
    @PrimaryKey
    var id : String? =null
    @Index
    var userid : String? =null

    @ColumnType(value = "LONGTEXT")
    var sourceUrl: String = ""
    // 名称
    var sourceName: String = ""
    // 图标
    @ColumnType(value = "LONGTEXT")
    var sourceIcon: String = ""

    var sourceorder: Int? = null
    // 分组
    var sourceGroup: String? = null
    // 注释
    @ColumnType(value = "LONGTEXT")
    var sourceComment: String? = null
    // 是否启用
    var enabled: Boolean? = null
        get() {
            if(field == null) {
                return false
            }
            return field
        }
    @ColumnType(value = "LONGTEXT")
    var json: String? = null


    @ONodeAttr(format = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT,value = "createtime")
    var createtime: LocalDateTime? = null

    fun jsontomodel(source: rss, userid:String) :UserRssSource {
        this.sourceName = source.sourceName
        this.sourceIcon = source.sourceIcon
        this.sourceGroup = source.sourceGroup
        this.sourceUrl = source.sourceUrl
        this.sourceComment = source.sourceComment
        this.enabled = true
        this.createtime = LocalDateTime.now()
        this.json= Gson().toJson(source)
        this.userid = userid
        this.id= Md5(userid+source.sourceUrl)
        return this
    }

    fun toBaseSource(): BaseRssSource {
        return BaseRssSource(
            sourceUrl = this.sourceUrl,
            sourceName = this.sourceName,
            sourceIcon = this.sourceIcon,
            sourceComment = this.sourceComment,
            sourceGroup = this.sourceGroup,
            sourceorder =  this.sourceorder,
            createtime = this.createtime,
            enabled = this.enabled,
            json = this.json?:"{}"
        )
    }
}