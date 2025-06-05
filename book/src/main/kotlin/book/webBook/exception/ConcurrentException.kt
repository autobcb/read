package book.webBook.exception

class ConcurrentException(msg: String, val waitTime: Int) : NoStackTraceException(msg)