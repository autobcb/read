package web.model

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import org.dromara.autotable.annotation.AutoTable
import org.dromara.autotable.annotation.ColumnType
import org.dromara.autotable.annotation.PrimaryKey
import org.noear.snack.annotation.ONodeAttr
import java.time.LocalDateTime
import java.util.*

@AutoTable(value = "user_cookie")
class UserCookie {
    @TableId
    @PrimaryKey
    var id : String? =null

    var userid : String? =null

    var host : String? = null

    @ColumnType(value = "MEDIUMTEXT")
    var value:String? = null

    @ONodeAttr(format = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT,value = "createtime")
    var createtime: LocalDateTime? = null

    fun create(userid:String):UserCookie{
        this.id = UUID.randomUUID().toString()
        this.userid = userid
        this.createtime = LocalDateTime.now()
        return this
    }

}