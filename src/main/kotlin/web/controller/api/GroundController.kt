package web.controller.api

import book.appCtx
import book.util.FileUtils
import com.google.gson.Gson
import org.apache.ibatis.solon.annotation.Db
import org.noear.solon.annotation.Body
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.core.handle.UploadedFile
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.mapper.BackGroundMapper
import web.model.BackGround
import web.response.JsonResponse
import web.response.NOT_BANK
import web.util.hash.Md5


@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class GroundController:BaseController() {

    private val groundDir = FileUtils.createFolderIfNotExist(appCtx.externalFiles, "assets","ground")

    @Db("db")
    @Inject
    lateinit var backGroundMapper: BackGroundMapper

    @Mapping("/addground")
    fun addground(accessToken:String?, @Body ground: BackGround)=run{
        val user=getuserbytocken(accessToken)
        var needup=false
        if(ground.name == null || ground.name == 0.toLong())  throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        val newground=ground.create(user.id!!,ground.name!!)
        backGroundMapper.insertOrUpdate(newground)
        if(!ground.backgroundimage.isNullOrBlank()){
            if(!ground.backgroundimage!!.startsWith("assets")){
                if(!FileUtils.exists(groundDir,ground.backgroundimage!!)){
                    needup=true
                }
            }
        }
        JsonResponse(true,"$needup")
    }

    @Mapping("/delground")
    fun delground(accessToken:String?, @Body ground: BackGround)=run{
        val user=getuserbytocken(accessToken)
        if(ground.name == null || ground.name == 0.toLong())  throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        val newground=ground.create(user.id!!,ground.name!!)
        backGroundMapper.deleteById(newground)
        JsonResponse(true)
    }

    @Mapping("/getallground")
    fun getallground(accessToken:String?)=run{
        val user=getuserbytocken(accessToken)
        JsonResponse(true).Data(backGroundMapper.getlistbyuserid(user.id!!))
    }

    @Mapping("/importground")
    open fun importground(accessToken:String?, file: UploadedFile?)=run{
        if(file == null) throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        if (file.isEmpty) {
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        getuserbytocken(accessToken)
        val cb=file.contentAsBytes
        val valueFile = FileUtils.createFileIfNotExist(groundDir,file.name)
        valueFile.writeBytes(cb)
        JsonResponse(true)
    }

}