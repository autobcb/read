package web.Util.model

import web.Model.Booklist
import  web.Util.hex.MD5

class SearchBook {
    var bookUrl: String = ""
    var origin: String = ""                     // 书源规则
    var originName: String = ""
    var originOrder: Int = 0
    var type: Int = 0                          // @BookType
     var name: String = ""
     var author: String = ""
     var kind: String? = null
    var coverUrl: String? = null
    var intro: String? = null
     var wordCount: String? = null
    var latestChapterTitle: String? = null
    var tocUrl: String = ""                    // 目录页Url (toc=table of Contents)
    var time: Long = 0

    fun tobooklist(id:String):Booklist{
        var bookList = Booklist().create()
        bookList.bookUrl = this.bookUrl
        bookList.origin = this.origin
        bookList.originName = this.originName
        bookList.originOrder = this.originOrder
        bookList.type = this.type
        bookList.name = this.name
        bookList.author = this.author
        bookList.kind = this.kind
        bookList.coverUrl = this.coverUrl
        bookList.intro=this.intro
        bookList.wordCount = this.wordCount
        bookList.latestChapterTitle = this.latestChapterTitle
        bookList.tocUrl = this.tocUrl
        bookList.id= MD5(id+this.bookUrl)
        bookList.userid=id
        return bookList
    }
}