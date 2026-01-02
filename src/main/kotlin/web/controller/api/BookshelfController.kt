package web.controller.api

import book.model.SearchBook
import kotlinx.coroutines.runBlocking
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.data.annotation.Cache
import org.noear.solon.data.cache.CacheService
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.mapper.BookGroupMapper
import web.mapper.BooklistMapper
import web.model.Booklist
import web.response.JsonResponse
import web.response.NEED_LOGIN
import web.response.NOT_BANK
import web.util.hash.Md5
import web.util.mapper.mapper


@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class BookshelfController:BaseController() {

    @Inject
    lateinit var booklistMapper: BooklistMapper

    @Inject
    lateinit var cacheService: CacheService

    @Inject
    lateinit var bookGroupMapper: BookGroupMapper

    @Mapping("/getBookshelfPage")
    open fun getBookshelfPage(accessToken: String?) = run {
        val user = getuserbytocken(accessToken)
        if (user.bookmd5.isNullOrBlank()){
            user.bookmd5=Md5(System.currentTimeMillis().toString())
            usersMapper.updatebookmd5(user.id!!, user.bookmd5!!)
        }
        val book = booklistMapper.getbooklistbyuserid(user.id!!)
        var list=mutableListOf<Booklist>()
        var page = 1
        book?.forEach {
            if (it.customCoverUrl != null && it.customCoverUrl!!.isNotBlank()) {
                it.coverUrl = it.customCoverUrl
            }
            if (it.customIntro != null && it.customIntro!!.isNotBlank()) {
                it.intro = it.customIntro
            }
            if (it.customIntro != null && it.customIntro!!.isNotBlank()) {
                it.intro = it.customIntro
            }
            if (it.durChapterPos == null) {
                it.durChapterPos = 0.0
            }
            it.readchapter=""
            if(list.size < 50){
                list.add(it)
            }else{
                cacheService.store("getBookshelfNew:${accessToken}${user.bookmd5}${page}",JsonResponse(true).Data(list),cachetime)
                page++
                list=mutableListOf()
                list.add(it)
            }
        }
        cacheService.store("getBookshelfNew:${accessToken}${user.bookmd5}${page}",JsonResponse(true).Data(list),cachetime)
        val groups =bookGroupMapper.getGroupbyuserid(user.id!!)
        cacheService.store("bookgroup:${accessToken}${user.bookmd5}",JsonResponse(true).Data(groups),cachetime)
        JsonResponse(true).Data(mapOf("page" to page, "md5" to user.bookmd5))
    }

    @Cache(key = "getBookshelfNew:\${accessToken}\${md5}\${page}",  seconds = 60)
    @Mapping("/getBookshelfNew")
    open fun getBookshelf(accessToken: String?,md5: String?,page: String) = run {
        JsonResponse(false)
    }


    @Cache(key = "bookgroup:\${accessToken}\${md5}",  seconds = 60)
    @Mapping("/getgroupNew")
    open fun getgroupNew(accessToken: String?,md5: String?) = run {
        JsonResponse(false)
    }

    @Mapping("/addreadchapter")
    open fun addreadchapter( accessToken:String?,readchapter: String?,url: String?) = runBlocking{
        val user = getuserbytocken(accessToken)
        val book = booklistMapper.getbook(user.id!!, url?:throw DataThrowable().data(JsonResponse(false, NOT_BANK)))?:
        throw DataThrowable().data(JsonResponse(true))
        val so = (book.readchapter ?: "").split(",").toMutableSet()
        if (!readchapter.isNullOrBlank()){
            val sn = readchapter.replace("[","").replace("]","").split(",").toMutableSet()
            sn.forEach {
                if (it.isNotBlank()){
                    so.add(it)
                }
            }
            booklistMapper.updatereadchapter(
                book.id!!,so.joinToString(",")
            )
        }
        JsonResponse(true)
    }

}