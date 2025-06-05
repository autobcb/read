package web.model

import java.time.LocalDateTime

class BaseRssSource(
    var sourceUrl: String = "",
    var sourceName: String = "",
    var sourceIcon: String = "",
    var sourceorder: Int? = null,
    var sourceGroup: String? = null,
    var sourceComment: String? = null,
    var enabled: Boolean? = null,
    var json: String? = null,
    var createtime: LocalDateTime? = null
)