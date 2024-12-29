package web.Model

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler
import com.google.gson.Gson
import io.legado.app.data.entities.rule.*
import org.dromara.autotable.annotation.ColumnType
import org.dromara.mpe.autotable.annotation.ColumnId
import org.dromara.mpe.autotable.annotation.Table
import web.Util.model.Booksource

@Table(dsName = "book_source")
class BookSource {
    @ColumnId
    var bookSourceUrl: String = ""           // 地址，包括 http/https
    var bookSourceName: String = ""           // 名称
    var bookSourceGroup: String? = null       // 分组
    var bookSourceType: Int = 0               // 类型，0 文本，1 音频
    @ColumnType(value = "LONGTEXT")
    var exploreUrl: String? = null                 // 发现url
    var enabled: Boolean = true            // 是否启用
    var enabledExplore: Boolean = true     //启用发现

    var bookSourceComment: String? = null           // 注释
    var lastUpdateTime: Long = 0             // 最后更新时间，用于排序
    var tag: String? = null
    var signkey:String = ""
    @ColumnType(value = "LONGTEXT")
    var json: String? = null                 // 发现url

    fun jsontomodel(bookSource: Booksource) :BookSource {
        this.bookSourceName = bookSource.bookSourceName
        this.bookSourceGroup = bookSource.bookSourceGroup
        this.bookSourceType = bookSource.bookSourceType
        this.bookSourceUrl = bookSource.bookSourceUrl
        this.exploreUrl = bookSource.exploreUrl
        this.enabled = bookSource.enabled
        this.enabledExplore = bookSource.enabledExplore
        this.bookSourceComment = bookSource.bookSourceComment
        this.lastUpdateTime = bookSource.lastUpdateTime
        this.tag = bookSource.tag
        this.json= Gson().toJson(bookSource)
        return this
    }
}