package web.model

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import org.dromara.autotable.annotation.AutoTable
import org.dromara.autotable.annotation.Index
import org.dromara.autotable.annotation.PrimaryKey
import org.noear.snack.annotation.ONodeAttr
import java.time.LocalDateTime
import java.util.UUID

@AutoTable(value = "bookmark")
class Bookmark {
    @TableId
    @PrimaryKey
    var id : String? =null

    @Index
    var userid : String? =null

    var boolurl : String? =null

    var cname : String? =null

    var cindex : Int? =null

    var cpos: Double? =null



    @ONodeAttr(format = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT,value = "createtime")
    var createtime: LocalDateTime? = null

    fun create(userid:String,book: Booklist):Bookmark{
        this.userid = userid
        this.boolurl = book.bookUrl
        this.id = UUID.randomUUID().toString()
        this.createtime = LocalDateTime.now()
        return this
    }
}