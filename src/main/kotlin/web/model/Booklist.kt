package web.model

import book.model.Book
import book.model.BookSource
import book.model.SearchBook
import com.baomidou.mybatisplus.annotation.TableId
import com.google.gson.Gson
import org.dromara.autotable.annotation.*
import java.util.*
import web.util.hash.Md5
import kotlin.math.PI

@AutoTable(value = "booklist")
class Booklist {

    @TableId
    @PrimaryKey
    var id : String? = null
    var userid : String? = null                    //用户id
    @ColumnType(value = "MEDIUMTEXT")
    var bookUrl: String? = null                   // 详情页Url(本地书源存储完整文件路径)
    @ColumnType(value = "MEDIUMTEXT")
    var tocUrl: String? = null                    // 目录页Url (toc=table of Contents)
    var origin: String? = null       // 书源URL(默认BookType.local)
    var originName: String? = null                //书源名称
    var originOrder: Int? = null
    var name: String? = null                   // 书籍名称(书源获取)
    var author: String? = null                 // 作者名称(书源获取)
    var kind: String? = null                    // 分类信息(书源获取)
    var customTag: String? = null              // 分类信息(用户修改)
    @ColumnType(value = "MEDIUMTEXT")
    var coverUrl: String? = null               // 封面Url(书源获取)
    @ColumnType(value = "MEDIUMTEXT")
    var customCoverUrl: String? = null         // 封面Url(用户修改)
    @ColumnType(value = "LONGTEXT")
    var intro: String? = null          // 简介内容(书源获取)
    @ColumnType(value = "LONGTEXT")
    var customIntro: String? = null      // 简介内容(用户修改)
    var charset: String? = null                // 自定义字符集名称(仅适用于本地书籍)
    var type: Int? = null                       // @BookType
        set(value) {
            when(value){
                32,1 ->{
                    field=1
                }
                64,2 ->{
                    field=2
                }
                else ->{
                    field=0
                }
            }
        }
    var bookgroup: String? = null                  // 自定义分组索引号
    var latestChapterTitle: String? = null     // 最新章节标题
    var latestChapterTime: Long? = null            // 最新章节标题更新时间
    var lastCheckTime: Long? = null               // 最近一次更新书籍信息的时间
    var lastCheckCount: Int? = null                // 最近一次发现新章节的数量
    var totalChapterNum: Int? = null               // 书籍目录总数
    var durChapterTitle: String? = null       // 当前章节名称
    var durChapterIndex: Int? = null       // 当前章节索引
    var durChapterPos: Double? = null                 // 当前阅读的进度(首行字符的索引位置)
    var durChapterTime: Long? = null               // 最近一次阅读书籍的时间(打开正文的时间)
    var wordCount: String? = null
    @ColumnType(value = "LONGTEXT")
    var readchapter: String? = null
    var useReplaceRule: Boolean? = null         // 正文使用净化替换规则
    var imageDecode: Boolean = false
    var downloadUrls: String? = null

    fun create():Booklist{
        this.id = UUID.randomUUID().toString()
        this.latestChapterTime = System.currentTimeMillis()
        this.lastCheckTime = System.currentTimeMillis()
        this.durChapterTime = System.currentTimeMillis()
        return this
    }

    fun bookto(book: Book,canchangename:Boolean =true ,canchangeindex:Boolean =false):Booklist{
        this.tocUrl=""
        this.bookUrl = book.bookUrl
        if(book.tocUrl.isNotBlank()) this.tocUrl = book.tocUrl
        this.origin = book.origin
        this.type = book.type
        this.originName = book.originName
        this.originOrder = book.originOrder
        if(book.name.isNotBlank() && canchangename)  this.name = book.name
        if(book.author.isNotBlank() ) this.author = book.author
        if((book.kind?:"").isNotBlank()) this.kind = book.kind
        if((book.customTag?:"").isNotBlank())  this.customTag = book.customTag
        if((book.customCoverUrl?:"").isNotBlank()) this.customCoverUrl = book.customCoverUrl
        if((book.coverUrl?:"").isNotBlank()) this.coverUrl = book.coverUrl
        if((book.intro?:"").isNotBlank()) this.intro = book.intro
        if((book.customIntro?:"").isNotBlank()) this.customIntro = book.customIntro
        if((book.charset?:"").isNotBlank()) this.charset = book.charset
        if(canchangename) this.type = book.type
        if((book.wordCount?:"").isNotBlank()) this.wordCount = book.wordCount
        if(canchangeindex) this.durChapterIndex = book.durChapterIndex
        if(canchangeindex) this.durChapterTime = book.durChapterTime
        if(canchangeindex) this.durChapterTitle = book.durChapterTitle
        if(!book.downloadUrls.isNullOrEmpty()) {
            this.downloadUrls = Gson().toJson(book.downloadUrls)
        }
        return this
    }

    fun needimageDecode(source: BookSource?){
        if(source != null && source.hasimageDecode() ){
            this.imageDecode=true
        }else{
            this.imageDecode=false
        }
    }
   companion object{
       fun tobooklist(book: SearchBook, id:String):Booklist{
           val bookList = Booklist().create()
           bookList.bookUrl = book.bookUrl
           bookList.origin = book.origin
           bookList.originName = book.originName
           bookList.originOrder = book.originOrder
           bookList.type = book.type
           bookList.name = book.name
           bookList.author = book.author
           bookList.kind = book.kind
           bookList.coverUrl = book.coverUrl
           bookList.intro=book.intro
           bookList.wordCount = book.wordCount
           bookList.latestChapterTitle = book.latestChapterTitle
           bookList.tocUrl = book.tocUrl
           bookList.id= Md5(id+book.bookUrl)
           bookList.userid=id
           if(!book.downloadUrls.isNullOrEmpty()) bookList.downloadUrls = book.downloadUrls
           return bookList
       }



       fun tobooklist(book: Book, id:String):Booklist{
           val bookList = Booklist().create()
           bookList.bookUrl = book.bookUrl
           bookList.origin = book.origin
           bookList.originName = book.originName
           bookList.originOrder = book.originOrder
           bookList.type = book.type
           bookList.name = book.name
           bookList.author = book.author
           bookList.kind = book.kind
           bookList.coverUrl = book.coverUrl
           bookList.intro=book.intro
           bookList.wordCount = book.wordCount
           bookList.latestChapterTitle = book.latestChapterTitle
           bookList.tocUrl = book.tocUrl
           bookList.id= Md5(id+book.bookUrl)
           bookList.userid=id
           if(!book.downloadUrls.isNullOrEmpty()) {
               bookList.downloadUrls = Gson().toJson(book.downloadUrls)
           }
           return bookList
       }
   }


}