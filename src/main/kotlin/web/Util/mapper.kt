package web.Util

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import web.Mapper.BookSourceMapper
import web.Mapper.BooklistMapper
import web.Mapper.CookieMapper

@Component
class mapper {
    @Autowired
    lateinit var booklistMapper: BooklistMapper

    @Autowired
    lateinit var bookSourcemapper: BookSourceMapper

    @Autowired
    lateinit var cookiemapper: CookieMapper

    companion object{
        var mapper:mapper?=null
        fun get():mapper{
            return mapper!!
        }
    }


    @PostConstruct
    fun init(){
        mapper =this
    }

}