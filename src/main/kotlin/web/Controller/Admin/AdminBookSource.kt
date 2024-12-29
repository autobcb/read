package web.Controller.Admin

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import web.Mapper.BookSourceMapper
import web.Model.BookSource
import web.Util.model.Booksource
import web.Response.JsonResponse
import web.Util.page.PageByAjax
import java.io.EOFException

@AdminApiController
class AdminBookSource {

    @Autowired
    lateinit var bookSourcemapper: BookSourceMapper

    @GetMapping("/seachbookSource")
    fun seachbookSource(@RequestParam("where") _where:String? , @RequestParam("order") _order:String? , @RequestParam("page") page:Int,@RequestParam("limit")  limit:Int) = run{
        var queryWrapper: QueryWrapper<BookSource> = QueryWrapper()
        if(_where != null && _where.isNotBlank()){
            queryWrapper.like("book_source_url",_where).or().like("book_source_name",_where).or().like("book_source_group",_where).or().like("tag",_where)
        }
        PageByAjax(bookSourcemapper,queryWrapper,page,limit,_order)
    }

    @Transactional(rollbackFor = [Exception::class])
    @PostMapping("/uploadSource")
    fun uploadSource(@RequestParam("file")  file: MultipartFile) = run{
        val content = String(file.bytes)
        var insert = 0
        var update = 0
        try {
            if (content.trim().startsWith("[")){
                //数组
                var bookSourcelist=Gson().fromJson(content, Array<Booksource>::class.java)
                bookSourcelist.forEach {
                    if(it.bookSourceUrl.isNotBlank() && it.bookSourceType == 0){
                        addorupdate(it).let {  (ins,ups)->
                            insert=insert+ins
                            update=update+ups
                        }
                    }
                }
            }else{
                //单独一个
                val bookSource = Gson().fromJson(content, Booksource::class.java)
                if (bookSource.bookSourceUrl.isBlank()){
                    return@run JsonResponse(false,"书源链接不能为空")
                }
                if(bookSource.bookSourceType != 0){
                    return@run JsonResponse(false,"暂不支持非文本连接")
                }
                addorupdate(bookSource).let {  (ins,ups)->
                    insert=insert+ins
                    update=update+ups
                }
            }
        }catch (e: EOFException){
            return@run JsonResponse(false,e.message?:"json格式化错误")
        }catch (e:Exception){
            return@run JsonResponse(false,e.message?:"导入失败")
        }

        JsonResponse(true,"新增${insert}条书源，更新${update}条书源")
    }

    @PostMapping("/delbookSource")
    fun delbookSource(@RequestParam("id") id: String?) = run{
        if (id == null || id.isBlank()){
            return JsonResponse(false,"ID?")
        }
        var bookSource=bookSourcemapper.getBookSource(id)
        if (bookSource == null){
            return JsonResponse(false,"书源不存在")
        }
        bookSourcemapper.deleteById(id)
        JsonResponse(true,"success")
    }

    @PostMapping("/delbookSources")
    fun delbookSources(@RequestBody ids: List<String>) = run{
        ids.forEach {id->
            if (id.isNotBlank()){
                bookSourcemapper.deleteById(id)
            }
        }
        JsonResponse(true,"success")
    }

    @PostMapping("/stopbookSource")
    fun stopbookSource(@RequestParam("id") id: String? ,@RequestParam("st") st: String?)= run{
        if (id == null || id.isBlank()){
            return JsonResponse(false,"ID?")
        }
        var bookSource=bookSourcemapper.getBookSource(id)
        if (bookSource == null){
            return JsonResponse(false,"书源不存在")
        }
        when(st){
            "0"->{
                bookSourcemapper.changeEnabled(id,false)
            }
            "1"->{
                bookSourcemapper.changeEnabled(id,true)
            }
            else -> return@run JsonResponse(false,"错误使用")
        }
        JsonResponse(true,"success")
    }

    @PostMapping("/stopbookSourceExplore")
    fun stopbookSourceExplore(@RequestParam("id") id: String? ,@RequestParam("st") st: String?)= run{
        if (id == null || id.isBlank()){
            return JsonResponse(false,"ID?")
        }
        var bookSource=bookSourcemapper.getBookSource(id)
        if (bookSource == null){
            return JsonResponse(false,"书源不存在")
        }
        when(st){
            "0"->{
                bookSourcemapper.changeenabledExplore(id,false)
            }
            "1"->{
                bookSourcemapper.changeenabledExplore(id,true)
            }
            else -> return@run JsonResponse(false,"错误使用")
        }
        JsonResponse(true,"success")
    }

    fun addorupdate(bookSource: Booksource) = run{
        var insert = 0
        var update = 0
        var source=BookSource().jsontomodel(bookSource)
        bookSourcemapper.getBookSource(bookSource.bookSourceUrl).let {
            if (it != null){
                source.enabled=it.enabled
                update=insert+bookSourcemapper.updateById(source)
            }else{
                bookSource.enabled=true
                insert=update+bookSourcemapper.insert(source)
            }
        }
        Pair(insert, update)
    }
}