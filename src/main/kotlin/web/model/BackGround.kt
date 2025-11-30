package web.model

import com.baomidou.mybatisplus.annotation.TableId
import org.dromara.autotable.annotation.AutoTable
import org.dromara.autotable.annotation.Index
import org.dromara.autotable.annotation.PrimaryKey
import web.util.hash.Md5


@AutoTable(value = "back_ground")
class BackGround {
    @TableId
    @PrimaryKey
    var id : String? =null

    @Index
    var userid : String? =null

    var name : Long? =null

    var backgroundColor : String? =null

    var textColor : String? =null

    var backgroundimage : String? =null

    fun create(userid:String,name:Long):BackGround{
        this.userid = userid
        this.name = name
        this.id = Md5("${userid}_${name}")
        return this
    }
}