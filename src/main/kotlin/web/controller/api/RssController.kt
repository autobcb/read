package web.controller.api

import book.app.App
import book.model.RssArticle
import book.model.RssSource
import book.util.GSON
import book.util.MD5Utils
import book.util.fromJsonArray
import book.util.fromJsonObject
import book.util.isTrue
import book.webBook.rss.Rss
import book.webBook.sortUrls
import kotlinx.coroutines.runBlocking
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Body
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.core.handle.Context
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.Cache
import org.noear.solon.data.annotation.CacheRemove
import org.noear.solon.data.annotation.Tran
import org.noear.solon.data.cache.CacheService
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.model.BaseRssSource
import web.model.UserRssSource
import web.model.Users
import web.response.*
import web.service.RssSourceService
import web.service.UserRssSourceService
import web.util.hash.Md5
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.*

@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class RssController :BaseController() {


    @Inject(value = "\${user.maxsource:0}", autoRefreshed=true)
    var maxsource: Int= 0


    @Inject
    lateinit var rssSourceService: RssSourceService


    @Inject
    lateinit var userRssSourceService: UserRssSourceService

    @Inject
    lateinit var cacheService: CacheService

    @Inject(value = "\${user.timeout:0}", autoRefreshed=true)
    var timeout: Int= 0

    @Mapping("/getRssSourcess")
    fun  getRssSourcess(accessToken: String?) =run{
        val user = getuserbytocken(accessToken)
        val sources: MutableList<BaseRssSource> = mutableListOf()
        when (user.source) {
            0 -> {
                rssSourceService.getEnabledSourcelist()?.forEach{
                    sources.add(it.toBaseSource())
                }
            }
            1 -> {
                rssSourceService.getallSourcelist()?.forEach{
                    sources.add(it.toBaseSource())
                }
            }
            else -> {
                userRssSourceService.getallSourcelist(user.id!!).forEach{
                    sources.add(it.toBaseSource())
                }
            }
        }
        val list: MutableList<Map<String, Any?>> = mutableListOf()
        sources.forEach {
            var rssSource: RssSource? = null
            var loginUi:String?=null
            kotlin.runCatching {
                rssSource=RssSource.fromJson(it.json?:"")
                rssSource!!.userid=user.id
                rssSource!!.usertocken=accessToken
                loginUi=rssSource!!.loginUi
                if(!loginUi.isNullOrEmpty()){
                    kotlin.runCatching {
                        val r=GSON.fromJsonArray<Any>(loginUi).getOrNull()
                        loginUi= GSON.toJson(r)
                    }
                }
            }
            list.add(
                mapOf(
                    "variableComment" to rssSource?.variableComment,
                    "loginUrl" to rssSource?.loginUrl,
                    "loginUi" to loginUi,
                    "sourceUrl" to it.sourceUrl,
                    "sourceName" to it.sourceName,
                    "sourceIcon" to it.sourceIcon,
                    "sourceGroup" to it.sourceGroup,
                    "enabled" to it.enabled
                )
            )
        }
        JsonResponse(true).Data(mapOf("sources" to list, "can" to (user.source != 0) ))
    }

    @Mapping("/rssshouldOverrideUrlLoading")
    fun rssshouldOverrideUrlLoading(accessToken:String?,id: String?,url: String?) = runBlocking{
        val user=getsourceuser(accessToken)
        if (id.isNullOrBlank() || url.isNullOrBlank()) {
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val rss= if(user.source == 2){
            userRssSourceService.getRssSource(id,user.id!!)?.toBaseSource()
        }else{
            rssSourceService.getRssSource(id)?.toBaseSource()
        } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        val rssSource=RssSource.fromJson(rss.json?:"")
        rssSource.userid=user.id
        rssSource.usertocken=accessToken
        val js = rssSource.shouldOverrideUrlLoading
        if (!js.isNullOrBlank()) {
            val t = System.currentTimeMillis()
            val result = rssSource.shouldOverrideUrlLoadingdo(js,url)
            if (System.currentTimeMillis() - t > 300) {
                App.log("${rssSource.getTag()}: url跳转拦截js执行耗时过长",accessToken!!)
            }
            if (result.isTrue()) {
               return@runBlocking JsonResponse(false)
            }
        }
        JsonResponse(true)
    }

    @Mapping("/getRssSources")
    fun getRssSources(accessToken:String?,id: String?) = run{
        val user=getsourceuser(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val bookSource= if(user.source == 2){
            userRssSourceService.getRssSource(id,user.id!!)?.toBaseSource()
        }else{
            rssSourceService.getRssSource(id)?.toBaseSource()
        } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        JsonResponse(true).Data(mapOf(
            "json" to bookSource.json,
            "enabled" to bookSource.enabled,
            "sourceGroup" to bookSource.sourceGroup,
        ))
    }

    @Tran
    @Mapping("/editRssSources")
    open fun editRssSources(accessToken:String?, @Body content:EditMsg) = run{
        val user=getsourceuser(accessToken)
        lateinit var  source:RssSource
        runCatching {
            source= RssSource.fromJson(content.json?:"")
        }.onFailure {
            throw DataThrowable().data(JsonResponse(false, SOURCE_JSON_ERROR))
        }
        if(source.sourceUrl.isEmpty()) throw DataThrowable().data(JsonResponse(false, SOURCE_URL_ERROR))
        if(user.source == 2){
            val rsssource= UserRssSource().jsontomodel(source,user.id!!)
            if(maxsource > 0){
                val list= userRssSourceService.getallSourcelist(user.id)
                if(list.size > maxsource){
                    throw DataThrowable().data(JsonResponse(false, MAX_ERROR))
                }
            }
            rsssource.sourceorder=9999
            if(content.id  != null && content.id!!.isNotEmpty()){
                val bs=
                    userRssSourceService.getRssSource(content.id!!,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                rsssource.sourceorder=bs.sourceorder
                rsssource.createtime=bs.createtime
                userRssSourceService.userRssSourceMapper.deleteById(content.id)
            }else{
                val bs=userRssSourceService.getRssSource(rsssource.sourceUrl,user.id!!)
                if (bs != null){
                    throw DataThrowable().data(JsonResponse(false, SOURCE_IS))
                }
            }
            rsssource.enabled=source.enabled
            userRssSourceService.userRssSourceMapper.insert(rsssource)
            userRssSourceService.cleancache(user.id)
        }else{
            val rsssource= web.model.RssSource().jsontomodel(source)
            rsssource.sourceorder=9999
            if(content.id  != null && content.id!!.isNotEmpty()){
                val bs=
                    rssSourceService.getRssSource(content.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                rsssource.sourceorder=bs.sourceorder
                rsssource.createtime=bs.createtime
                rssSourceService.rssSourceMapper.deleteById(bs.id)
            }else{
                val bs=rssSourceService.getRssSource(source.sourceUrl)
                if (bs != null){
                    throw DataThrowable().data(JsonResponse(false, SOURCE_IS))
                }
            }
            rsssource.enabled=source.enabled
            rssSourceService.rssSourceMapper.insert(rsssource)
            rssSourceService.cleancache()
        }

        JsonResponse(true)
    }


    @Mapping("/topRssSource")
    fun topRssSource( accessToken:String?, id: String?)= run{
        val user=getsourceuser(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        var order=1
        if(user.source == 2){
            val rsssource= userRssSourceService.getRssSource(id,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            val sources = userRssSourceService.getallSourcelist(user.id!!)
            if(maxsource > 0){
                if(sources.size > maxsource){
                    throw DataThrowable().data(JsonResponse(false, MAX_ERROR))
                }
            }
            for( it in sources){
                if(it.sourceUrl == rsssource.sourceUrl){
                    userRssSourceService.changeorder(it.id?:"",user.id, 0)
                }else{
                    userRssSourceService.changeorder(it.id?:"",user.id, order)
                    order++
                }
            }
        }else{
            val rsssource= rssSourceService.getRssSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            val sources = rssSourceService.getallSourcelist()
            for( it in sources!!){
                if(it.sourceUrl == rsssource.sourceUrl){
                    rssSourceService.changeorder(it.sourceUrl, 0)
                }else{
                    rssSourceService.changeorder(it.sourceUrl, order)
                    order++
                }
            }
        }
        JsonResponse(true)
    }

    @Mapping("/bottomallrssSource")
    fun bottomallrssSource( accessToken:String?,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
        if (ids == null || ids.isEmpty()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        var order=0
        if(user.source == 2){
            val sources = userRssSourceService.getallSourcelist(user.id!!)
            if(maxsource > 0){
                if(sources.size > maxsource){
                    throw DataThrowable().data(JsonResponse(false, MAX_ERROR))
                }
            }
            for( it in sources){
                if(!ids.contains(it.sourceUrl)){
                    userRssSourceService.changeorder(it.id?:"",user.id, order)
                    order++
                }
            }
            for(id in ids){
                for( it in sources){
                    if(it.sourceUrl == id){
                        userRssSourceService.changeorder(it.id?:"",user.id, order)
                        break;
                    }
                }
                order++
            }
        }else{
            val sources = rssSourceService.getallSourcelist()
            for( it in sources!!){
                if(!ids.contains(it.sourceUrl)){
                    rssSourceService.changeorder(it.sourceUrl, order)
                    order++
                }
            }
            for(id in ids){
                rssSourceService.changeorder(id, order)
                order++
            }
        }
        JsonResponse(true)
    }

    @Mapping("/topallrssSource")
    fun topallrssSource( accessToken:String?,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
        if (ids == null || ids.isEmpty()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        var order=ids.size
        if(user.source == 2){
            val sources = userRssSourceService.getallSourcelist(user.id!!)
            if(maxsource > 0){
                if(sources.size > maxsource){
                    throw DataThrowable().data(JsonResponse(false, MAX_ERROR))
                }
            }
            for( it in sources){
                if(!ids.contains(it.sourceUrl)){
                    userRssSourceService.changeorder(it.id?:"",user.id, order)
                    order++
                }
            }
            order = 0
            for(id in ids){
                for( it in sources){
                    if(it.sourceUrl == id){
                        userRssSourceService.changeorder(it.id?:"",user.id, order)
                        break;
                    }
                }
                order++
            }
        }else{
            val sources = rssSourceService.getallSourcelist()
            for( it in sources!!){
                if(!ids.contains(it.sourceUrl)){
                    rssSourceService.changeorder(it.sourceUrl, order)
                    order++
                }
            }
            order = 0
            for(id in ids){
                rssSourceService.changeorder(id, order)
                order++
            }
        }
        JsonResponse(true)
    }

    @Mapping("/bottomRssSource")
    fun bottomRssSource( accessToken:String?, id: String?)= run{
        val user=getsourceuser(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        var order=0
        if(user.source == 2){
            val rsssource= userRssSourceService.getRssSource(id,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            val sources = userRssSourceService.getallSourcelist(user.id!!)
            if(maxsource > 0){
                if(sources.size > maxsource){
                    throw DataThrowable().data(JsonResponse(false, MAX_ERROR))
                }
            }
            for( it in sources){
                if(it.sourceUrl == rsssource.sourceUrl){
                    userRssSourceService.changeorder(it.id?:"",user.id, sources.size-1)
                }else{
                    userRssSourceService.changeorder(it.id?:"",user.id, order)
                    order++
                }
            }
        }else{
            val rsssource= rssSourceService.getRssSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            val sources = rssSourceService.getallSourcelist()
            for( it in sources!!){
                if(it.sourceUrl == rsssource.sourceUrl){
                    rssSourceService.changeorder(it.sourceUrl, sources.size-1)
                }else{
                    rssSourceService.changeorder(it.sourceUrl, order)
                    order++
                }
            }
        }
        JsonResponse(true)
    }

    @Mapping("/editrsssourcegroup")
    fun editrsssourcegroup( accessToken:String?,st:String,group:String,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
        if (ids == null || ids.isEmpty()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        if(user.source == 2){
            for(id in ids){
                val bookSource= userRssSourceService.getRssSource(id,user.id!!)
                if (bookSource == null) continue
                val sp=bookSource.sourceGroup?.split(",")
                val groups=mutableListOf<String>()
                if(st == "0"){
                    groups.add(group)
                }
                sp?.forEach{
                    if(it != group){
                        groups.add(it)
                    }
                }
                bookSource.sourceGroup=groups.joinToString(",")
                if(bookSource.sourceGroup!!.endsWith(",")){
                    bookSource.sourceGroup=bookSource.sourceGroup!!.substring(0, bookSource.sourceGroup!!.length - 1)
                }
                userRssSourceService.changegroup(bookSource.id, user.id,bookSource.sourceGroup)
            }
        }else{
            for(id in ids){
                val bookSource= rssSourceService.getRssSource(id)
                if (bookSource == null) continue
                val sp=bookSource.sourceGroup?.split(",")
                val groups=mutableListOf<String>()
                if(st == "0"){
                    groups.add(group)
                }
                sp?.forEach{
                    if(it != group){
                        groups.add(it)
                    }
                }
                bookSource.sourceGroup=groups.joinToString(",")
                if(bookSource.sourceGroup!!.endsWith(",")){
                    bookSource.sourceGroup=bookSource.sourceGroup!!.substring(0, bookSource.sourceGroup!!.length - 1)
                }
                rssSourceService.changegroup(id,bookSource.sourceGroup)
            }
        }
        JsonResponse(true)
    }

    @Mapping("/delRssSource")
    fun delRssSource(accessToken:String?,id: String?) = run{
        val user=getsourceuser(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        if(user.source == 2){
            val rsssource= userRssSourceService.getRssSource(id,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            userRssSourceService.userRssSourceMapper.deleteById(rsssource.id?:"")
            userRssSourceService.cleancache(user.id)
        }else{
            val rsssource= rssSourceService.getRssSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            rssSourceService.rssSourceMapper.deleteById(rsssource.id?:"")
            rssSourceService.cleancache()
        }
        JsonResponse(true)
    }


    @Mapping("/stopRssSource")
    fun stopRssSource(accessToken:String?,id: String? ,st: String?)= run{
        val user=getsourceuser(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        if(user.source == 2){
            val rss= userRssSourceService.getRssSource(id,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            when(st){
                "0"->{
                    userRssSourceService.changeEnabled(rss.id!!,user.id,false)
                }
                "1"->{
                    userRssSourceService.changeEnabled(rss.id!!,user.id,true)
                }
                else -> throw DataThrowable().data(JsonResponse(false, USE_ERROE))
            }
        }else{
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
        }
        JsonResponse(true)
    }

    @Mapping("/startRssSources")
    fun startRssSources(accessToken:String?,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
        if(user.source == 2){
            ids?.forEach {
                if (it.isNotBlank()){
                    val rss=
                        userRssSourceService.getRssSource(it,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    userRssSourceService.changeEnabled(rss.id!!,user.id,true)
                }
            }
        }else{
            ids?.forEach {
                if (it.isNotBlank()){
                    rssSourceService.getRssSource(it) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    rssSourceService.changeEnabled(it,true)
                }
            }
        }
        JsonResponse(true)
    }

    @Mapping("/stopRssSources")
    fun stopRssSources(accessToken:String?,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
        if(user.source == 2){
            ids?.forEach {
                if (it.isNotBlank()){
                    val rss=
                        userRssSourceService.getRssSource(it,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    userRssSourceService.changeEnabled(rss.id!!,user.id,false)
                }
            }
        }else{
            ids?.forEach {
                if (it.isNotBlank()){
                    rssSourceService.getRssSource(it) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    rssSourceService.changeEnabled(it,false)
                }
            }
        }
        JsonResponse(true)
    }

    @Mapping("/delRssSources")
    fun delRssSources(accessToken:String?,@Body ids: List<String>?) = run{
        val user=getsourceuser(accessToken)
        ids?.forEach {id->
            if (id.isNotBlank()){
                if(user.source == 2){
                    userRssSourceService.delRssSource(id,user.id!!)
                }else{
                    rssSourceService.rssSourceMapper.deleteById(MD5Utils.md5Encode(id))
                    rssSourceService.cleancache()
                }
            }
        }
        JsonResponse(true)
    }

    @Mapping("/getRssSourcejson")
    fun getRssSourcejson(accessToken:String?,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
        var s="["
        ids?.forEach {
            if (it.isNotBlank()){
                val rss=if(user.source == 2){
                    userRssSourceService.getRssSource(it,user.id!!)?.toBaseSource()
                }else{
                    rssSourceService.getRssSource(it)?.toBaseSource()
                } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                s = if(s == "[" ){
                    "$s ${rss.json}"
                }else {
                    "$s , ${rss.json}"
                }
            }
        }
        s="$s ]"
        JsonResponse(true).Data(s)
    }

    @Tran
    @Mapping("/saveRssSources")
    fun saveRssSources(accessToken:String?, source:String, urls:String)=run{
        val user=getsourceuser(accessToken)
        var insert = 0
        var update = 0
        var list= listOf<String>()
        if(urls.isNotEmpty()){
            list= GSON.fromJsonArray<String>(urls).getOrNull()?:listOf()
        }
        if(user.source == 2 && maxsource > 0){
            val list= userRssSourceService.getallSourcelist(user.id)
            if(list.size > maxsource){
                throw DataThrowable().data(JsonResponse(false, MAX_ERROR))
            }
        }
        val rssSourcelist= RssSource.fromJsonArray(source)
        rssSourcelist.forEach {
            if(list.isNotEmpty()){
                if(list.contains(it.sourceUrl)){
                    addorupdate(it,user).let {  (ins,ups)->
                        insert += ins
                        update += ups
                    }
                }
            }else{
                addorupdate(it,user).let {  (ins,ups)->
                    insert += ins
                    update += ups
                }
            }
        }
        JsonResponse(true,"新增${insert}条订阅源，更新${update}条订阅源")
    }


    @Mapping("/getRssSourcesloginui")
    fun getRssSourcesloginui(accessToken: String?, url: String) = run {
        val user = getuserbytocken(accessToken)
        val rss=if(user.source == 2){
            userRssSourceService.getRssSource(url,user.id!!)?.toBaseSource()
        }else{
            rssSourceService.getRssSource(url)?.toBaseSource()
        } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        val rssSource=RssSource.fromJson(rss.json?:"")
        rssSource.userid=user.id
        rssSource.usertocken=accessToken
        var loginUi=rssSource.loginUi
        if(!loginUi.isNullOrEmpty()){
            kotlin.runCatching {
                val r=GSON.fromJsonArray<Any>(loginUi).getOrNull()
                loginUi= GSON.toJson(r)
            }
        }
        JsonResponse(true).Data(loginUi)
    }

    @Mapping("/getRssType")
    fun getRssType(accessToken:String?, id:String)= runBlocking{
        val user = getuserbytocken(accessToken)
        val rss=if(user.source == 2){
            userRssSourceService.getRssSource(id,user.id!!)?.toBaseSource()
        }else{
            rssSourceService.getRssSource(id)?.toBaseSource()
        } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        if(rss.enabled != true) throw DataThrowable().data(JsonResponse(false, NOT_IS))
        val rssSource=RssSource.fromJson(rss.json?:"")
        rssSource.userid=user.id
        rssSource.usertocken=accessToken
        var type = 0
        var url: String
        var name=""
        if(rssSource.singleUrl ){
            type=1
        }
        kotlin.runCatching {
            val sorts=rssSource.sortUrls()
            if(sorts.isNotEmpty()){
                url=rssSource.sortUrls()[0].second
                name=rssSource.sortUrls()[0].first
            }else{
                url=rss.sourceUrl
            }
            val header=rssSource.getHeaderMap()
            var loginUi=rssSource.loginUi
            if(!loginUi.isNullOrEmpty()){
                kotlin.runCatching {
                    val r=GSON.fromJsonArray<Any>(loginUi).getOrNull()
                    loginUi= GSON.toJson(r)
                }
            }
            JsonResponse(true).Data(mapOf("type" to type,
                "url" to url,
                "name" to name,
                "enableJs" to rssSource.enableJs,
                "js" to rssSource.injectJs,
                "loginUi" to loginUi,
                "loginUrl" to rssSource.loginUrl,
                "contentBlacklist" to rssSource.contentBlacklist,
                "contentWhitelist" to rssSource.contentWhitelist,
                "shouldOverrideUrlLoading" to rssSource.shouldOverrideUrlLoading,
                "header" to GSON.toJson(header),
            ))
        }.onFailure {
            if ( timeout > 0 && it is SocketTimeoutException  || it is SocketException || (it.message?.contains("timeout"))?:false) {
                val md5=Md5(rss.json?:"")
                var num=  cacheService.getOrStore(md5, Int::class.java,600) {
                    0
                }
                if(num > timeout){
                    if(user.source == 2){
                        userRssSourceService.changeEnabled(id,user.id!!,false)
                    }else{
                        rssSourceService.changeEnabled(id,false);
                    }
                }else{
                    num++
                    cacheService.store(md5,num,600)
                }
            }
            App.log("${rss.sourceName}sorts加载失败:${it.message}",accessToken?:"")
            App.toast("${rss.sourceName}sorts加载失败:${it.message}",accessToken?:"")
            throw it
        }
    }

    //@Cache(key = "getArticles:\${accessToken},\${id},\${page},\${sortUrl},\${sortName}", tags = "rsssearch\${accessToken}", seconds = 600)
    @Mapping("/getArticles")
    fun  getArticles(accessToken:String?, id:String,sortUrl :String ,sortName:String,page:Int)= runBlocking{
        val user = getuserbytocken(accessToken)
        val rss=if(user.source == 2){
            userRssSourceService.getRssSource(id,user.id!!)?.toBaseSource()
        }else{
            rssSourceService.getRssSource(id)?.toBaseSource()
        } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        if(rss.enabled != true) throw DataThrowable().data(JsonResponse(false, NOT_IS))
        val rssSource=RssSource.fromJson(rss.json?:"")
        rssSource.userid=user.id
        rssSource.usertocken=accessToken
        var articles:Pair<MutableList<RssArticle>, String?>
        var i=0
        while (true){
            try{
                articles=Rss().getArticles(sortName, sortUrl,rssSource,page)
                break
            }catch (e:Exception){
                i++
                if(i> 3){
                    App.toast("${rss.sourceName}列表加载失败:${e.message}",accessToken?:"")
                    App.log("${rss.sourceName}列表加载失败:${e.message}",accessToken?:"")
                    throw e
                }
            }
        }
        JsonResponse(true).Data(mapOf("articles" to articles.first,"next" to articles.second))
    }

    @Cache(key = "getRsssortUrls:\${accessToken},\${id}", tags = "rsssearch\${accessToken}", seconds = 600)
    @Mapping("/getRsssortUrls")
    fun  getRsssortUrls(accessToken:String?, id:String)= runBlocking{
        val user = getuserbytocken(accessToken)
        val rss=if(user.source == 2){
            userRssSourceService.getRssSource(id,user.id!!)?.toBaseSource()
        }else{
            rssSourceService.getRssSource(id)?.toBaseSource()
        } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        if(rss.enabled != true) throw DataThrowable().data(JsonResponse(false, NOT_IS))
        val rssSource=RssSource.fromJson(rss.json?:"")
        kotlin.runCatching {
            rssSource.userid=user.id
            rssSource.usertocken=accessToken
            val list = mutableListOf<Map<String,String>>()
            rssSource.sortUrls().forEach{
                list.add(mapOf("sortName" to it.first,"sortUrl" to it.second))
            }
            return@runBlocking JsonResponse(true).Data(list)
        }.onFailure {
            if (timeout > 0 &&  it is SocketTimeoutException  || it is SocketException || (it.message?.contains("timeout"))?:false) {
                val md5=Md5(rss.json?:"")
                var num=  cacheService.getOrStore(md5, Int::class.java,600) {
                    0
                }
                if(num > timeout){
                    if(user.source == 2){
                        userRssSourceService.changeEnabled(id,user.id!!,false)
                    }else{
                        rssSourceService.changeEnabled(id,false);
                    }
                }else{
                    num++
                    cacheService.store(md5,num,600)
                }
            }
            App.log("${rss.sourceName}tabs加载失败:${it.message}",accessToken?:"")
            App.toast("${rss.sourceName}tabs加载失败:${it.message}",accessToken?:"")
            return@runBlocking JsonResponse(false,it.message?:"")
        }

    }

    @Cache(key = "getRssContent:\${accessToken},\${id},\${article}", tags = "rsssearch\${accessToken}", seconds = 300)
    @Mapping("/getRssContent")
    fun  getRssContent(accessToken:String?, id:String,article : String)= runBlocking{
        val myid=UUID.randomUUID().toString()

        val user = getuserbytocken(accessToken)
        val rss=if(user.source == 2){
            userRssSourceService.getRssSource(id,user.id!!)?.toBaseSource()
        }else{
            rssSourceService.getRssSource(id)?.toBaseSource()
        } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        if(rss.enabled != true) throw DataThrowable().data(JsonResponse(false, NOT_IS))
        kotlin.runCatching {
            val rssArticle= GSON.fromJsonObject<RssArticle>(article).getOrNull()?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            val rssSource=RssSource.fromJson(rss.json?:"")
            rssSource.userid=user.id
            rssSource.usertocken=accessToken
            var body: String = if (rssSource.ruleDescription.isNullOrBlank()) {
                Rss().getContent(rssArticle, rssSource.ruleContent?:"",rssSource)
            }else{
                rssArticle.description?:""
            }
            val header=rssSource.getHeaderMap()
            if(body.isNotEmpty()){
                body=clHtml(body,rssSource)
                cacheService.store(myid,body,300)
            }
            return@runBlocking  JsonResponse(true).Data(mapOf(
                "content" to body,
                "enableJs" to rssSource.enableJs,
                "js" to rssSource.injectJs,
                "header" to GSON.toJson(header),
                "baseurl" to rssSource.loadWithBaseUrl,
                "id" to myid
                ))
        }.onFailure {
            App.log("${rss.sourceName}正文加载失败:${it.message}",accessToken?:"")
            App.toast("${rss.sourceName}正文加载失败:${it.message}",accessToken?:"")
            return@runBlocking  JsonResponse(false,it.message?:"")
        }
    }



    @Mapping("/getRssLoginInfo")
    open fun getRssLoginInfo(accessToken: String?, id: String) = run {
        val user = getuserbytocken(accessToken)
        val rss=if(user.source == 2){
            userRssSourceService.getRssSource(id,user.id!!)?.toBaseSource()
        }else{
            rssSourceService.getRssSource(id)?.toBaseSource()
        } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        val rssSource=RssSource.fromJson(rss.json?:"")
        rssSource.userid = user.id
        rssSource.usertocken = accessToken
        var info = rssSource.getLoginInfo()
        if (info.isNullOrBlank()) {
            info = "{}"
        }
        JsonResponse(true).Data(info)
    }

    @CacheRemove(tags = "rsssearch\${accessToken}")
    @Mapping("/putRssLoginInfo")
    open fun putRssLoginInfo(accessToken: String?, id: String, info: String?) = run {
        val user = getuserbytocken(accessToken)
        val rss=if(user.source == 2){
            userRssSourceService.getRssSource(id,user.id!!)?.toBaseSource()
        }else{
            rssSourceService.getRssSource(id)?.toBaseSource()
        } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        val rssSource=RssSource.fromJson(rss.json?:"")
        rssSource.userid = user.id
        rssSource.usertocken = accessToken
        rssSource.putLoginInfo(info ?: "{}")
        kotlin.runCatching { rssSource.login() }
        JsonResponse(true)
    }

    @CacheRemove(tags = "rsssearch\${accessToken}")
    @Mapping("/rssaction")
    open fun rssaction(accessToken: String?, id: String, action: String?) = runBlocking {
        val user = getuserbytocken(accessToken)
        if(action == null) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        val rss=if(user.source == 2){
            userRssSourceService.getRssSource(id,user.id!!)?.toBaseSource()
        }else{
            rssSourceService.getRssSource(id)?.toBaseSource()
        } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        val rssSource=RssSource.fromJson(rss.json?:"")
        rssSource.userid = user.id
        rssSource.usertocken = accessToken
        kotlin.runCatching {
            rssSource.runaction(action)
        }.onFailure { e ->
            logger.info("$action JavaScript error", e)
            App.log("$action JavaScript error:$e",accessToken?:"")
            App.toast("JavaScript error:$e",accessToken?:"")
        }
        JsonResponse(true)
    }

    @Mapping("/getRssVariable")
    open fun getRssVariable(accessToken: String?, id: String) = run {
        val user = getuserbytocken(accessToken)
        val rss=if(user.source == 2){
            userRssSourceService.getRssSource(id,user.id!!)?.toBaseSource()
        }else{
            rssSourceService.getRssSource(id)?.toBaseSource()
        } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        val rssSource=RssSource.fromJson(rss.json?:"")
        rssSource.userid = user.id
        rssSource.usertocken = accessToken
        val info = rssSource.getVariable()
        JsonResponse(true).Data(info)
    }

    @CacheRemove(tags = "rsssearch\${accessToken}")
    @Mapping("/setRssVariable")
    open fun setRssVariable(accessToken: String?, id: String, info: String?) = run {
        val user = getuserbytocken(accessToken)
        val rss=if(user.source == 2){
            userRssSourceService.getRssSource(id,user.id!!)?.toBaseSource()
        }else{
            rssSourceService.getRssSource(id)?.toBaseSource()
        } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        val rssSource=RssSource.fromJson(rss.json?:"")
        rssSource.userid = user.id
        rssSource.usertocken = accessToken
        rssSource.setVariable(info)
        JsonResponse(true)
    }


    @Mapping("/getRssContenthtml")
    fun  getRssContenthtml(ctx: Context, id:String)= runBlocking{
        val html=cacheService.get(id,String::class.java)
        ctx.outputAsHtml(html)
    }


    private fun addorupdate(rss: RssSource,user: Users) = run{
        var insert = 0
        var update = 0
        if(rss.sourceName.isEmpty()){
            return  Pair(insert, update)
        }
        if(user.source == 2){
            val source=UserRssSource().jsontomodel(rss, userid = user.id!!)
            userRssSourceService.getRssSource(rss.sourceUrl, userid = user.id!!).let {
                if (it != null){
                    source.enabled=it.enabled
                    if(it.createtime != null){
                        source.createtime=it.createtime
                    }
                    update += userRssSourceService.userRssSourceMapper.updateById(source)
                }else{
                    source.sourceorder=9999
                    insert += userRssSourceService.userRssSourceMapper.insert(source)
                }
            }
            userRssSourceService.cleancache(user.id)
        }else{
            val source= web.model.RssSource().jsontomodel(rss)
            rssSourceService.getRssSource(rss.sourceUrl).let {
                if (it != null){
                    source.enabled=it.enabled
                    if(it.createtime != null){
                        source.createtime=it.createtime
                    }
                    update += rssSourceService.rssSourceMapper.updateById(source)
                }else{
                    source.sourceorder=9999
                    insert += rssSourceService.rssSourceMapper.insert(source)
                }
            }
            rssSourceService.cleancache()
        }
        Pair(insert, update)
    }

   private fun clHtml(content: String,rssSource :RssSource): String {
        return when {
            !rssSource.style.isNullOrEmpty() -> {
                """
                    <style>
                        ${rssSource.style}
                    </style>
                    $content
                """.trimIndent()
            }

            content.contains("<style>".toRegex()) -> {
                content
            }

            else -> {
                """
                    <style>
                        img{max-width:100% !important; width:auto; height:auto;}
                        video{object-fit:fill; max-width:100% !important; width:auto; height:auto;}
                        body{word-wrap:break-word; height:auto;max-width: 100%; width:auto;}
                    </style>
                    $content
                """.trimIndent()
            }
        }
    }

}