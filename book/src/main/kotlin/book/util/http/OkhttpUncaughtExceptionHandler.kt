package book.util.http



object OkhttpUncaughtExceptionHandler : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(t: Thread, e: Throwable) {
        println("Okhttp Dispatcher中的线程执行出错\n${e.localizedMessage}" )
    }

}
