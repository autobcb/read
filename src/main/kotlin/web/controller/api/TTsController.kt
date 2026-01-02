package web.controller.api


import book.util.FileUtils
import book.util.GSON
import book.util.MD5Utils
import book.util.fromJsonArray
import book.webBook.WBook
import kotlinx.coroutines.runBlocking
import org.noear.solon.annotation.Body
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.core.handle.Context
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.Cache
import org.noear.solon.data.annotation.Tran
import org.noear.solon.data.cache.CacheService
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.mapper.HttpTTSMapper
import web.model.HttpTts
import web.model.Users
import web.notification.TTs
import web.response.*
import web.util.hash.Md5
import java.io.File
import java.nio.file.Paths


@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class TTsController : BaseController() {

    @Inject
    lateinit var httpTTSMapper: HttpTTSMapper

    @Inject(value = "\${default.tts:}", autoRefreshed=true)
    var tts:String=""

    @Inject
    lateinit var cacheService: CacheService


    @Mapping("/getallttsPage")
    fun getallttsPage(accessToken:String?) = run{
        val user = getuserbytocken(accessToken)
        if (user.tssmd5.isNullOrBlank()){
            user.tssmd5=Md5(System.currentTimeMillis().toString())
            usersMapper.updatettsmd5(user.id!!, user.tssmd5!!)
        }
        var list:MutableList<HttpTts> = mutableListOf()
        var page = 1
        httpTTSMapper.getalltts(user.id!!).forEach{
            var loginUi=it.loginUi
            if(!loginUi.isNullOrEmpty()){
                kotlin.runCatching {
                    val r=GSON.fromJsonArray<Any>(loginUi).getOrNull()
                    loginUi= GSON.toJson(r)
                }
            }
            it.loginUi=loginUi
            if(list.size >= 50){
                cacheService.store("getallttsNew:${accessToken}${user.tssmd5}${page}",JsonResponse(true).Data(list),cachetime)
                page++
                list=mutableListOf()
            }
            list.add(it)
        }
        cacheService.store("getallttsNew:${accessToken}${user.tssmd5}${page}",JsonResponse(true).Data(list),cachetime)
        JsonResponse(true).Data(mapOf("page" to page, "md5" to user.tssmd5))
    }

    @Cache(key = "getallttsNew:\${accessToken}\${md5}\${page}",  seconds = 60)
    @Mapping("/getallttsNew")
    open fun getBookshelf(accessToken: String?,md5: String?,page: String) = run {
        JsonResponse(false)
    }

    @Mapping("/getalltts")
    fun getalltts(accessToken:String?) = run{
        val user = getuserbytocken(accessToken)
        val list:MutableList<HttpTts> = mutableListOf()
        httpTTSMapper.getalltts(user.id!!).forEach{
            var loginUi=it.loginUi
            if(!loginUi.isNullOrEmpty()){
                kotlin.runCatching {
                    val r=GSON.fromJsonArray<Any>(loginUi).getOrNull()
                    loginUi= GSON.toJson(r)
                }
            }
            it.loginUi=loginUi
            list.add(it)
        }
        JsonResponse(true).Data(list)
    }

    @Mapping("/getdefaulttts")
    fun getdefaulttts(accessToken:String?) = run{
        getuserbytocken(accessToken)
        JsonResponse(true).Data(tts)
    }


    @Tran
    @Mapping("/addtts")
    open fun addtts(accessToken:String?, @Body tts: HttpTts)=run{
        val user = getuserbytocken(accessToken)
        if(tts.name.isBlank() || tts.url.isBlank()) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        if(tts.id.isNullOrBlank()){
            httpTTSMapper.getttsbyname(user.id!!,tts.name).also {
                if(it.isNotEmpty()){
                    throw DataThrowable().data(JsonResponse(false, NAME_ERROR))
                }
            }
            tts.create(user.id!!,tts.name)
            httpTTSMapper.insert(tts)
        }else{
            httpTTSMapper.getttsbyname(user.id!!,tts.name).forEach{
                if(it.id != tts.id){
                    throw DataThrowable().data(JsonResponse(false, NAME_ERROR))
                }
            }
            tts.create(user.id!!,tts.name)
            httpTTSMapper.deleteById(tts.id)
            httpTTSMapper.insert(tts)
        }
        TTs.sendNotification(user)
        JsonResponse(true)
    }

    @Mapping("/deltts")
    fun deltts(accessToken:String?,id: String?) = run{
        val user = getuserbytocken(accessToken)
        val tts= httpTTSMapper.gettts(id?:throw DataThrowable().data(JsonResponse(false, NOT_BANK)) ,user.id!!) ?:
        throw DataThrowable().data(JsonResponse(false, NOT_IS))
        httpTTSMapper.deleteById(tts.id)
        TTs.sendNotification(user)
        JsonResponse(true)
    }

    @Mapping("/delttss")
    fun delttss(accessToken:String?,@Body ids: List<String>?) = run{
        val user = getuserbytocken(accessToken)
        ids?.forEach {id->
            if (id.isNotBlank()){
                httpTTSMapper.gettts(id,user.id!!)?.let {
                    httpTTSMapper.deleteById(id)
                }
            }
        }
        TTs.sendNotification(user)
        JsonResponse(true)
    }


    @Mapping("/savettss")
    fun savettss(accessToken:String?, @Body content:String)=run{
        val user = getuserbytocken(accessToken)
        var insert = 0
        var update = 0
        val ttss= GSON.fromJsonArray<HttpTts>(content).getOrNull()
        ttss?.forEach {
           runCatching {
               addorupdate(it,user).let {  (ins,ups)->
                   insert += ins
                   update += ups
               }
           }
        }
        TTs.sendNotification(user)
        JsonResponse(true,"新增${insert}条引擎，更新${update}条引擎")
    }


    @Mapping("/tts")
    fun tts(ctx: Context, accessToken:String?, id: String?, speakText:String?, speechRate:Double?)= runBlocking{
        val user = getuserbytocken(accessToken)
        if(id.isNullOrBlank() || speakText.isNullOrBlank() ) throw Exception(NOT_BANK)
        var rate=speechRate?:100.0
        if (rate < 50) rate=50.0
        if(rate > 300) rate=300.0
        val tts= (httpTTSMapper.selectById(id)?:throw Exception(NOT_BANK)).totts()
        tts.userid=user.id
        tts.usertocken=accessToken
        if(tts.contentType.isNullOrBlank()){
            ctx.contentType("audio/mpeg")
        }else{
            ctx.contentType(tts.contentType)
        }
        //ctx.headerSet("Content-Disposition","attachment;filename=tts.mp3");
        WBook.getSpeakStream(tts,speakText,rate.toInt()).use { i->
            val b = ByteArray(4096)
            var len: Int
            while ((i.read(b).also { len = it }) != -1) {
                ctx.outputStream().write(b, 0, len)
            }
        }
    }


    @Mapping("/getttsLoginInfo")
    open fun getLoginInfo(accessToken: String?, id: String?) = run {
        val user = getuserbytocken(accessToken)
        val tts=(httpTTSMapper.selectById(id)?:throw DataThrowable().data(JsonResponse(false, NOT_IS) )).totts()
        tts.userid = user.id
        tts.usertocken = accessToken
        var info = tts.getLoginInfo()
        if (info.isNullOrBlank()) {
            info = "{}"
        }
        JsonResponse(true).Data(info)
    }


    @Mapping("/putttsLoginInfo")
    open fun putLoginInfo(accessToken: String?, id: String?, info: String?) = run {
        val user = getuserbytocken(accessToken)
        val tts=(httpTTSMapper.selectById(id)?:throw DataThrowable().data(JsonResponse(false, NOT_IS) )).totts()
        tts.userid = user.id
        tts.usertocken = accessToken
        tts.putLoginInfo(info ?: "{}")
        kotlin.runCatching { tts.login() }
        JsonResponse(true)
    }

    @Mapping("/ttsaction")
    open fun action(accessToken: String?, id: String?, action: String?) = runBlocking {
        val user = getuserbytocken(accessToken)
        if(action == null) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        val tts=(httpTTSMapper.selectById(id)?:throw DataThrowable().data(JsonResponse(false, NOT_IS) )).totts()
        tts.userid = user.id
        tts.usertocken = accessToken
        kotlin.runCatching {
            tts.runaction(action)
        }.onFailure { e ->
            logger.info("$action JavaScript error", e)
        }
        JsonResponse(true)
    }

    @Mapping("/upjson")
    fun upjson(accessToken:String?, @Body content:String)=run{
        getuserbytocken(accessToken)
        val jsonFile = "${MD5Utils.md5Encode(content)}.json"
        val relativeCoverUrl = Paths.get("assets", "", "json", jsonFile).toString()
        val  url= "/$relativeCoverUrl"
        val jsonUrl = Paths.get("", "storage", relativeCoverUrl).toString()
        val file= File(jsonUrl)
        if (file.exists()) {
            file.delete()
        }
        FileUtils.writeText(jsonUrl,content)
        JsonResponse(true,url)
    }

    private fun addorupdate(tts: HttpTts, user: Users) = run{
        var insert = 0
        var update = 0
        if(tts.name.isEmpty()){
            return  Pair(insert, update)
        }
        httpTTSMapper.getttsbyname(user.id!!,tts.name).let {
            if (it.isNotEmpty()){
                val r=it[0]
                tts.id=r.id
                tts.userid=r.userid
                tts.name = r.name
                update+=httpTTSMapper.updateById(tts)
            }else{
                tts.create(user.id!!,tts.name)
                insert+= httpTTSMapper.insert(tts)
            }
        }
        Pair(insert, update)
    }
}