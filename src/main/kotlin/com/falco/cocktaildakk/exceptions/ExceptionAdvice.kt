package com.falco.cocktaildakk.exceptions

import com.falco.cocktaildakk.domain.common.CommonResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.core.convert.ConversionFailedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.PrintWriter
import java.io.StringWriter
import java.sql.SQLIntegrityConstraintViolationException
import java.util.*

@RestControllerAdvice
class ExceptionAdvice {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun onMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<*> {
        val errors: MutableMap<String, String> = LinkedHashMap()
        for (fieldError in e.bindingResult.fieldErrors) {
            val fieldName = fieldError.field
            var errorMessage = Optional.ofNullable(fieldError.defaultMessage).orElse("")
            if (errors.containsKey(fieldName)) {
                val existingErrorMessage = errors[fieldName]
                errorMessage = "$existingErrorMessage, $errorMessage"
            }
            errors[fieldName] = errorMessage
        }
        return ResponseEntity<Any?>(
            CommonResponse.onFailure("REQUEST_ERROR", "요청 형식 에러 result 확인해주세요", errors),
            null,
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException::class)
    fun handleSQLIntegrityConstraintViolationException(e: SQLIntegrityConstraintViolationException): ResponseEntity<*> {
        val error = "잘못된 입력 값 : " + e.message
        return ResponseEntity<Any?>(
            CommonResponse.onFailure("ILLEGAL_ARGUMENT", "데이터가 존재하지 않습니다.", Collections.singletonMap("error", error)),
            null,
            HttpStatus.NO_CONTENT
        )
    }


    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<*> {
        val error = "잘못된 입력 값 : " + e.message
        return ResponseEntity<Any?>(
            CommonResponse.onFailure("ILLEGAL_ARGUMENT", "잘못된 인자 에러", Collections.singletonMap("error", error)),
            null,
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(ConversionFailedException::class)
    fun handleConversionFailedException(e: ConversionFailedException): ResponseEntity<*> {
        val error = e.value.toString() + "은(는) " + e.targetType + " 필드에 대한 유효한 값이 아닙니다."
        return ResponseEntity<Any?>(
            CommonResponse.onFailure("CONVERSION_ERROR", "변환 에러", Collections.singletonMap("error", error)),
            null,
            HttpStatus.BAD_REQUEST
        )
    }

    private fun getExceptionStackTrace(
        e: Exception, @AuthenticationPrincipal user: User?,
        request: HttpServletRequest
    ) {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        pw.append("\n==========================!!!ERROR TRACE!!!==========================\n")
        pw.append("uri: " + request.requestURI + " " + request.method + "\n")
        if (user != null) {
            pw.append("uid: " + user.username + "\n")
        }
        pw.append(e.message)
        pw.append("\n=====================================================================")
        ExceptionAdvice.logger.error(sw.toString())
    }

    @ExceptionHandler(value = [BaseException::class])
    fun onKnownException(
        baseException: BaseException,
        @AuthenticationPrincipal user: User?,
        request: HttpServletRequest
    ): ResponseEntity<*> {
        getExceptionStackTrace(baseException, user, request)
        return ResponseEntity<Any?>(
            CommonResponse.onFailure(
                baseException.errorCode.errorReason.code,
                baseException.message,
                baseException.errorCode.errorReason.result
            ),
            null, baseException.errorCode.errorReason.httpStatus
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ExceptionAdvice::class.java)
    }
}