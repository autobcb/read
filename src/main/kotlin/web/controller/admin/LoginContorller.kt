package web.controller.admin

import org.noear.solon.annotation.*
import org.noear.solon.core.handle.Context
import org.noear.solon.core.util.DataThrowable
import web.response.*
import web.util.admin.loginok
import web.util.admin.logout as logout2


@Controller
@Mapping("/admin")
class LoginContorller {

    @Inject(value = "\${admin.username}", autoRefreshed=true)
    lateinit var adminusername:String

    @Inject(value = "\${admin.password}", autoRefreshed=true)
    lateinit var adminpassword: String



    @Post
    @Mapping("/login")
    fun login(ctx: Context, username: String?, password: String?)=run {
        if (username.isNullOrBlank() || password.isNullOrBlank() )  {
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = NOT_BANK))
        }

        if(adminusername == username && adminpassword == password) {
            loginok(username,ctx)
            JsonResponse(true)
        }else{
            JsonResponse(isSuccess = false, errorMsg =PASS_ERROR )
        }
    }



    @Mapping("/logout")
    fun logout(ctx: Context){
        logout2(ctx)
        ctx.redirect("/admin/login")
    }
}