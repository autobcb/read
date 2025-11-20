package web.controller.api


import book.model.BookSource
import book.util.GSON
import book.util.fromJsonArray
import org.noear.solon.annotation.Body
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.annotation.Path
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.Cache
import org.noear.solon.data.annotation.CacheRemove
import org.noear.solon.data.annotation.Tran
import org.noear.solon.data.cache.CacheService
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.mapper.BooklistMapper
import web.model.BaseSource
import web.model.Booklist
import web.model.Users
import web.response.*
import web.util.hash.Md5
import java.util.Date
import kotlin.collections.forEach

@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class SourceController:BaseController() {

    @Inject(value = "\${user.maxsource:0}", autoRefreshed=true)
    var maxsource: Int= 0

    @Inject
    lateinit var cacheService: CacheService

    @Inject
    lateinit var booklistMapper: BooklistMapper


    @Mapping("/getBookSourcesPage")
    open fun getBookSources(accessToken: String?) = run {
        val user = getuserbytocken(accessToken)
        if (user.sourcemd5.isNullOrBlank()){
            user.sourcemd5=Md5(System.currentTimeMillis().toString())
            usersMapper.updatesourcemd5(user.id!!, user.sourcemd5!!)
        }
        val source: List<BaseSource> = if( user.source != 0){
            getallBookSourcelist(user)
        }else{
            getBookSourcelist(true,user)
        }
        var list: MutableList<Map<String, Any?>> = mutableListOf()
        var page = 1
        source.forEach {
            val s=BookSource.fromJson(it.json).getOrNull()
            s?.usertocken=accessToken
            s?.userid=user.id
            var loginUi=s?.loginUi
            if(!loginUi.isNullOrEmpty()){
                runCatching {
                    val r=GSON.fromJsonArray<Any>(loginUi).getOrNull()
                    loginUi= GSON.toJson(r)
                }
            }
            if(list.size >= 50){
                cacheService.store("getBookSourcesNew:${accessToken}${user.sourcemd5+"${user.source}"}${page}",JsonResponse(true).Data(list),cachetime)
                page++
                list=mutableListOf()
            }
            list.add(
                mapOf(
                    "checkKeyWord" to s?.ruleSearch?.checkKeyWord,
                    "variableComment" to s?.variableComment,
                    "bookSourceGroup" to it.bookSourceGroup,
                    "loginUrl" to s?.loginUrl,
                    "loginUi" to loginUi,
                    "bookSourceName" to it.bookSourceName,
                    "bookSourceUrl" to it.bookSourceUrl,
                    "enabledExplore" to it.enabledExplore,
                    "enabled" to it.enabled
                )
            )
        }
        cacheService.store("getBookSourcesNew:${accessToken}${user.sourcemd5+"${user.source}"}${page}",JsonResponse(true).Data(list),cachetime)
        JsonResponse(true).Data(mapOf("page" to page, "md5" to user.sourcemd5+"${user.source}"))
    }


    @Cache(key = "getBookSourcesNew:\${accessToken}\${md5}\${page}",  seconds = 60)
    @Mapping("/getBookSourcesNew")
    open fun getBookshelf(accessToken: String?,md5: String?,page: String) = run {
        JsonResponse(false)
    }

    @Mapping("/getcansource")
    open fun getcansource( accessToken:String?)=run{
        val user=getuserbytocken(accessToken)
        if(user.source == 0){
            throw DataThrowable().data(JsonResponse(false,CAN_NOT))
        }
        JsonResponse(true)
    }

    @Mapping("/saveBookSources")
    fun saveBookSources(accessToken:String?, @Body content:String)=run{
        val user=getsourceuser(accessToken)
        var insert = 0
        var update = 0
        val bookSourcelist= BookSource.fromJsonArray(content).getOrNull()

        if(user.source == 2 && maxsource > 0){
            val list= userBookSourceMapper.getallBookSourcelist(user.id!!)?:listOf()
            if(list.size +(bookSourcelist?:listOf<String>()).size> maxsource){
                throw DataThrowable().data(JsonResponse(false, MAX_ERROR))
            }
        }

        bookSourcelist?.forEach {
           runCatching {
               addorupdate(it,user).let {  (ins,ups)->
                   insert += ins
                   update += ups
               }
           }
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true,"新增${insert}条书源，更新${update}条书源")
    }

    @Mapping("/saveBookSourcesv2")
    fun saveBookSourcesv2(accessToken:String?, source:String, urls:String)=run{
        val user=getsourceuser(accessToken)
        var insert = 0
        var update = 0
        var list= listOf<String>()
        if(urls.isNotEmpty()){
            list=GSON.fromJsonArray<String>(urls).getOrNull()?:listOf()
        }
        if(user.source == 2 && maxsource > 0){
            val list2= userBookSourceMapper.getallBookSourcelist(user.id!!)?:listOf()
            if(list2.size +list.size > maxsource){
                throw DataThrowable().data(JsonResponse(false, MAX_ERROR))
            }
        }
        val bookSourcelist= BookSource.fromJsonArray(source).getOrNull()
        bookSourcelist?.forEach {
           runCatching {
               if(list.isNotEmpty()){
                   if(list.contains(it.bookSourceUrl)){
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
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true,"新增${insert}条书源，更新${update}条书源")
    }


    @Mapping("/saveBookSource")
    fun saveBookSource( accessToken:String?, @Body content:String)=run{
        val user=getsourceuser(accessToken)
        var insert = 0
        var update = 0
        val booksource  = BookSource.fromJson(content).getOrNull()?: BookSource()
        if(user.source == 2 && maxsource > 0){
            val list= userBookSourceMapper.getallBookSourcelist(user.id!!)?:listOf()
            if(list.size > maxsource){
                throw DataThrowable().data(JsonResponse(false, MAX_ERROR))
            }
        }
        if (booksource.bookSourceName.isNotEmpty())
            addorupdate(booksource, user ).let {  (ins,ups)->
                insert += ins
                update += ups
            }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true,"新增${insert}条书源，更新${update}条书源")
    }


    @Mapping("/topSource")
    fun topSource( accessToken:String?, id: String?)= run{
        val user=getsourceuser(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        if(user.source == 2){
            val bookSource= userBookSourceMapper.getBookSource(id,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            val sources = userBookSourceMapper.getallBookSourcelist(user.id!!)
            if(maxsource > 0){
                val list= sources?:listOf()
                if(list.size > maxsource){
                    throw DataThrowable().data(JsonResponse(false, MAX_ERROR))
                }
            }
            var order=1
            for( it in sources!!){
                if(it.bookSourceUrl == bookSource.bookSourceUrl){
                    userBookSourceMapper.changeorder(it.id?:"", 0)
                }else{
                    userBookSourceMapper.changeorder(it.id?:"", order)
                    order++
                }
            }
        }else{
            val bookSource= bookSourceMapper.getBookSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            val sources = bookSourceMapper.getallBookSourcelist()
            var order=1
            for( it in sources!!){
                if(it.bookSourceUrl == bookSource.bookSourceUrl){
                    bookSourceMapper.changeorder(it.bookSourceUrl?:"", 0)
                }else{
                    bookSourceMapper.changeorder(it.bookSourceUrl?:"", order)
                    order++
                }
            }
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true)
    }

    @Mapping("/bottomSource")
    fun bottomSource( accessToken:String?, id: String?)= run{
        val user=getsourceuser(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        var order=0
        if(user.source == 2){
            val bookSource= userBookSourceMapper.getBookSource(id,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            val sources = userBookSourceMapper.getallBookSourcelist(user.id!!)
            if(maxsource > 0){
                val list= sources?:listOf()
                if(list.size > maxsource){
                    throw DataThrowable().data(JsonResponse(false, MAX_ERROR))
                }
            }
            for( it in sources!!){
                if(it.bookSourceUrl == bookSource.bookSourceUrl){
                    userBookSourceMapper.changeorder(it.id?:"",sources.size-1)
                }else{
                    userBookSourceMapper.changeorder(it.id?:"",order)
                    order++
                }
            }
        }else{
            val bookSource= bookSourceMapper.getBookSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            val sources = bookSourceMapper.getallBookSourcelist()
            for( it in sources!!){
                if(it.bookSourceUrl == bookSource.bookSourceUrl){
                    bookSourceMapper.changeorder(it.bookSourceUrl?:"", sources.size-1)
                }else{
                    bookSourceMapper.changeorder(it.bookSourceUrl?:"", order)
                    order++
                }
            }
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true)
    }

    @Mapping("/delbookSource")
    fun delbookSource(accessToken:String?,id: String?) = run{
        val user=getsourceuser(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        if(user.source == 2){
            val bookSource= userBookSourceMapper.getBookSource(id,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            userBookSourceMapper.deleteById(bookSource.id?:"")
        }else{
            bookSourceMapper.getBookSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            bookSourceMapper.deleteById(id)
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true)
    }

    @Mapping("/topallSource")
    fun topallSource( accessToken:String?,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
        if (ids == null || ids.isEmpty()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        var order=ids.size
        if(user.source == 2){
            val sources = userBookSourceMapper.getallBookSourcelist(user.id!!)
            if(maxsource > 0){
                val list= sources?:listOf()
                if(list.size > maxsource){
                    throw DataThrowable().data(JsonResponse(false, MAX_ERROR))
                }
            }
            for( it in sources!!){
                if(!ids.contains(it.bookSourceUrl)){
                    userBookSourceMapper.changeorder(it.id?:"", order)
                    order++
                }
            }
            order = 0
            for(id in ids){
                for( it in sources){
                    if(it.bookSourceUrl == id){
                        userBookSourceMapper.changeorder(it.id?:"", order)
                        break
                    }
                }
                order++
            }
        }else{
            val sources = bookSourceMapper.getallBookSourcelist()
            for( it in sources!!){
                if(!ids.contains(it.bookSourceUrl)){
                    bookSourceMapper.changeorder(it.bookSourceUrl?:"", order)
                    order++
                }
            }
            order = 0
            for(id in ids){
                bookSourceMapper.changeorder(id, order)
                order++
            }
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true)
    }

    @Mapping("/bottomallSource")
    fun bottomallSource( accessToken:String?,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
        if (ids == null || ids.isEmpty()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        var order=0
        if(user.source == 2){
            val sources = userBookSourceMapper.getallBookSourcelist(user.id!!)
            if(maxsource > 0){
                val list= sources?:listOf()
                if(list.size > maxsource){
                    throw DataThrowable().data(JsonResponse(false, MAX_ERROR))
                }
            }
            for( it in sources!!){
                if(!ids.contains(it.bookSourceUrl)){
                    userBookSourceMapper.changeorder(it.id?:"", order)
                    order++
                }
            }
            for(id in ids){
                for( it in sources){
                    if(it.bookSourceUrl == id){
                        userBookSourceMapper.changeorder(it.id?:"", order)
                        break
                    }
                }
                order++
            }
        }else{
            val sources = bookSourceMapper.getallBookSourcelist()
            for( it in sources!!){
                if(!ids.contains(it.bookSourceUrl)){
                    bookSourceMapper.changeorder(it.bookSourceUrl?:"", order)
                    order++
                }
            }
            for(id in ids){
                bookSourceMapper.changeorder(id, order)
                order++
            }
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true)
    }

    @Mapping("/editsourcegroup")
    fun editsourcegroup( accessToken:String?,st:String,group:String,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
        if (ids == null || ids.isEmpty()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        if(user.source == 2){
            for(id in ids){
                val bookSource= userBookSourceMapper.getBookSource(id,user.id!!)
                if (bookSource == null) continue
                val sp=bookSource.bookSourceGroup?.split(",")
                val groups=mutableListOf<String>()
                if(st == "0"){
                    groups.add(group)
                }
                sp?.forEach{
                    if(it != group){
                        groups.add(it)
                    }
                }
                bookSource.bookSourceGroup=groups.joinToString(",")
                if(bookSource.bookSourceGroup!!.endsWith(",")){
                    bookSource.bookSourceGroup=bookSource.bookSourceGroup!!.substring(0, bookSource.bookSourceGroup!!.length - 1)
                }
                userBookSourceMapper.changegroup(bookSource.id!!, bookSource.bookSourceGroup?:" ")
            }
        }else{
            for(id in ids){
                val bookSource= bookSourceMapper.getBookSource(id)
                if (bookSource == null) continue
                val sp=bookSource.bookSourceGroup?.split(",")
                val groups=mutableListOf<String>()
                if(st == "0"){
                    groups.add(group)
                }
                sp?.forEach{
                    if(it != group){
                        groups.add(it)
                    }
                }
                bookSource.bookSourceGroup=groups.joinToString(",")
                if(bookSource.bookSourceGroup!!.endsWith(",")){
                    bookSource.bookSourceGroup=bookSource.bookSourceGroup!!.substring(0, bookSource.bookSourceGroup!!.length - 1)
                }
                bookSourceMapper.changegroup(id,bookSource.bookSourceGroup?:" ")
            }
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true)
    }

    @Mapping("/delbookSources")
    fun delbookSources(accessToken:String?,@Body ids: List<String>?) = run{
        val user=getsourceuser(accessToken)
        ids?.forEach {id->
            if (id.isNotBlank()){
                if(user.source == 2){
                    userBookSourceMapper.delBookSource(id,user.id!!)
                }else{
                    bookSourceMapper.deleteById(id)
                }
            }
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true)
    }

    @Mapping("/getbookSources")
    fun getbookSources(accessToken:String?,id: String?) = run{
        val user=getsourceuser(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val bookSource= if(user.source == 2){
            userBookSourceMapper.getBookSource(id,user.id!!)?.toBaseSource()
        }else{
            bookSourceMapper.getBookSource(id)?.toBaseSource()
        } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
        JsonResponse(true).Data(mapOf(
            "json" to bookSource.json,
            "enabled" to bookSource.enabled,
            "bookSourceGroup" to bookSource.bookSourceGroup,
            "enabledexplore" to bookSource.enabledExplore,
        ))
    }

    @Tran
    @CacheRemove(tags = "search\${accessToken}")
    @Mapping("/editbookSources")
    open fun editbookSources(accessToken:String?, @Body content:EditMsg) = run{
        val user=getsourceuser(accessToken)
        val source= BookSource.fromJson(content.json?:"").getOrNull().also {
            if(it == null ) throw DataThrowable().data(JsonResponse(false, SOURCE_JSON_ERROR))
        }!!
        if(source.bookSourceUrl.isEmpty()) throw DataThrowable().data(JsonResponse(false, SOURCE_URL_ERROR))
        var isupdate=false
        if(user.source == 2){
            if(maxsource > 0){
                val list= userBookSourceMapper.getallBookSourcelist(user.id!!)?:listOf()
                if(list.size > maxsource){
                    throw DataThrowable().data(JsonResponse(false, MAX_ERROR))
                }
            }
            val bookSource= web.model.UserBookSource().jsontomodel(source,user.id!!)
            bookSource.sourceorder=9999
            if(content.id  != null && content.id!!.isNotEmpty()){
                val bs=
                    userBookSourceMapper.getBookSource(content.id!!,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                bookSource.sourceorder=bs.sourceorder
                bookSource.createtime=bs.createtime
                bookSource.lastUpdateTime=Date().time
                userBookSourceMapper.deleteById(bookSource.id)
                if(content.id  != source.bookSourceUrl){
                    isupdate=true
                }
            }else{
                val bs=userBookSourceMapper.getBookSource(source.bookSourceUrl,user.id!!)
                if (bs != null){
                    throw DataThrowable().data(JsonResponse(false, SOURCE_IS))
                }
            }
            bookSource.enabled=source.enabled
            userBookSourceMapper.insert(bookSource)
        }else{
            val bookSource= web.model.BookSource().jsontomodel(source)
            bookSource.sourceorder=9999
            if(content.id  != null && content.id!!.isNotEmpty()){
                val bs=
                    bookSourceMapper.getBookSource(content.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                bookSource.sourceorder=bs.sourceorder
                bookSource.createtime=bs.createtime
                bookSource.lastUpdateTime=Date().time
                bookSourceMapper.deleteById(content.id)
                if(content.id  != source.bookSourceUrl){
                    isupdate=true
                }
            }else{
                val bs=bookSourceMapper.getBookSource(source.bookSourceUrl)
                if (bs != null){
                    throw DataThrowable().data(JsonResponse(false, SOURCE_IS))
                }
            }
            bookSource.enabled=source.enabled
            bookSourceMapper.insert(bookSource)
        }
        if(isupdate){
            booklistMapper.updatebysource(user.id!!,content.id!!,source.bookSourceUrl)
            web.notification.Book.sendNotification(user)
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true)
    }


    @Mapping("/stopbookSource")
    fun stopbookSource(accessToken:String?,id: String? ,st: String?)= run{
        val user= getsourceuser(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        //torderSource(user)
        if(user.source == 2){
            val bookSource= userBookSourceMapper.getBookSource(id,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            when(st){
                "0"->{
                    userBookSourceMapper.changeEnabled(bookSource.id!!,false)
                }
                "1"->{
                    userBookSourceMapper.changeEnabled(bookSource.id!!,true)
                }
                else -> throw DataThrowable().data(JsonResponse(false, USE_ERROE))
            }
        }else{
            bookSourceMapper.getBookSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            when(st){
                "0"->{
                    bookSourceMapper.changeEnabled(id,false)
                }
                "1"->{
                    bookSourceMapper.changeEnabled(id,true)
                }
                else -> throw DataThrowable().data(JsonResponse(false, USE_ERROE))
            }
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true)
    }

    @Mapping("/stopbookSources")
    fun stopbookSources(accessToken:String?,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
        //torderSource(user)
        if(user.source == 2){
            ids?.forEach {
                if (it.isNotBlank()){
                    val bookSource=
                        userBookSourceMapper.getBookSource(it,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    userBookSourceMapper.changeEnabled(bookSource.id!!,false)
                }
            }
        }else{
            ids?.forEach {
                if (it.isNotBlank()){
                    bookSourceMapper.getBookSource(it) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    bookSourceMapper.changeEnabled(it,false)
                }
            }
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true)
    }

    @Mapping("/startbookSources")
    fun startbookSources(accessToken:String?,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
        //torderSource(user)
        if(user.source == 2){
            ids?.forEach {
                if (it.isNotBlank()){
                    val bookSource=
                        userBookSourceMapper.getBookSource(it,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    userBookSourceMapper.changeEnabled(bookSource.id!!,true)
                }
            }
        }else{
            ids?.forEach {
                if (it.isNotBlank()){
                    bookSourceMapper.getBookSource(it) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    bookSourceMapper.changeEnabled(it,true)
                }
            }
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true)
    }

    @Mapping("/stopbookSourceExplores")
    fun stopbookSourceExplores(accessToken:String?,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
        //torderSource(user)
        if(user.source == 2){
            ids?.forEach {
                if (it.isNotBlank()){
                    val bookSource=
                        userBookSourceMapper.getBookSource(it,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    userBookSourceMapper.changeenabledExplore(bookSource.id!!,false)
                }
            }
        }else{
            ids?.forEach {
                if (it.isNotBlank()){
                    bookSourceMapper.getBookSource(it) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    bookSourceMapper.changeenabledExplore(it,false)
                }
            }
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true)
    }

    @Mapping("/startbookSourceExplores")
    fun startbookSourceExplores(accessToken:String?,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
       // torderSource(user)
        if(user.source == 2){
            ids?.forEach {
                if (it.isNotBlank()){
                    val bookSource=
                        userBookSourceMapper.getBookSource(it,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    userBookSourceMapper.changeenabledExplore(bookSource.id!!,true)
                }
            }
        }else{
            ids?.forEach {
                if (it.isNotBlank()){
                    bookSourceMapper.getBookSource(it) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    bookSourceMapper.changeenabledExplore(it,true)
                }
            }
        }
        web.notification.Source.sendNotification(user.let { if (user.source == 2) it else null })
        JsonResponse(true)
    }


    @Mapping("/getbookSourcejson")
    fun getbookSourcejson(accessToken:String?,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
        var s="["
        ids?.forEach {
            if (it.isNotBlank()){
                val bookSource=if(user.source == 2){
                    userBookSourceMapper.getBookSource(it,user.id!!)?.toBaseSource()
                }else{
                    bookSourceMapper.getBookSource(it)?.toBaseSource()
                } ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                s = if(s == "[" ){
                    "$s ${bookSource.json}"
                }else {
                    "$s , ${bookSource.json}"
                }
            }
        }
        s="$s ]"
        JsonResponse(true).Data(s)
    }


   private fun addorupdate(bookSource: BookSource,user: Users) = run{
        var insert = 0
        var update = 0
        if(bookSource.bookSourceName.isEmpty()){
            return  Pair(insert, update)
        }
       if(user.source == 2){
           val source= web.model.UserBookSource().jsontomodel(bookSource, userid = user.id!!)
           userBookSourceMapper.getBookSource(bookSource.bookSourceUrl, userid = user.id!!).let {
               if (it != null){
                   source.enabled=it.enabled
                   if(it.createtime != null){
                       source.createtime=it.createtime
                   }
                   source.sourceorder=it.sourceorder
                   bookSource.lastUpdateTime=Date().time
                   update += userBookSourceMapper.updateById(source)
               }else{
                   source.enabled=true
                   source.sourceorder=9999
                   insert += userBookSourceMapper.insert(source)
               }
           }
       }else{
           val source= web.model.BookSource().jsontomodel(bookSource)
           bookSourceMapper.getBookSource(bookSource.bookSourceUrl).let {
               if (it != null){
                   source.enabled=it.enabled
                   if(it.createtime != null){
                       source.createtime=it.createtime
                   }
                   source.sourceorder=it.sourceorder
                   bookSource.lastUpdateTime=Date().time
                   update += bookSourceMapper.updateById(source)
               }else{
                   source.enabled=true
                   source.sourceorder=9999
                   insert += bookSourceMapper.insert(source)
               }
           }
       }
        Pair(insert, update)
    }


}

class EditMsg{
    var json:String?=null
    var id:String?=null
}