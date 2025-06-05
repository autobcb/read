package web.model

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import org.dromara.autotable.annotation.AutoTable
import org.dromara.autotable.annotation.ColumnType
import org.dromara.autotable.annotation.PrimaryKey
import org.noear.snack.annotation.ONodeAttr
import web.util.hash.Md5
import java.time.LocalDateTime
import java.util.UUID

@AutoTable(value = "book_cache")
class BookCache {

    @TableId
    @PrimaryKey
    var id : String? =null

    var userid : String? =null

    var bookid: String? = null

    @ColumnType(value = "MEDIUMTEXT")
    var bookUrl: String? = null

    var name: String? = null                   // 书籍名称(书源获取)
    var author: String? = null                 // 作者名称(书源获取)
    var origin: String? = null       // 书源URL(默认BookType.local)
    var originName: String? = null                //书源名称
    var totalChapterNum: Int? = null               // 书籍目录总数
    var num: Int? = null

    @ColumnType(value = "LONGTEXT")
    var cacheindex : String? = null

    @ONodeAttr(format = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT,value = "createtime")
    var createtime: LocalDateTime? = null

    fun create(userid:String,booklist: Booklist):BookCache{
        this.userid = userid
        this.bookid = booklist.id
        this.createtime = LocalDateTime.now()
        this.id = Md5("$userid${this.bookid}")
        this.bookUrl=booklist.bookUrl
        this.name = booklist.name
        this.author = booklist.author
        this.origin = booklist.origin
        this.originName = booklist.originName
        this.totalChapterNum = booklist.totalChapterNum
        return this
    }
}