package web.controller.admin

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import org.noear.solon.annotation.Body
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Get
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.annotation.Param
import org.noear.solon.core.handle.UploadedFile
import org.noear.solon.core.util.DataThrowable
import web.model.BookSource
import web.response.*
import web.service.BookSourceService
import web.util.page.PageByAjax
import java.io.EOFException
import java.util.*
import book.model.BookSource as Booksource


@Controller
@Mapping("/admin")
class BookSourceController {

    @Inject
    lateinit var bookSourceService: BookSourceService

    @Get
    @Mapping("/seachbookSource")
    fun seachbookSource(where:String?, order:String?, @Param(defaultValue = "1") page:Int, @Param(defaultValue = "20")  limit:Int) = run{
        val queryWrapper: QueryWrapper<BookSource> = QueryWrapper()
        if(!where.isNullOrBlank()){
            queryWrapper.like("book_source_url",where).or().like("book_source_name",where).or().like("book_source_group",where)
        }
        PageByAjax(bookSourceService.booksSourceMapper,queryWrapper,page,limit,order)
    }

    @Mapping("/uploadSource")
    fun uploadSource(file: UploadedFile) = run{
        val content = String(file.contentAsBytes)
        var insert = 0
        var update = 0
        try {
            if (content.trim().startsWith("[")){
                //数组
                val bookSourcelist= Booksource.fromJsonArray(content).getOrNull()
                bookSourcelist?.forEach {
                   //if(it.bookSourceUrl.isNotBlank() && it.bookSourceType != 1){
                        addorupdate(it).let {  (ins,ups)->
                            insert += ins
                            update += ups
                        }
                    //}
                }
            }else{
                //单独一个
                val bookSource =Booksource.fromJson(content).getOrNull()?:Booksource()
                if (bookSource.bookSourceUrl.isBlank()){
                    throw DataThrowable().data(JsonResponse(false, SOURCE_URL_BANK))
                }
               // if(bookSource.bookSourceType == 1){
                //    throw DataThrowable().data(JsonResponse(false, SOURCE_TYPE_ERROR))
               // }
                addorupdate(bookSource).let {  (ins,ups)->
                    insert += ins
                    update += ups
                }
            }
        }catch (e: EOFException){
            throw DataThrowable().data(JsonResponse(false, JSON_ERROR))
        }catch (e:Exception){
            e.printStackTrace()
            throw DataThrowable().data(JsonResponse(false, DO_ERROR))
        }

        JsonResponse(true,"新增${insert}条书源，更新${update}条书源")
    }

    @Mapping("/delbookSource")
    fun delbookSource(id: String?) = run{
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        bookSourceService.getBookSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        bookSourceService.booksSourceMapper.deleteById(id)
        bookSourceService.cleancache()
        JsonResponse(true)
    }

    @Mapping("/delbookSources")
    fun delbookSources(@Body ids: List<String>?) = run{
        ids?.forEach {id->
            if (id.isNotBlank()){
                bookSourceService.booksSourceMapper.deleteById(id)
            }
        }
        bookSourceService.cleancache()
        JsonResponse(true)
    }

    @Mapping("/stopbookSource")
    fun stopbookSource(id: String? ,st: String?)= run{
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        bookSourceService.getBookSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        when(st){
            "0"->{
                bookSourceService.changeEnabled(id,false)
            }
            "1"->{
                bookSourceService.changeEnabled(id,true)
            }
            else -> throw DataThrowable().data(JsonResponse(false, USE_ERROE))
        }
        JsonResponse(true)
    }

    @Mapping("/stopbookSourceExplore")
    fun stopbookSourceExplore( id: String? ,st: String?)= run{
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
         bookSourceService.getBookSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        when(st){
            "0"->{
                bookSourceService.changeenabledExplore(id,false)
            }
            "1"->{
                bookSourceService.changeenabledExplore(id,true)
            }
            else -> throw DataThrowable().data(JsonResponse(false, USE_ERROE))
        }
        JsonResponse(true)
    }

    @Mapping("/topSource")
    fun topSource( id: String?)= run{
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val bookSource= bookSourceService.getBookSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        val sources = bookSourceService.getallBookSourcelist()
        var order=1
        for( it in sources!!){
            if(it.bookSourceUrl == bookSource.bookSourceUrl){
                bookSourceService.changeorder(it.bookSourceUrl?:"", 0)
            }else{
                bookSourceService.changeorder(it.bookSourceUrl?:"", order)
                order++
            }
        }
        JsonResponse(true)
    }




    private fun addorupdate(bookSource: Booksource) = run{
        var insert = 0
        var update = 0
        val source=BookSource().jsontomodel(bookSource)
        bookSourceService.getBookSource(bookSource.bookSourceUrl).let {
            if (it != null){
                source.enabled=it.enabled
                if(it.createtime != null){
                    source.createtime=it.createtime
                }
                source.sourceorder=it.sourceorder
                bookSource.lastUpdateTime= Date().time
                update += bookSourceService.booksSourceMapper.updateById(source)
            }else{
                source.enabled=true
                source.sourceorder=9999
                insert += bookSourceService.booksSourceMapper.insert(source)
            }
        }
        bookSourceService.cleancache()
        Pair(insert, update)
    }

}