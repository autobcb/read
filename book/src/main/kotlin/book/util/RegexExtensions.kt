package book.util

import book.webBook.exception.RegexTimeoutException
import com.script.ScriptBindings
import com.script.rhino.RhinoScriptEngine
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.lang.Thread.sleep
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


/**
 * 带有超时检测的正则替换
 */
fun CharSequence.replace(regex: String, replacement: String, timeout: Long): String {
    val charSequence = this@replace
    val isJs = replacement.startsWith("@js:")
    val replacement1 = if (isJs) replacement.substring(4) else replacement
    return runBlocking {
        try {
            val pattern = Pattern.compile(
                regex,
                Pattern.UNICODE_CHARACTER_CLASS or Pattern.MULTILINE
            )
            val matcher = pattern.matcher(charSequence)
            val stringBuffer = StringBuffer()
            // 使用 withTimeout 替代手动线程管理
            withTimeout(timeout) {
                while (matcher.find() && isActive) {
                    ensureActive()
                    if (isJs) {
                        val jsResult = withContext(Dispatchers.IO) {
                            val executor = Executors.newSingleThreadExecutor()
                            val future = executor.submit<String> {
                                RhinoScriptEngine.run {
                                    val bindings = ScriptBindings()
                                    bindings["result"] = matcher.group()
                                    eval(replacement1, bindings).toString()
                                }
                            }
                            try {
                                withTimeout(timeout) {
                                    future.get(timeout, TimeUnit.MILLISECONDS)
                                }
                            } catch (e: TimeoutException) {
                                future.cancel(true)  // 强制中断线程‌:ml-citation{ref="6" data="citationList"}
                                executor.shutdownNow()
                                throw RegexTimeoutException("JS脚本执行超时")
                            }
                        }
                        val quotedResult = Matcher.quoteReplacement(jsResult)
                        matcher.appendReplacement(stringBuffer, quotedResult)
                    } else {
                        matcher.appendReplacement(stringBuffer, replacement1)
                    }
                }
            }
            matcher.appendTail(stringBuffer)
            stringBuffer.toString()
        } catch (e: TimeoutCancellationException) {
            throw RegexTimeoutException(
                "替换超时,替换规则$regex\n替换内容:$charSequence"
            )
        }
    }
}
