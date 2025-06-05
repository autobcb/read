package web.interceptor

import org.noear.solon.core.handle.Handler
import org.noear.solon.annotation.Component
import org.noear.solon.core.handle.Context
import org.noear.solon.core.route.PathRule
import org.noear.solon.core.route.RouterInterceptor
import org.noear.solon.core.route.RouterInterceptorChain
import web.util.admin.islogin


@Component
class AdminAuthInterceptorImpl: RouterInterceptor {
    override fun pathPatterns(): PathRule {
        return PathRule().include("/admin").include("/admin/**").exclude("/admin/login")
    }

    @Throws(Throwable::class)
    override fun doIntercept(ctx: Context, mainHandler: Handler?, chain: RouterInterceptorChain) {
        if (islogin(ctx)){
            chain.doIntercept(ctx, mainHandler);
        }else{
            ctx.redirect("/admin/login")
        }
    }
}