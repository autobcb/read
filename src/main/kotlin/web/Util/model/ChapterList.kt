package web.Util.model

import io.legado.app.data.entities.Book
import io.legado.app.data.entities.BookChapter
import web.Model.BookSource

class ChapterList(
    var book: Book? = null,
    var bookChapters: List<BookChapter> = listOf(),
    var source: BookSource? = null,
    var addtime:Long =System.currentTimeMillis()
)