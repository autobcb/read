package web.model


import com.baomidou.mybatisplus.annotation.TableId
import org.dromara.autotable.annotation.AutoTable
import org.dromara.autotable.annotation.ColumnNotNull
import org.dromara.autotable.annotation.Index
import org.dromara.autotable.annotation.PrimaryKey
import org.dromara.autotable.annotation.TableIndex
import org.dromara.autotable.annotation.enums.IndexTypeEnum
import org.noear.snack.annotation.ONodeAttr
import web.response.EMAIL_ERROR
import web.response.NOT_BANK
import web.response.PASS_VAIL_ERROR
import web.response.PHONE_ERROR
import web.util.admin.passsign
import web.util.validation.isEmail
import web.util.validation.isPhoneNum
import java.time.LocalDateTime
import java.util.*



@AutoTable(value = "users")
@TableIndex(type = IndexTypeEnum.UNIQUE, fields = ["username"])
class Users {

    @TableId
    @PrimaryKey
    var id : String? =null



    @ColumnNotNull
    var username: String? = null

    @ColumnNotNull
    var  password: String? = null
        get(){
            return if (field == null || field!!.isBlank()){
                null
            }else{
                field
            }
        }
    @Index
    var email: String? = null

    var code: String? = null

    var source: Int = 0

    var phone: String? = null

    var AllowUpTxt: Boolean? = null

    var AllowCache: Boolean? = null

    var AllowImg: Boolean? = null

    var Allowcheck: Boolean? = null

    var bookmd5 :String? = null

    var sourcemd5 :String? = null

    var rssmd5  :String? = null

    var tssmd5 :String? = null

    var groundmd5 :String? = null

    var replacemd5 :String? = null

    var comment: String? = null

    @ONodeAttr(format = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //@TableField(fill = FieldFill.INSERT_UPDATE,value = "updatetime")
    var updatetime:LocalDateTime? = null

    @ONodeAttr(format = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //@TableField(fill = FieldFill.INSERT,value = "createtime")
    var createtime: LocalDateTime? = null

    fun create():Users{
        this.id = UUID.randomUUID().toString()
        this.createtime = LocalDateTime.now()
        this.updatetime = LocalDateTime.now()
        this.password = passsign(this.password!!)
        return this
    }

    fun update():Users{
        this.updatetime = LocalDateTime.now()
        if(this.password.isNullOrBlank()){
            this.password = null
        }else{
            this.password = passsign(password!!)
        }
        this.createtime=null
        return this
    }

    fun Check():Pair<Boolean,String>{
        if(this.username.isNullOrBlank() ){
            return Pair(false, NOT_BANK)
        }
        if(this.id.isNullOrBlank() && this.password == null ){
            return Pair(false,NOT_BANK)
        }
        if(this.password != null  && (this.password!!.length <6 || this.password!!.length > 15) ){
            return Pair(false,PASS_VAIL_ERROR)
        }
        if ((this.email?:"").isNotBlank() && !isEmail(this.email)){
            return Pair(false,EMAIL_ERROR)
        }
        if ((this.phone?:"").isNotBlank() && !isPhoneNum(this.phone)){
            return Pair(false,PHONE_ERROR)
        }
        return Pair(true,"")
    }

    //override fun toString():String = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter()).create().toJson(this)

}