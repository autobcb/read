package web.model

import book.util.MD5Utils
import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.google.gson.Gson
import org.dromara.autotable.annotation.AutoTable
import org.dromara.autotable.annotation.ColumnType
import org.dromara.autotable.annotation.PrimaryKey
import org.noear.snack.annotation.ONodeAttr
import web.util.hash.Md5
import java.time.LocalDateTime
import book.model.RssSource as rss

@AutoTable(value = "rss_source")
class RssSource {
    @TableId
    @PrimaryKey
    var id : String? =null

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

    fun jsontomodel(source: rss) :RssSource {
        this.sourceName = source.sourceName
        this.sourceIcon = source.sourceIcon
        this.sourceGroup = source.sourceGroup
        this.sourceUrl = source.sourceUrl
        this.sourceComment = source.sourceComment
        this.enabled =source.enabled
        this.createtime = LocalDateTime.now()
        this.json= Gson().toJson(source)
        this.id= MD5Utils.md5Encode(this.sourceUrl)
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