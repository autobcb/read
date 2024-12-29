package web.Model

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import com.fasterxml.jackson.annotation.JsonFormat
import org.dromara.autotable.annotation.ColumnNotNull
import org.dromara.autotable.annotation.Index
import org.dromara.autotable.annotation.enums.IndexTypeEnum
import org.dromara.mpe.autotable.annotation.ColumnId
import org.dromara.mpe.autotable.annotation.Table
import com.google.gson.*
import web.Util.json.LocalDateTimeAdapter
import web.Util.passsign
import web.Util.validation.isEmail
import web.Util.validation.isPhoneNum


import java.time.LocalDateTime
import java.util.UUID


@Table(dsName = "users")
class Users {
    @ColumnId
    var id : String =""


    @Index(type = IndexTypeEnum.UNIQUE)
    @ColumnNotNull
    var username: String = ""

    @ColumnNotNull
    var password: String? = ""
        get(){
            if (field == null || field!!.isBlank()){
                return null
            }else{
                return field
            }
        }

    var email: String = ""

    var phone: String = ""

    var AllowUpTxt: Boolean = false

    var comment: String = ""

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE,value = "updatetime")
    var updatetime:LocalDateTime = LocalDateTime.now()

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT,value = "createtime")
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
        if(this.password != null){
            this.password = passsign(password!!)
        }
        return this
    }

    fun Check():Pair<Boolean,String>{
        if(this.username.isBlank() ){
            return Pair(false,"用户名不能为空")
        }
        if(this.id.isBlank() && this.password == null ){
            return Pair(false,"密码不能为空")
        }
        if(this.password != null  && (this.password!!.length <6 || this.password!!.length > 15) ){
            return Pair(false,"密码长度请在6位到15位之间")
        }
        if (this.email.isNotBlank() && !isEmail(this.email)){
            return Pair(false,"邮箱格式不正确")
        }
        if (this.phone.isNotBlank() && !isPhoneNum(this.phone)){
            return Pair(false,"手机号不正确")
        }
        return Pair(true,"")
    }


    override fun toString():String = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter()).create().toJson(this)
}