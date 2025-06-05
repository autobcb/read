package web.controller.admin

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.*
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.Tran
import web.mapper.CodeMapper
import web.model.Code
import web.response.*
import web.util.admin.getcodes
import web.util.page.PageByAjax



@Controller
@Mapping("/admin")
open class CodeController {

    @Db("db")
    @Inject
    lateinit var codeMapper: CodeMapper

    @Mapping("/seachcode")
    fun seachcode(where:String?, order:String?, @Param(defaultValue = "1") page:Int, @Param(defaultValue = "20") limit:Int) = run  {
        val queryWrapper: QueryWrapper<Code> = QueryWrapper()
        if(!where.isNullOrBlank()){
            queryWrapper.like("code",where)
        }
        PageByAjax(codeMapper,queryWrapper,page,limit,order)
    }


    @Post
    @Mapping("/addcode")
    fun addcode( @Param(defaultValue = "1") num:Int) = run{
        if(num <= 0){
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = NUM_ERROR))
        }
        /*var date=  Date().time
        for(i in 1..num){
            val randoms = (100000..999999).random()
            var code=Code().create("$date$randoms")
            codeMapper.insert(code)
        }*/
        val codes=getcodes(num)
        for(c in codes){
            val code=Code().create(c)
            codeMapper.insert(code)
        }
        JsonResponse(true)
    }

    @Tran
    @Mapping("/delcode")
    fun delcode(code: String?) = run{
        if (code.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = NOT_BANK))
        }
        val ncode= codeMapper.getCode(code) ?: throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = NOT_IS))
        codeMapper.deleteById(ncode)
        JsonResponse(true)
    }

    @Mapping("/delcodes")
    fun delcodes(@Body ids:List<String>?) = run{
        ids?.forEach { id->
            if (id.isNotBlank()){
                val code=codeMapper.getCode(id)
                if (code != null){
                    codeMapper.deleteById(code)
                }
            }
        }
        JsonResponse(true)
    }
}