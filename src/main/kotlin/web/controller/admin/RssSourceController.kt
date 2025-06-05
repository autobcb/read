package web.controller.admin


import web.model.RssSource
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import org.noear.solon.annotation.*
import org.noear.solon.core.handle.UploadedFile
import org.noear.solon.core.util.DataThrowable
import web.response.*
import web.service.RssSourceService
import web.util.hash.Md5
import web.util.page.PageByAjax
import java.io.EOFException



@Controller
@Mapping("/admin")
class RssSourceController {


    @Inject
    lateinit var rssSourceService: RssSourceService

    @Get
    @Mapping("/seachrssSource")
    fun seachrssSource(where:String?, order:String?, @Param(defaultValue = "1") page:Int, @Param(defaultValue = "20")  limit:Int) = run{
        val queryWrapper: QueryWrapper<RssSource> = QueryWrapper()
        if(!where.isNullOrBlank()){
            queryWrapper.like("source_url",where).or().like("source_name",where).or().like("source_group",where)
        }
        PageByAjax(rssSourceService.rssSourceMapper,queryWrapper,page,limit,order)
    }

    @Mapping("/delRssSource")
    fun delRssSource(id: String?) = run{
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val rss= rssSourceService.getRssSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        rssSourceService.rssSourceMapper.deleteById(rss.id)
        rssSourceService.cleancache()
        JsonResponse(true)
    }

    @Mapping("/delRssSources")
    fun delRssSources(@Body ids: List<String>?) = run{
        ids?.forEach {id->
            if (id.isNotBlank()){
                rssSourceService.rssSourceMapper.deleteById(Md5(id))
            }
        }
        rssSourceService.cleancache()
        JsonResponse(true)
    }

    @Mapping("/stopRssSource")
    fun stopRssSource(id: String? ,st: String?)= run{
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        rssSourceService.getRssSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        when(st){
            "0"->{
                rssSourceService.changeEnabled(id,false)
            }
            "1"->{
                rssSourceService.changeEnabled(id,true)
            }
            else -> throw DataThrowable().data(JsonResponse(false, USE_ERROE))
        }
        JsonResponse(true)
    }

    @Mapping("/topRssSource")
    fun topRssSource( id: String?)= run{
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val rss= rssSourceService.getRssSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        val sources = rssSourceService.getallSourcelist()
        var order=1
        for( it in sources!!){
            if(it.sourceUrl == rss.sourceUrl){
                rssSourceService.changeorder(it.sourceUrl, 0)
            }else{
                rssSourceService.changeorder(it.sourceUrl, order)
                order++
            }
        }
        JsonResponse(true)
    }

    @Mapping("/uploadRssSource")
    fun uploadRssSource(file: UploadedFile) = run{
        val content = String(file.contentAsBytes)
        var insert = 0
        var update = 0
        try {
            if (content.trim().startsWith("[")){
                //数组
                val sources= book.model.RssSource.fromJsonArray(content)
                sources.forEach {
                    //if(it.bookSourceUrl.isNotBlank() && it.bookSourceType != 1){
                    addorupdate(it).let {  (ins,ups)->
                        insert += ins
                        update += ups
                    }
                    //}
                }
            }else{
                //单独一个
                val source = book.model.RssSource.fromJson(content)
                if (source.sourceUrl.isBlank()){
                    throw DataThrowable().data(JsonResponse(false, SOURCE_URL_BANK))
                }
                // if(bookSource.bookSourceType == 1){
                //    throw DataThrowable().data(JsonResponse(false, SOURCE_TYPE_ERROR))
                // }
                addorupdate(source).let {  (ins,ups)->
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

    private fun addorupdate(rsssource: book.model.RssSource) = run{
        var insert = 0
        var update = 0
        val source=RssSource().jsontomodel(rsssource)
        rssSourceService.getRssSource(source.sourceUrl).let {
            if (it != null){
                source.enabled=it.enabled
                if(it.createtime != null){
                    source.createtime=it.createtime
                }
                source.sourceorder=it.sourceorder
                update += rssSourceService.rssSourceMapper.updateById(source)
            }else{
                //source.enabled=true
                source.sourceorder=9999
                insert += rssSourceService.rssSourceMapper.insert(source)
            }
        }
        rssSourceService.cleancache()
        Pair(insert, update)
    }
}