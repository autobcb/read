package web.model

import book.model.HttpTTS
import com.baomidou.mybatisplus.annotation.TableId
import org.dromara.autotable.annotation.AutoTable
import org.dromara.autotable.annotation.ColumnType
import org.dromara.autotable.annotation.PrimaryKey
import web.util.hash.Md5

@AutoTable(value = "http_tts")
class HttpTts {
    @TableId
    @PrimaryKey
    var id : String? =null
    var userid : String? =null
    var name: String  =""
    @ColumnType(value = "LONGTEXT")
    var url: String  =""
    var contentType: String? = null

    var concurrentRate: String ? =null
    @ColumnType(value = "LONGTEXT")
    var loginUrl: String? = null
    @ColumnType(value = "LONGTEXT")
    var loginUi: String? = null
    @ColumnType(value = "LONGTEXT")
    var header: String? = null
    var enabledCookieJar: Boolean? = false
    @ColumnType(value = "LONGTEXT")
    var loginCheckJs: String? = null
    var lastUpdateTime: Long = System.currentTimeMillis()

    fun create(userid:String,name:String):HttpTts{
        this.userid = userid
        this.name = name
        this.id = Md5(userid+name)
        this.lastUpdateTime= System.currentTimeMillis()
        return this
    }

    fun  totts() :book.model.HttpTTS{
        var httpTTS: book.model.HttpTTS=book.model.HttpTTS(
            name = this.name,
            userid = this.userid,
            url = this.url,
            contentType = this.contentType,
            concurrentRate = this.concurrentRate,
            loginUrl = this.loginUrl,
            loginUi = this.loginUi,
            header = this.header,
            enabledCookieJar = this.enabledCookieJar,
            loginCheckJs = this.loginCheckJs
        )
        httpTTS.sqlid=id?:""
        return httpTTS
    }
}