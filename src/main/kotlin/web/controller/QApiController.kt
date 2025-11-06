package web.controller

import kotlinx.coroutines.runBlocking
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.core.handle.Context
import org.noear.solon.core.handle.ModelAndView
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.cache.CacheService
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.controller.api.ApiWebSocket
import web.mapper.CodeMapper
import web.mapper.UsersMapper
import web.mapper.UsertockenMapper
import web.model.Code
import web.model.Users
import web.model.Usertocken
import web.response.ADD_ERROR
import web.response.EMAIL_IS
import web.response.JsonResponse
import web.response.NOT_BANK
import web.response.QJsonResponse
import web.response.USER_IS
import web.util.admin.getcodes
import web.util.hash.Md5
import web.util.hash.md5

@CrossOrigin(origins = "*")
@Controller
@Mapping("/qapi")
class QApiController {
    @Inject(value = "\${admin.code:}", autoRefreshed=true)
    var mycode:String=""

    @Inject
    lateinit var cacheService: CacheService

    @Inject
    lateinit var usersMapper: UsersMapper

    @Inject
    lateinit var usertockenMapper: UsertockenMapper

    @Inject
    lateinit var codeMapper: CodeMapper

    @Inject(value = "\${user.allowuptxt:false}", autoRefreshed=true)
    var allowuptxt:Boolean=false

    @Inject(value = "\${user.allowcache:false}", autoRefreshed=true)
    var allowcache:Boolean=false

    @Inject(value = "\${user.allowimg:false}", autoRefreshed=true)
    var allowimg:Boolean=false


    @Inject(value = "\${user.source:0}", autoRefreshed=true)
    var source:Int =0



    fun checkkey(key: String?){
        if (mycode.isBlank() ){
            throw DataThrowable().data(QJsonResponse(false).Msg("站点未配置密钥"))
        }
        if (key != mycode){
            throw DataThrowable().data(QJsonResponse(false).Msg("密钥错误"))
        }
    }


    @Mapping("/check")
    fun check(key:String? )=run {
        checkkey(key)
        QJsonResponse(true)
    }


    @Mapping("/getcode")
    fun getcode(key: String?,isalltime: Int?)=run {
        checkkey(key)
        val c= getcodes(1)[0]
        if (isalltime == 1){
            val code=Code().create(c)
            codeMapper.insert(code)
        }else{
            cacheService.store(c,c,60*10)
        }
        QJsonResponse(true).Data(c)
    }


    @Mapping("/login")
    fun login(md5: String?,username: String,time: Int)=run {
        if (mycode.isBlank() ){
            throw DataThrowable().data("站点未配置密钥")
        }
        val now=System.currentTimeMillis()/1000
        if (now < time || now-time > 300){
            throw DataThrowable().data("时间错误")
        }
        val signmd5="$username$time$mycode".md5()
        if (signmd5 != md5){
            throw DataThrowable().data("验证失败")
        }
        val user: Users?=usersMapper.getUserByusername(username)
        if (user == null){
            throw DataThrowable().data("未查询到用户")
        }
        val tocken=Usertocken().create()
        tocken.userid=user.id
        tocken.model="一键登陆"
        usertockenMapper.insert(tocken)
        ModelAndView("ylogin.html").also {
            it.put("id","\\\"${tocken.id}\\\"")
        }
    }

    @Mapping("/changeSourcePermission")
    fun changeSourcePermission(key: String?, username: String, permission: Int)=runBlocking {
        checkkey(key)
        val user: Users?=usersMapper.getUserByusername(username)
        if (user == null){
            throw DataThrowable().data(QJsonResponse(false).Msg("未查询到用户"))
        }
        when (permission) {
            0->{
                //不允许修改书源
                usersMapper.updatesource(user.id!!,0)
                web.notification.Source.sendNotification(user)
                web.notification.RssSource.sendNotification(user)
            }
            1 ->{
                //允许修改书源
                usersMapper.updatesource(user.id!!,1)
                web.notification.Source.sendNotification(user)
                web.notification.RssSource.sendNotification(user)
            }
            2->{
                //独立书源
                usersMapper.updatesource(user.id!!,2)
                web.notification.Source.sendNotification(user)
                web.notification.RssSource.sendNotification(user)
            }
            else -> {
                throw DataThrowable().data(QJsonResponse(false).Msg("permission错误或者后端版本太低"))
            }
        }
        ApiWebSocket.colseByuserid(user.id!!)
        QJsonResponse(true)
    }


    @Mapping("/changePermission")
    fun changePermission(key: String?,username: String,permission: Int,allow: Boolean)=runBlocking {
        checkkey(key)
        val user: Users?=usersMapper.getUserByusername(username)
        if (user == null){
            throw DataThrowable().data(QJsonResponse(false).Msg("未查询到用户"))
        }
        when (permission) {
            0->{
               //允许上传书本
                usersMapper.updateallow_up_txt(user.id!!,allow)
            }
            1->{
                //图片解密
                usersMapper.updateallow_img(user.id!!,allow)
            }
            2->{
                //书源检验
                usersMapper.updateallow_up_txt(user.id!!,allow)
            }
            else -> {
                throw DataThrowable().data(QJsonResponse(false).Msg("permission错误或者后端版本太低"))
            }
        }
        ApiWebSocket.colseByuserid(user.id!!)
        QJsonResponse(true)
    }


    @Mapping("/regester")
    fun regester(key: String?,username:String? , password:String? ,phone:String?,email:String?)=run {
        checkkey(key)
        if (username.isNullOrBlank() || password.isNullOrBlank()   || email.isNullOrBlank()  )  {
            throw DataThrowable().data(QJsonResponse(false).Msg("用户名密码或者邮箱不能为空"))
        }
        val user=Users().apply {
            this.username = username
            this.password = password
            this.phone = phone
            this.email = email
        }
        val (checkok,msg)=user.Check()
        if (!checkok) {
            throw DataThrowable().data(QJsonResponse(false).Msg(msg))
        }
        if(usersMapper.getUserByusername(user.username?:"") != null) {
            throw DataThrowable().data(QJsonResponse(false).Msg("用户名已存在"))
        }

        if(usersMapper.getUserByemail(email).isNotEmpty()) {
            throw DataThrowable().data(QJsonResponse(false).Msg("邮箱已存在"))
        }
        user.source=source
        user.AllowCache=allowcache
        user.AllowUpTxt=allowuptxt
        user.AllowImg=allowimg

        if(user.source != 0 && user.source !=1 && user.source !=2){
            user.source=0
        }
        if(usersMapper.insert(user.create()) == 0){
            throw DataThrowable().data(QJsonResponse(false).Msg("添加失败"))
        }
        QJsonResponse(true)
    }

    @Mapping("/searchByEmail")
    fun searchByEmail(key: String?,email:String?)=run {
        checkkey(key)
        if (email.isNullOrBlank()){
            throw DataThrowable().data(QJsonResponse(false).Msg("邮箱不能为空"))
        }
        val list=usersMapper.getUserByemail(email)
        val namelist=mutableListOf<String>()
        list.forEach {
            namelist.add(it.username?:"")
        }
        QJsonResponse(true).Data(namelist)
    }

}