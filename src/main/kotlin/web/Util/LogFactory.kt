package web.Util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class LogFactory {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)
}