package book

import book.util.getWorkDir
import java.io.File
import java.nio.file.Files

object appCtx {
    val cacheDir: String by lazy {
        getWorkDir("storage", "cache")
    }

    val  externalFiles= File("storage").also {
        if (!it.exists()) {
            it.mkdirs()
        }
    }
}
