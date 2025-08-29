package web.controller


import org.noear.solon.annotation.*
import org.noear.solon.core.handle.Context
import org.noear.solon.core.handle.ModelAndView
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.Tran
import org.noear.solon.data.cache.CacheService
import org.noear.solon.web.cors.annotation.CrossOrigin
import org.slf4j.LoggerFactory
import web.mapper.CodeMapper
import web.mapper.UsersMapper
import web.model.Users
import web.response.*
import web.util.admin.getMailCode
import web.util.admin.passsign
import web.util.mail.Mail

@CrossOrigin(origins = "*")
@Controller
open class HomeController {
    private val logger = LoggerFactory.getLogger(HomeController::class.java)
    companion object{
        var needcode=true;
    }

    @get:Get
    @get:Mapping("/regester")
    val reghtml = ModelAndView("regester.html")


    @Inject
    lateinit var usersMapper: UsersMapper

    @Inject
    lateinit var codeMapper: CodeMapper

    @Inject
    lateinit var cacheService: CacheService



    @Inject(value = "\${user.allowuptxt:false}", autoRefreshed=true)
    var allowuptxt:Boolean=false

    @Inject(value = "\${user.allowcache:false}", autoRefreshed=true)
    var allowcache:Boolean=false

    @Inject(value = "\${user.allowimg:false}", autoRefreshed=true)
    var allowimg:Boolean=false


    @Inject(value = "\${user.source:0}", autoRefreshed=true)
    var source:Int =0



    @Tran
    @Post
    @Mapping("/regester")
    fun regester(username:String? , password:String? ,phone:String?,email:String?, code:String?) = run {
        if (username.isNullOrBlank() || password.isNullOrBlank()   || email.isNullOrBlank()  )  {
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        if (needcode){
            if (code.isNullOrBlank()){
                throw DataThrowable().data(JsonResponse(false,NOT_BANK))
            }
        }
        val user=Users().apply {
            this.username = username
            this.password = password
            this.code = code
            this.phone = phone
            this.email = email
        }
        val (checkok,msg)=user.Check()
        if (!checkok) {
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = msg))
        }
        var c =""
        if(needcode){
            c=cacheService.get(code,String::class.java)
            if(c != code){
                codeMapper.getCode(code!!).also {
                    if(it == null) throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = CODE_ERROR))
                }
            }
        }else{
            val c=cacheService.get("code_$email",String::class.java)
            if(c != code){
                throw DataThrowable().data(JsonResponse(false, CODE_CHECK_ERROR))
            }
        }

        if(usersMapper.getUserByusername(user.username?:"") != null) {
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = USER_IS))
        }

        if(usersMapper.getUserByemail(email).isNotEmpty()) {
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = EMAIL_IS))
        }
        if (needcode){
            if(c == code){
                user.code="开放注册"
            }
        }

        user.source=source
        user.AllowCache=allowcache
        user.AllowUpTxt=allowuptxt
        user.AllowImg=allowimg

        if(user.source != 0 && user.source !=1 && user.source !=2){
            user.source=0
        }
        if(usersMapper.insert(user.create()) == 0){
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = ADD_ERROR))
        }
        if (needcode){
            if(c != code && codeMapper.deleteById(code) == 0){
                throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = ADD_ERROR))
            }
        }
        JsonResponse(true)
    }

    @Mapping("/needcode")
    fun needcode()=run {
        JsonResponse(needcode)
    }

    @Mapping("/sendResetCode")
    fun sendResetCode( username:String? ,email: String?)=run {
        if (email.isNullOrBlank()   )  {
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = EMAIL_ERROR))
        }
        var ct=0
        runCatching {
            ct=cacheService.get("codetime_$email",Int::class.java)
        }
        if(ct > 3){
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = EMAIL_CONT_ERROR))
        }
        if(needcode){
            if (username.isNullOrBlank() )  {
                throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = EMAIL_ERROR))
            }
            val user=usersMapper.getUserByusername(username)?:throw DataThrowable().data(JsonResponse(false,USER_NOT))
            if(user.email != email){
                throw DataThrowable().data(JsonResponse(false,EMAIL_CHECK_ERROR))
            }
        }

        val c= getMailCode()
        cacheService.store("code_$email",c,60*10)
        logger.info("$email has been sent to $c")
        runCatching {
            Mail.sendCode(c,email)
        }.onFailure {
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = it.message?:"邮件发送失败"))
        }
        cacheService.store("codetime_$email",ct+1,60*60*24)
        JsonResponse(true)
    }

    @Mapping("/resetPassword")
    fun  resetPassword(username:String? , password:String? , code:String? ,email:String?)=run {
        if(username.isNullOrBlank() || password.isNullOrBlank() || email.isNullOrBlank() || code.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        if(password.length <6 || password.length > 15 ){
            throw DataThrowable().data(JsonResponse(false,PASS_VAIL_ERROR))
        }
        val user=usersMapper.getUserByusername(username)?:throw DataThrowable().data(JsonResponse(false,USER_NOT))
        if(user.email != email){
            throw DataThrowable().data(JsonResponse(false,EMAIL_CHECK_ERROR))
        }
        val c=cacheService.get("code_$email",String::class.java)
        if(c != code){
            throw DataThrowable().data(JsonResponse(false, CODE_CHECK_ERROR))
        }
        cacheService.remove("code_$email")
        usersMapper.changepass(user.id!!, passsign( password))

        JsonResponse(true)
    }


    @Mapping("/ua")
    fun ua(ctx: Context)=run {
        ctx.outputAsHtml( ctx.userAgent())
    }

}