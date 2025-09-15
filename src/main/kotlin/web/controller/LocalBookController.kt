package web.controller

import book.model.Book
import book.webBook.localBook.LocalBook
import org.noear.solon.annotation.Controller
import org.noear.solon.annotation.Mapping
import org.noear.solon.core.handle.Context
import org.noear.solon.core.util.DataThrowable
import web.response.JsonResponse
import web.response.NOT_TXT
import java.io.File

@Controller
class LocalBookController {

    @Mapping("/searchlocalbook")
    fun search(ctx: Context, path: String?,key:String?): JsonResponse = run{
        if(ctx.realIp() != "127.0.0.1"){
            throw DataThrowable().data(JsonResponse(false, "非发请求"))
        }
        if(key.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, "关键字不能为空"))
        }
        if(path.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, "路径不能为空"))
        }
        val file= File(path)
        if(!file.exists()){
            throw DataThrowable().data(JsonResponse(false, "文件夹不存在"))
        }
        if(!file.isDirectory){
            throw DataThrowable().data(JsonResponse(false, "路径不是文件夹"))
        }
        val books = mutableListOf<Book>()
        getname(file).forEach { k,v->
             if(k.contains(key)){
                 if(books.size < 10){
                     val book = Book.initLocalBook(v, v, "")
                     books.add(book)
                 }
             }
        }
        JsonResponse(true).Data(books)
    }

    @Mapping("/getlocalbookinfo")
    fun info(ctx: Context,path: String?): JsonResponse = run{
        if(ctx.realIp() != "127.0.0.1"){
            throw DataThrowable().data(JsonResponse(false, "非发请求"))
        }
        if(path.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, "路径不能为空"))
        }
        val file= File(path)
        if(!file.exists()){
            throw DataThrowable().data(JsonResponse(false, "文件夹不存在"))
        }
        if(!file.name.endsWith(".txt") && !file.name.endsWith(".epub")){
            throw DataThrowable().data(JsonResponse(false,NOT_TXT))
        }
        val book = Book.initLocalBook(file.path, file.path, "")
        JsonResponse(true).Data(book)
    }

    @Mapping("/getlocalbookinfoChapterList")
    fun getChapterList(ctx: Context,path: String?) = run{
        if(ctx.realIp() != "127.0.0.1"){
            throw DataThrowable().data(JsonResponse(false, "非发请求"))
        }
        if(path.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, "路径不能为空"))
        }
        val file= File(path)
        if(!file.exists()){
            throw DataThrowable().data(JsonResponse(false, "文件夹不存在"))
        }
        if(!file.name.endsWith(".txt") && !file.name.endsWith(".epub")){
            throw DataThrowable().data(JsonResponse(false,NOT_TXT))
        }
        val book = Book.initLocalBook(file.path, file.path, "")
        val chapters = LocalBook.getChapterList(book)
        JsonResponse(true).Data(chapters)
    }

    @Mapping("/getlocalbookinfoContent")
    fun getContent(ctx: Context,path: String?,index: Int?) = run{
        if(ctx.realIp() != "127.0.0.1"){
            throw DataThrowable().data(JsonResponse(false, "非发请求"))
        }
        if(path.isNullOrBlank()){
            throw DataThrowable().data(JsonResponse(false, "路径不能为空"))
        }
        val file= File(path)
        if(!file.exists()){
            throw DataThrowable().data(JsonResponse(false, "文件夹不存在"))
        }
        if(!file.name.endsWith(".txt") && !file.name.endsWith(".epub")){
            throw DataThrowable().data(JsonResponse(false,NOT_TXT))
        }
        val book = Book.initLocalBook(file.path, file.path, "")
        val chapters = LocalBook.getChapterList(book)
        if((index ?: 0) > chapters.size - 1){
            throw DataThrowable().data(JsonResponse(false, "index 错误"))
        }
        val content = LocalBook.getContent(book,chapters[index?:0])
        JsonResponse(true).Data(content)
    }


    fun getname(file: File): MutableMap<String,String> {
        val books=mutableMapOf<String, String>()
        file.walk().maxDepth(1).forEach {
            if(it.isDirectory && it.name != file.name){
                books.putAll(getname(it))
            }else{
                if(it.name.endsWith(".txt") || it.name.endsWith(".epub")){
                    books[it.nameWithoutExtension] = it.path
                }
            }
        }
        return books
    }

}