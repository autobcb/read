package web.model


data  class BaseSource (
    var bookSourceUrl: String     ,       // 地址，包括 http/https
    var bookSourceName: String? = null  ,         // 名称
    var bookSourceGroup: String? = null,
    var sourceorder: Int? = null,
    var bookSourceType: Int? = null   ,      // 类型，0 文本，1 音频
    var enabled: Boolean = false ,        // 是否启用
   var enabledExplore: Boolean? = null  ,   //启用发现
    var json: String ,
){}