package web.model

import com.baomidou.mybatisplus.annotation.TableId
import org.dromara.autotable.annotation.AutoTable
import org.dromara.autotable.annotation.ColumnType
import org.dromara.autotable.annotation.Index
import org.dromara.autotable.annotation.PrimaryKey
import web.util.hash.Md5

@AutoTable(value = "sgread")
class Sgread {

    @TableId
    @PrimaryKey
    var id : String? =null

    @Index
    var userid : String? =null

    @ColumnType(value = "MEDIUMTEXT")
    var bookUrl: String? = null

    var t: Long=0;

    fun create(userid:String,bookUrl:String):Sgread{
        this.userid = userid
        this.bookUrl = bookUrl
        this.id = Md5(userid+bookUrl)
        this.t= System.currentTimeMillis()
        return this
    }


}