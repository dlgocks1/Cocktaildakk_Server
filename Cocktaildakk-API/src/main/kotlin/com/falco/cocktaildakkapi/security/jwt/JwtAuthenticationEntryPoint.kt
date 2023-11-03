package com.falco.cocktaildakkapi.security.jwt

import com.falco.cocktaildakkcommon.exceptions.CommonErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.json.JSONObject
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        val exception = request.getAttribute("exception") as? String
        when (exception) {
            "NotExistUser" -> {
                // 토큰이 만료된 경우 예외처리
                setResponse(response, CommonErrorCode.NOT_EXIST_USER)
            }

            "ExpiredJwtException" -> {
                setResponse(response, CommonErrorCode.EXPIRED_JWT_EXCEPTION)
            }

            "MalformedJwtException" -> {
                setResponse(response, CommonErrorCode.INVALID_TOKEN_EXCEPTION)
            }

            "HijackException" -> {
                setResponse(response, CommonErrorCode.HIJACK_JWT_TOKEN_EXCEPTION)
            }

            null -> {
                // 토큰이 없는 경우 예외처리
                setResponse(response, CommonErrorCode.UNAUTHORIZED_EXCEPTION)
            }
        }
    }

    @Throws(IOException::class)
    private fun setResponse(response: HttpServletResponse, errorCode: CommonErrorCode) {
        response.contentType = "application/json;charset=UTF-8"
        response.characterEncoding = "utf-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val json = JSONObject().apply {
            put("code", errorCode.code)
            put("message", errorCode.message)
            put("isSuccess", false)
        }
        response.writer.print(json)
    }
}