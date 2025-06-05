package web.filter


import org.noear.solon.annotation.Component
import org.noear.solon.core.exception.StatusException
import org.noear.solon.core.handle.Context
import org.noear.solon.core.handle.Filter
import org.noear.solon.core.handle.FilterChain
import org.noear.solon.core.handle.ModelAndView
import org.slf4j.Logger
import org.slf4j.LoggerFactory



@Component(index = 0)
class ErrorsFilter : Filter {

    val log:Logger = LoggerFactory.getLogger(ErrorsFilter::class.java)

    override fun doFilter(ctx: Context?, chain: FilterChain?) {
        runCatching {
            chain!!.doFilter(ctx)
        }.onFailure {
            when(it){
                is StatusException->{
                    if(it.code == 404){
                        ctx!!.status(it.code)
                        ctx.render(ModelAndView("errors/404.html"))
                    }else{
                        ctx!!.status(it.code)
                        throw it
                    }
                }
                else->{
                    ctx!!.status(500)
                    throw it
                }
            }
        }
    }
}