package book.util


import java.io.File
import java.io.FileOutputStream



fun File.createFileIfNotExist(): File {
    if (!exists()) {
        parentFile?.createFolderIfNotExist()
        createNewFile()
    }
    return this
}

fun File.createFileReplace(): File {
    if (!exists()) {
        parent?.let {
            File(it).mkdirs()
        }
        createNewFile()
    } else {
        delete()
        createNewFile()
    }
    return this
}

fun File.createFolderIfNotExist(): File {
    if (!exists()) {
        mkdirs()
    }
    return this
}

fun File.createFolderReplace(): File {
    if (exists()) {
        FileUtils.delete(this, true)
    }
    mkdirs()
    return this
}

fun File.checkWrite(): Boolean {
    var file: File? = null
    return try {
        val filename = System.currentTimeMillis().toString()
        file = FileUtils.createFileIfNotExist(this, filename)
        file.outputStream().bufferedWriter().use { it.write(filename) }
        file.inputStream().bufferedReader().use { it.readText() == filename }
    } catch (e: Exception) {
        false
    } finally {
        file?.delete()
    }
}

fun File.outputStream(append: Boolean = false): FileOutputStream {
    return FileOutputStream(this, append)
}
