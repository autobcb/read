package web.Util.model


import io.legado.app.data.entities.rule.*


class Booksource {
    var bookSourceUrl: String = ""           // 地址，包括 http/https
    var bookSourceName: String = ""           // 名称
    var bookSourceGroup: String? = null       // 分组
    var bookSourceType: Int = 0               // 类型，0 文本，1 音频
    var bookUrlPattern: String? = null       //详情页url正则
    var customOrder: Int = 0                 // 手动排序编号
    var enabled: Boolean = true            // 是否启用
    var enabledExplore: Boolean = true     //启用发现
    var enabledCookieJar:Boolean = false
    var concurrentRate: String? = null    //并发率
    var header: String? = null
    var loginUrl: String? = null             // 登录地址
    var loginCheckJs: String? = null           // 登录检测js
    var bookSourceComment: String? = null           // 注释
    var lastUpdateTime: Long = 0             // 最后更新时间，用于排序
    var respondTime: Long = 180000L               // 响应时间，用于排序
    var weight: Int = 0                      // 智能排序的权重
    var exploreUrl: String? = null                 // 发现url
    var ruleExplore: ExploreRule? = null           // 发现规则
    var searchUrl: String? = null                  // 搜索url
    var ruleSearch: SearchRule? = null            // 搜索规则
    var ruleBookInfo: BookInfoRule? = null         // 书籍信息页规则\
    var ruleToc: TocRule? = null                  // 目录页规则
    var ruleContent: ContentRule? = null            // 正文页规则
    var loginJs:String? =null                   //登录规则
    var tag: String? = null
    var searchRule: SearchRule? = null
    var exploreRule: ExploreRule? = null
    var bookInfoRule: BookInfoRule? = null
    var tocRule: TocRule? = null
    var contentRule: ContentRule? = null
    var jsLib:String?=null
}