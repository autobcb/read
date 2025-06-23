package web.model

import com.baomidou.mybatisplus.annotation.TableId
import org.dromara.autotable.annotation.AutoTable
import org.dromara.autotable.annotation.ColumnType
import org.dromara.autotable.annotation.PrimaryKey
import web.util.hash.Md5

@AutoTable(value = "item")
class Item {
    @TableId
    @PrimaryKey
    var id : String? =null

    var userid : String? =null

    var name : String? =null

    @ColumnType(value = "LONGTEXT")
    var value : String? =null

    fun create(userid:String,name: String):Item{
        this.userid = userid
        this.name = name
        this.id = Md5("${userid}_${name}")
        return this
    }
}