package web.model

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import org.dromara.autotable.annotation.AutoTable
import org.dromara.autotable.annotation.Index
import org.dromara.autotable.annotation.PrimaryKey
import org.noear.snack.annotation.ONodeAttr
import web.util.hash.Md5
import java.time.LocalDateTime

@AutoTable(value = "book_group")
class BookGroup {
    @TableId
    @PrimaryKey
    var id : String? =null

    @Index
    var userid : String? =null

    var bookgroup : String? = null

    var grouporder: Int? = null

    @ONodeAttr(format = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT,value = "createtime")
    var createtime: LocalDateTime? = null

    fun create(userid:String,bookgroup:String):BookGroup{
        this.bookgroup=bookgroup
        this.userid = userid
        this.createtime = LocalDateTime.now()
        this.id = Md5("$userid${this.bookgroup}")
        return this
    }

}