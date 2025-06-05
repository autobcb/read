package web.util

import kotlinx.coroutines.CompletableDeferred
import java.util.concurrent.ConcurrentHashMap

// 请求响应管理器（线程安全）
object ResponseManager {
    private val pendingRequests = ConcurrentHashMap<String, CompletableDeferred<String>>()

    fun registerRequest(correlationId: String): CompletableDeferred<String> {
        return CompletableDeferred<String>().also { deferred ->
            pendingRequests[correlationId] = deferred
        }
    }

    fun completeRequest(correlationId: String, result: String) {
        pendingRequests.remove(correlationId)?.complete(result)
    }

    fun cleanupExpiredRequest(correlationId: String) {
        pendingRequests.remove(correlationId)?.cancel()
    }
}