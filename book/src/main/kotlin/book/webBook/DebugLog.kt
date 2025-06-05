package book.webBook

import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val logger: Logger = LoggerFactory.getLogger(DebugLog::class.java)

interface DebugLog: HttpLoggingInterceptor.Logger {
    fun log(
        sourceUrl: String? = "",
        msg: String? = "",
        isHtml: Boolean = false
    ) {
        logger.info("sourceUrl: {}, msg: {}", sourceUrl, msg)
    }

    override fun log(message: String) {
        logger.debug(message)
    }
}