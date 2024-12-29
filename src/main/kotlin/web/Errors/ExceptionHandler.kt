package web.Errors

import io.legado.app.utils.msg
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import web.Response.JsonResponse
import java.net.SocketTimeoutException
import java.sql.SQLIntegrityConstraintViolationException

@ControllerAdvice
class MyExceptionHandler {

    @ExceptionHandler(value = [JsonException::class])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun JsonException(e: JsonException) : JsonResponse {
        return JsonResponse(false,e.message?:"")
    }

    @ExceptionHandler(value = [SQLIntegrityConstraintViolationException::class])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun SQLIntegrityConstraintViolationException(e: SQLIntegrityConstraintViolationException): JsonResponse {
        return JsonResponse(false,e.message?:"")
    }

    @ExceptionHandler(value = [SocketTimeoutException::class])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun SocketTimeoutException(e: SocketTimeoutException): JsonResponse {
        return JsonResponse(false,e.message?:"")
    }
}