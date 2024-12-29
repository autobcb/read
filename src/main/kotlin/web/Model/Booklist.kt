package web.Model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.dromara.autotable.annotation.ColumnType
import org.dromara.mpe.autotable.annotation.ColumnId
import org.dromara.mpe.autotable.annotation.Table
import java.util.*
import io.legado.app.data.entities.Book
@Table(dsName = "booklist")
class Booklist {
    @ColumnId
    @JsonIgnore
    var id : String =""
    var userid : String =""                    //用户id
    @ColumnType(value = "MEDIUMTEXT")
    var bookUrl: String = ""                   // 详情页Url(本地书源存储完整文件路径)
    @ColumnType(value = "MEDIUMTEXT")
    var tocUrl: String = ""                    // 目录页Url (toc=table of Contents)
    var origin: String = ""       // 书源URL(默认BookType.local)
    var originName: String = ""                //书源名称
    var originOrder: Int = 0
    var name: String = ""                   // 书籍名称(书源获取)
    var author: String = ""                 // 作者名称(书源获取)
    var kind: String? = null                    // 分类信息(书源获取)
    @JsonIgnore
    var customTag: String? = null              // 分类信息(用户修改)
    @ColumnType(value = "MEDIUMTEXT")
    var coverUrl: String? = null               // 封面Url(书源获取)
    @JsonIgnore
    @ColumnType(value = "MEDIUMTEXT")
    var customCoverUrl: String? = null         // 封面Url(用户修改)
    @ColumnType(value = "LONGTEXT")
    var intro: String? = null          // 简介内容(书源获取)
    @JsonIgnore
    @ColumnType(value = "LONGTEXT")
    var customIntro: String? = null      // 简介内容(用户修改)
    var charset: String? = null                // 自定义字符集名称(仅适用于本地书籍)
    @JsonIgnore
    var type: Int = 0                          // @BookType
    //var group: Int = 0                         // 自定义分组索引号
    var latestChapterTitle: String? = null     // 最新章节标题
    var latestChapterTime: Long = System.currentTimeMillis()            // 最新章节标题更新时间
    var lastCheckTime: Long = System.currentTimeMillis()               // 最近一次更新书籍信息的时间
    var lastCheckCount: Int = 0                // 最近一次发现新章节的数量
    var totalChapterNum: Int = 0               // 书籍目录总数
    var durChapterTitle: String? = null       // 当前章节名称
    var durChapterIndex: Int = 0            // 当前章节索引
    var durChapterPos: Double = 0.0                 // 当前阅读的进度(首行字符的索引位置)
    var durChapterTime: Long = System.currentTimeMillis()               // 最近一次阅读书籍的时间(打开正文的时间)
    var wordCount: String? = null
    @JsonIgnore
    @ColumnType(value = "LONGTEXT")
    var readchapter: String? = null
    var useReplaceRule: Boolean = true         // 正文使用净化替换规则
    //var variable: String? = null                // 自定义书籍变量信息(用于书源规则检索书籍信息)

    fun create():Booklist{
        this.id = UUID.randomUUID().toString()
        this.latestChapterTime = System.currentTimeMillis()
        this.lastCheckTime = System.currentTimeMillis()
        this.durChapterTime = System.currentTimeMillis()
        return this
    }

    fun bookto(book:Book):Booklist{
        this.bookUrl = book.bookUrl
        if(book.tocUrl.isNotBlank()) this.tocUrl = book.tocUrl
        this.origin = book.origin
        this.originName = book.originName
        this.originOrder = book.originOrder
        if(book.name.isNotBlank())  this.name = book.name
        if(book.author.isNotBlank()) this.author = book.author
        if((book.kind?:"").isNotBlank()) this.kind = book.kind
        if((book.customTag?:"").isNotBlank())  this.customTag = book.customTag
        if((book.customCoverUrl?:"").isNotBlank()) this.customCoverUrl = book.customCoverUrl
        if((book.coverUrl?:"").isNotBlank()) this.coverUrl = book.coverUrl
        if((book.intro?:"").isNotBlank()) this.intro = book.intro
        if((book.customIntro?:"").isNotBlank()) this.customIntro = book.customIntro
        if((book.charset?:"").isNotBlank()) this.charset = book.charset
        this.type = book.type
        if((book.wordCount?:"").isNotBlank()) this.wordCount = book.wordCount
        return this
    }

    fun tobook():Book{
        val book = Book()
        book.bookUrl = this.bookUrl
        book.tocUrl = this.tocUrl
        book.origin = this.origin
        book.originName = this.originName
        book.originOrder = this.originOrder
        book.name = this.name
        book.author = this.author
        book.kind = this.kind
        book.customTag = this.customTag
        book.customCoverUrl = this.customCoverUrl
        book.coverUrl = this.coverUrl
        book.intro = this.intro
        book.customIntro = this.customIntro
        book.charset = this.charset
        book.type = this.type
        book.lastCheckCount = this.lastCheckCount
        book.lastCheckTime = this.lastCheckTime
        book.totalChapterNum = this.totalChapterNum
        book.durChapterTitle = this.durChapterTitle
        book.durChapterIndex = this.durChapterIndex
        book.durChapterTime = this.durChapterTime
        book.wordCount = this.wordCount
        book.useReplaceRule = this.useReplaceRule
        return book
    }

}