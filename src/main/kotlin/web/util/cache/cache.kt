package web.util.cache



import book.util.FileUtils
import java.io.File

const val cachepath="cache"

private const val bookcachepath="bookcache"

private  const val localpath="local"

private const val cookiepath="cookie"

fun getlocalpath(name: String): String {
    return "$localpath/$name"
}




fun checkfile(){
    checkfile2(cachepath)
    checkfile2(cookiepath)
    checkfile2(bookcachepath)

    checkfile(localpath)
}

fun checkfile2(path:String){
    val file = File(path)
    if (file.exists()){
        FileUtils.delete(file, true)
    }
}

fun checkfile(path:String){
    val file = File(path)
    if (!file.exists()){
        file.mkdirs()
    }else{
        if (!file.isDirectory){
            file.delete()
            val file1 = File(path)
            file1.mkdirs()
        }
    }
}

