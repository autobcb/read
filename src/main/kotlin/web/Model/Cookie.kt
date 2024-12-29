package web.Model

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.fasterxml.jackson.annotation.JsonFormat
import org.dromara.autotable.annotation.ColumnType
import org.dromara.mpe.autotable.annotation.ColumnId
import org.dromara.mpe.autotable.annotation.Table
import web.Util.passsign
import java.time.LocalDateTime
import java.util.*

@Table(dsName = "cookie")
class Cookie {
    @ColumnId
    var host=""
    @ColumnType(value = "MEDIUMTEXT")
    var value=""

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT,value = "createtime")
    var createtime: LocalDateTime? = null

    fun create():Cookie{
        this.createtime = LocalDateTime.now()
        return this
    }

}