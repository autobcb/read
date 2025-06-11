package web.controller.api


import book.model.BookSource
import book.util.GSON
import book.util.fromJsonArray
import org.noear.solon.annotation.Body
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Mapping
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.CacheRemove
import org.noear.solon.data.annotation.Tran
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.model.Users
import web.response.*
import java.util.Date

@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class SourceController:BaseController() {
    
    
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

        bookSourcelist?.forEach {
            addorupdate(it,user).let {  (ins,ups)->
                insert += ins
                update += ups
            }
        }
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
        val bookSourcelist= BookSource.fromJsonArray(source).getOrNull()
        bookSourcelist?.forEach {
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
        JsonResponse(true,"新增${insert}条书源，更新${update}条书源")
    }


    @Mapping("/saveBookSource")
    fun saveBookSource( accessToken:String?, @Body content:String)=run{
        val user=getsourceuser(accessToken)
        var insert = 0
        var update = 0
        val booksource  = BookSource.fromJson(content).getOrNull()?: BookSource()
        if (booksource.bookSourceName.isNotEmpty())
            addorupdate(booksource, user ).let {  (ins,ups)->
                insert += ins
                update += ups
            }
        JsonResponse(true,"新增${insert}条书源，更新${update}条书源")
    }


    @Mapping("/topSource")
    fun topSource( accessToken:String?, id: String?)= run{
        val user=getsourceuser(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        if(user.source == 2){
            val bookSource= userBookSourceService.getBookSource(id,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            val sources = userBookSourceService.getallBookSourcelist(user.id!!)
            var order=1
            for( it in sources!!){
                if(it.bookSourceUrl == bookSource.bookSourceUrl){
                    userBookSourceService.changeorder(it.id?:"",user.id, 0)
                }else{
                    userBookSourceService.changeorder(it.id?:"",user.id, order)
                    order++
                }
            }
        }else{
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
        }
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
            val bookSource= userBookSourceService.getBookSource(id,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            val sources = userBookSourceService.getallBookSourcelist(user.id!!)

            for( it in sources!!){
                if(it.bookSourceUrl == bookSource.bookSourceUrl){
                    userBookSourceService.changeorder(it.id?:"",user.id, sources.size-1)
                }else{
                    userBookSourceService.changeorder(it.id?:"",user.id, order)
                    order++
                }
            }
        }else{
            val bookSource= bookSourceService.getBookSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            val sources = bookSourceService.getallBookSourcelist()
            for( it in sources!!){
                if(it.bookSourceUrl == bookSource.bookSourceUrl){
                    bookSourceService.changeorder(it.bookSourceUrl?:"", sources.size-1)
                }else{
                    bookSourceService.changeorder(it.bookSourceUrl?:"", order)
                    order++
                }
            }
        }
        JsonResponse(true)
    }

    @Mapping("/delbookSource")
    fun delbookSource(accessToken:String?,id: String?) = run{
        val user=getsourceuser(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        if(user.source == 2){
            val bookSource= userBookSourceService.getBookSource(id,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            userBookSourceService.userBookSourceMapper.deleteById(bookSource.id?:"")
            userBookSourceService.cleancache(user.id)
        }else{
            bookSourceService.getBookSource(id) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            bookSourceService.booksSourceMapper.deleteById(id)
            bookSourceService.cleancache()
        }
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
            val sources = userBookSourceService.getallBookSourcelist(user.id!!)
            for( it in sources!!){
                if(!ids.contains(it.bookSourceUrl)){
                    userBookSourceService.changeorder(it.id?:"",user.id, order)
                    order++
                }
            }
            order = 0
            for(id in ids){
                for( it in sources){
                    if(it.bookSourceUrl == id){
                        userBookSourceService.changeorder(it.id?:"",user.id, order)
                        break;
                    }
                }
                order++
            }
        }else{
            val sources = bookSourceService.getallBookSourcelist()
            for( it in sources!!){
                if(!ids.contains(it.bookSourceUrl)){
                    bookSourceService.changeorder(it.bookSourceUrl?:"", order)
                    order++
                }
            }
            order = 0
            for(id in ids){
                bookSourceService.changeorder(id, order)
                order++
            }
        }
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
            val sources = userBookSourceService.getallBookSourcelist(user.id!!)
            for( it in sources!!){
                if(!ids.contains(it.bookSourceUrl)){
                    userBookSourceService.changeorder(it.id?:"",user.id, order)
                    order++
                }
            }
            for(id in ids){
                for( it in sources){
                    if(it.bookSourceUrl == id){
                        userBookSourceService.changeorder(it.id?:"",user.id, order)
                        break;
                    }
                }
                order++
            }
        }else{
            val sources = bookSourceService.getallBookSourcelist()
            for( it in sources!!){
                if(!ids.contains(it.bookSourceUrl)){
                    bookSourceService.changeorder(it.bookSourceUrl?:"", order)
                    order++
                }
            }
            for(id in ids){
                bookSourceService.changeorder(id, order)
                order++
            }
        }
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
                val bookSource= userBookSourceService.getBookSource(id,user.id!!)
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
                userBookSourceService.changegroup(bookSource.id, user.id,bookSource.bookSourceGroup)
            }
        }else{
            for(id in ids){
                val bookSource= bookSourceService.getBookSource(id)
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
                bookSourceService.changegroup(id,bookSource.bookSourceGroup)
            }
        }
        JsonResponse(true)
    }

    @Mapping("/delbookSources")
    fun delbookSources(accessToken:String?,@Body ids: List<String>?) = run{
        val user=getsourceuser(accessToken)
        ids?.forEach {id->
            if (id.isNotBlank()){
                if(user.source == 2){
                    userBookSourceService.delBookSource(id,user.id!!)
                }else{
                    bookSourceService.booksSourceMapper.deleteById(id)
                    bookSourceService.cleancache()
                }
            }
        }
        JsonResponse(true)
    }

    @Mapping("/getbookSources")
    fun getbookSources(accessToken:String?,id: String?) = run{
        val user=getsourceuser(accessToken)
        if (id.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val bookSource= if(user.source == 2){
            userBookSourceService.getBookSource(id,user.id!!)?.toBaseSource()
        }else{
            bookSourceService.getBookSource(id)?.toBaseSource()
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
        if(user.source == 2){
            val bookSource= web.model.UserBookSource().jsontomodel(source,user.id!!)
            bookSource.sourceorder=9999
            if(content.id  != null && content.id!!.isNotEmpty()){
                val bs=
                    userBookSourceService.getBookSource(content.id!!,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                bookSource.sourceorder=bs.sourceorder
                bookSource.createtime=bs.createtime
                bookSource.lastUpdateTime=Date().time
                userBookSourceService.userBookSourceMapper.deleteById(bookSource.id)
            }else{
                val bs=userBookSourceService.getBookSource(source.bookSourceUrl,user.id!!)
                if (bs != null){
                    throw DataThrowable().data(JsonResponse(false, SOURCE_IS))
                }
            }
            bookSource.enabled=source.enabled
            userBookSourceService.userBookSourceMapper.insert(bookSource)
            userBookSourceService.cleancache(user.id)
        }else{
            val bookSource= web.model.BookSource().jsontomodel(source)
            bookSource.sourceorder=9999
            if(content.id  != null && content.id!!.isNotEmpty()){
                val bs=
                    bookSourceService.getBookSource(content.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                bookSource.sourceorder=bs.sourceorder
                bookSource.createtime=bs.createtime
                bookSource.lastUpdateTime=Date().time
                bookSourceService.booksSourceMapper.deleteById(content.id)
            }else{
                val bs=bookSourceService.getBookSource(source.bookSourceUrl)
                if (bs != null){
                    throw DataThrowable().data(JsonResponse(false, SOURCE_IS))
                }
            }
            bookSource.enabled=source.enabled
            bookSourceService.booksSourceMapper.insert(bookSource)
            bookSourceService.cleancache()
        }

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
            val bookSource= userBookSourceService.getBookSource(id,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
            when(st){
                "0"->{
                    userBookSourceService.changeEnabled(bookSource.id!!,user.id,false)
                }
                "1"->{
                    userBookSourceService.changeEnabled(bookSource.id!!,user.id,true)
                }
                else -> throw DataThrowable().data(JsonResponse(false, USE_ERROE))
            }
        }else{
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
        }
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
                        userBookSourceService.getBookSource(it,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    userBookSourceService.changeEnabled(bookSource.id!!,user.id,false)
                }
            }
        }else{
            ids?.forEach {
                if (it.isNotBlank()){
                    bookSourceService.getBookSource(it) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    bookSourceService.changeEnabled(it,false)
                }
            }
        }
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
                        userBookSourceService.getBookSource(it,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    userBookSourceService.changeEnabled(bookSource.id!!,user.id,true)
                }
            }
        }else{
            ids?.forEach {
                if (it.isNotBlank()){
                    bookSourceService.getBookSource(it) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    bookSourceService.changeEnabled(it,true)
                }
            }
        }
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
                        userBookSourceService.getBookSource(it,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    userBookSourceService.changeenabledExplore(bookSource.id!!,user.id,false)
                }
            }
        }else{
            ids?.forEach {
                if (it.isNotBlank()){
                    bookSourceService.getBookSource(it) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    bookSourceService.changeenabledExplore(it,false)
                }
            }
        }
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
                        userBookSourceService.getBookSource(it,user.id!!) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    userBookSourceService.changeenabledExplore(bookSource.id!!,user.id,true)
                }
            }
        }else{
            ids?.forEach {
                if (it.isNotBlank()){
                    bookSourceService.getBookSource(it) ?: throw DataThrowable().data(JsonResponse(false, NOT_IS))
                    bookSourceService.changeenabledExplore(it,true)
                }
            }
        }
        JsonResponse(true)
    }


    @Mapping("/getbookSourcejson")
    fun getbookSourcejson(accessToken:String?,@Body ids: List<String>?)= run{
        val user=getsourceuser(accessToken)
        var s="["
        ids?.forEach {
            if (it.isNotBlank()){
                val bookSource=if(user.source == 2){
                    userBookSourceService.getBookSource(it,user.id!!)?.toBaseSource()
                }else{
                    bookSourceService.getBookSource(it)?.toBaseSource()
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
           userBookSourceService.getBookSource(bookSource.bookSourceUrl, userid = user.id!!).let {
               if (it != null){
                   source.enabled=it.enabled
                   if(it.createtime != null){
                       source.createtime=it.createtime
                   }
                   source.sourceorder=it.sourceorder
                   bookSource.lastUpdateTime=Date().time
                   update += userBookSourceService.userBookSourceMapper.updateById(source)
               }else{
                   source.enabled=true
                   source.sourceorder=9999
                   insert += userBookSourceService.userBookSourceMapper.insert(source)
               }
           }
           userBookSourceService.cleancache(user.id)
       }else{
           val source= web.model.BookSource().jsontomodel(bookSource)
           bookSourceService.getBookSource(bookSource.bookSourceUrl).let {
               if (it != null){
                   source.enabled=it.enabled
                   if(it.createtime != null){
                       source.createtime=it.createtime
                   }
                   source.sourceorder=it.sourceorder
                   bookSource.lastUpdateTime=Date().time
                   update += bookSourceService.booksSourceMapper.updateById(source)
               }else{
                   source.enabled=true
                   source.sourceorder=9999
                   insert += bookSourceService.booksSourceMapper.insert(source)
               }
           }
           bookSourceService.cleancache()
       }
        Pair(insert, update)
    }


}

class EditMsg{
    var json:String?=null
    var id:String?=null
}