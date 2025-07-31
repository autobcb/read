package web.util


import book.util.GSON
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BookChapter(
    var url: String = "",               // 章节地址
    var title: String = "",              // 章节标题
    var isVolume: Boolean = false,      // 是否是卷名
    var baseUrl: String = "",           //用来拼接相对url
    var bookUrl: String = "",           // 书籍地址
    var index: Int = 0,                 // 章节序号
    var resourceUrl: String? = null,    // 音频真实URL
    var wordCount: String? = null,      // 本章节字数
    var isPay: Boolean = false,         // 是否已购买
    var isVip: Boolean = false,         // 是否VIP
    var tag: String? = null,            //
    var start: Long? = null,            // 章节起始位置
    var end: Long? = null,               // 章节终止位置
    var startFragmentId: String? = null,  //EPUB书籍当前章节的fragmentId
    var endFragmentId: String? = null,    //EPUB书籍下一章节的fragmentId
    var lastCheckTime: Long? = null,
    var userid: String = ""
) {

    companion object {
        fun  toBookChapterList(json: String):List<book.model.BookChapter>? {
            runCatching {
                val type = object : TypeToken<List<BookChapter>?>() {}.type
                val l:List<BookChapter> = GSON.fromJson(json,type)
                val list: MutableList<book.model.BookChapter> = mutableListOf()
                for(element in l){
                    list.add(element.toBookChapter())
                }
                return list
            }
            return null;
        }

        fun  toBookChapterJson(l:List<book.model.BookChapter>): String{
            runCatching {
                val list: MutableList<BookChapter> = mutableListOf()
                for(element in l){
                    list.add(BookChapter(
                        url=element.url,
                        title=element.title,
                        isVolume=element.isVolume,
                        baseUrl=element.baseUrl,
                        bookUrl=element.bookUrl,
                        index=element.index,
                        resourceUrl=element.resourceUrl,
                        wordCount=element.wordCount,
                        isPay=element.isPay,
                        isVip=element.isVip,
                        tag=element.tag,
                        start=element.start,
                        end=element.end,
                        startFragmentId=element.startFragmentId,
                        endFragmentId=element.endFragmentId,
                        lastCheckTime=element.lastCheckTime,
                        userid=element.userid
                    ))
                }
                return Gson().toJson(list)
            }
            return "[]"
        }
    }

    fun toBookChapter(): book.model.BookChapter {
        return  book.model.BookChapter (
            url=this.url,
            title=this.title,
            isVolume=this.isVolume,
            baseUrl=this.baseUrl,
            bookUrl=this.bookUrl,
            index=this.index,
            resourceUrl=this.resourceUrl,
            wordCount=this.wordCount,
            isPay=this.isPay,
            isVip=this.isVip,
            tag=this.tag,
            start=this.start,
            end=this.end,
            startFragmentId=this.startFragmentId,
            endFragmentId=this.endFragmentId,
            lastCheckTime=this.lastCheckTime,
            userid=this.userid
        )
    }
}