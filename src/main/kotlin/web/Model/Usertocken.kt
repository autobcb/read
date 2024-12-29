package web.Model

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.fasterxml.jackson.annotation.JsonFormat
import org.dromara.mpe.autotable.annotation.ColumnId
import org.dromara.mpe.autotable.annotation.Table
import java.time.LocalDateTime
import java.util.*

@Table(dsName = "usertocken")
class Usertocken {
    @ColumnId
    var id : String =""
    var model=""
    var userid : String =""                    //用户id

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT,value = "createtime")
    var createtime: LocalDateTime? = null

    fun create():Usertocken{
        this.id = UUID.randomUUID().toString()
        this.createtime = LocalDateTime.now()
        return this
    }
}