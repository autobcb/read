package web.controller.api

import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Inject
import org.noear.solon.annotation.Mapping
import org.noear.solon.core.util.DataThrowable
import org.noear.solon.web.cors.annotation.CrossOrigin
import web.mapper.ItemMapper
import web.model.Item
import web.response.JsonResponse
import web.response.NOT_BANK

@Controller
@Mapping(routepath)
@CrossOrigin(origins = "*")
open class ItemController: BaseController() {

    @Inject
    lateinit var itemMapper: ItemMapper



    @Mapping("/getitem")
    fun  getitem(accessToken:String?, name:String?) = run{
        if (name.isNullOrBlank()) {
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val user=getuserbytocken(accessToken)
        val item = itemMapper.getbyname(user.id!!,name)
        JsonResponse(true).Data(item?.value)
    }

    @Mapping("/setitem")
    fun  setitem(accessToken:String?, name:String?, value:String?) = run{
        if (name.isNullOrBlank() || value == null) {
            throw DataThrowable().data(JsonResponse(false, NOT_BANK))
        }
        val user=getuserbytocken(accessToken)
        val item = Item().create(user.id!!,name)
        item.value = value
        itemMapper.insertOrUpdate(item)
        JsonResponse(true)
    }
}