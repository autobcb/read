package web.util.page

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import web.response.PageResponse

fun <T> Page(mapper: BaseMapper<T>,queryWrapper: QueryWrapper<T>,  _page:Int, _limit:Int, _order:String?):Triple<Long,Int,List<T>>  {
    val page=_page.let { if (_page<1 ) 0 else _page  }
    val limit=_limit.let { if (_limit <= 0 ) 20 else _limit  }

    val count=mapper.selectCount(queryWrapper)
    if (count == 0.toLong()){
        return Triple(0,page,emptyList())
    }

    if (_order != null && _order.isNotBlank()){
        val order=_order.trim().split(" ")
        if (order.size > 2){
            throw PageException("排序错误")
        }
        if (order.size == 1 || !order[1].equals("DESC",true)){
            queryWrapper.orderByAsc(order[0])
        }else{
            queryWrapper.orderByDesc(order[0])
        }
    }
    val rowBegin = (page - 1) * limit
    if (rowBegin > 0){
        queryWrapper.last("LIMIT $limit offset $rowBegin")
    }else{
        queryWrapper.last("LIMIT $limit")
    }

    val list=mapper.selectList(queryWrapper)
    return Triple(count,page,list)
}

fun <T> PageByAjax(mapper: BaseMapper<T>,queryWrapper: QueryWrapper<T>,  _page:Int, _limit:Int, _order:String?):PageResponse{
    val (count,page,data) = Page(mapper, queryWrapper, _page, _limit, _order)
    return PageResponse(count,page,data)
}