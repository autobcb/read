package web.controller.api

import book.util.GSON
import book.util.fromJsonArray
import org.noear.solon.annotation.Body
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.Tran
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.mapper.BookGroupMapper
import web.mapper.BooklistMapper
import web.model.BookGroup
import web.notification.Book
import web.response.GROUPIS
import web.response.GROUP_NOT_EDIT
import web.response.JsonResponse
import web.response.NOT_BANK
import web.response.NOT_IS
import web.response.TOO_MANY_GROUPS

@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class BookGroupController:BaseController() {
    @Inject
    lateinit var bookGroupMapper: BookGroupMapper

    @Inject
    lateinit var booklistMapper: BooklistMapper

    companion object{
        val ygroup=listOf<String>("未分组","有声书","漫画")
    }


    @Mapping("/getgroup")
    open fun getgroup( accessToken:String?)=run{
        val user=getuserbytocken(accessToken)
        JsonResponse(true).Data(bookGroupMapper.getGroupbyuserid(user.id!!))
    }

    @Tran
    @Mapping("/addgroup")
    open fun addgroup( accessToken:String?,name:String?)=run{
        val user=getuserbytocken(accessToken)
        if(name == null){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        bookGroupMapper.getGroupbyuserid(user.id!!).let {
            if (it.size > 20){
                throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = TOO_MANY_GROUPS))
            }
        }

        if(name == "全部"  || bookGroupMapper.getGroupbyName(user.id!!,name) != null) {
            throw DataThrowable().data(JsonResponse(isSuccess = false, errorMsg = GROUPIS))
        }
        val groups=bookGroupMapper.getGroupbyuserid(user.id!!)
        var max=0
        groups.forEach {
            if (it.grouporder != null && it.grouporder!! > max){
                max=it.grouporder!!
            }
        }
        bookGroupMapper.insert(BookGroup().create(user.id!!,name).also {
            it.grouporder=max+1
        })
        Book.sendNotification(user)
        JsonResponse(true)
    }

    @Tran
    @Mapping("/delgroup")
    open fun delgroup( accessToken:String?,name:String?)=run{
        val user=getuserbytocken(accessToken)
        if(name == null){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val group=bookGroupMapper.getGroupbyName(user.id!!,name).also {
            if(it == null){
                throw DataThrowable().data(JsonResponse(false, NOT_IS))
            }
        }!!
        bookGroupMapper.deleteById(group.id!!)
        booklistMapper.delbookgroup(user.id!!, group.bookgroup!!)
        Book.sendNotification(user)
        JsonResponse(true)
    }

    @Tran
    @Mapping("/editgroup")
    open fun editgroup( accessToken:String?,oldname:String?,newname:String?)=run{
        val user=getuserbytocken(accessToken)
        if(oldname == null || newname == null){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        if (ygroup.contains(oldname)) {
            throw DataThrowable().data(JsonResponse(false, GROUP_NOT_EDIT))
        }
        val group=bookGroupMapper.getGroupbyName(user.id!!,oldname).also {
            if(it == null){
                throw DataThrowable().data(JsonResponse(false, NOT_IS))
            }
        }!!
        group.bookgroup=newname
        bookGroupMapper.updateById(group)
        booklistMapper.upbookgroup(user.id!!,oldname,newname)
        Book.sendNotification(user)
        JsonResponse(true)
    }

    @Tran
    @Mapping("/setgroup")
    open fun setgroup( accessToken:String?,name:String?, url: String?)=run{
        val user=getuserbytocken(accessToken)
        if(url == null){
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        if (ygroup.contains(name)) {
            throw DataThrowable().data(JsonResponse(false, GROUP_NOT_EDIT))
        }
        if(name != null && name != "全部"){
            bookGroupMapper.getGroupbyName(user.id!!,name).also {
                if(it == null){
                    throw DataThrowable().data(JsonResponse(false, NOT_IS))
                }
            }!!
        }
        val book=booklistMapper.getbook(user.id!!,url).also {
            if(it == null){
                throw DataThrowable().data(JsonResponse(false, NOT_IS))
            }
        }!!
        booklistMapper.changebookgroup(book.id!!,name?:"")
        Book.sendNotification(user)
        JsonResponse(true)
    }


    @Tran
    @Mapping("/setgroups")
    open fun setgroups( accessToken:String?,name:String?, @Body ids: List<String>?)=run{
        val user=getuserbytocken(accessToken)
        if (ids.isNullOrEmpty()){
            throw DataThrowable().data(JsonResponse(false,NOT_BANK))
        }
        if (ygroup.contains(name)) {
            throw DataThrowable().data(JsonResponse(false, GROUP_NOT_EDIT))
        }
        if(name != null && name != "全部"){
            bookGroupMapper.getGroupbyName(user.id!!,name).also {
                if(it == null){
                    throw DataThrowable().data(JsonResponse(false, NOT_IS))
                }
            }!!
        }
        for (url in ids){
            val book=booklistMapper.getbook(user.id!!,url)
            if(book == null){
                continue;
            }
            booklistMapper.changebookgroup(book.id!!,name?:"")
        }

        Book.sendNotification(user)
        JsonResponse(true)
    }

    @Mapping("/ordergroup")
    open fun ordergroup( accessToken:String?,groups:String)=run{
        val user=getuserbytocken(accessToken)
        val mgroups=bookGroupMapper.getGroupbyuserid(user.id!!)
        var o=0;
        GSON.fromJsonArray<String>(groups).getOrNull()?.forEach {
            if(it != "全部"){
                for(  g in mgroups){
                    if(g.bookgroup == it){
                        g.grouporder=o
                        bookGroupMapper.updateById(g)
                        break;
                    }
                }
                o++
            }
        }
        Book.sendNotification(user)
        JsonResponse(true)
    }
}