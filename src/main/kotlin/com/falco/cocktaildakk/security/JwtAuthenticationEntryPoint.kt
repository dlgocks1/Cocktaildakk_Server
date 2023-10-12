package com.falco.cocktaildakk.security

import com.falco.cocktaildakk.domain.common.CommonErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.json.JSONException
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
        val errorCode: CommonErrorCode

        /**
         * 토큰이 없는 경우 예외처리
         */
        if (exception == null) {
            errorCode = CommonErrorCode.UNAUTHORIZED_EXCEPTION
            setResponse(response, errorCode)
            return
        }
        /**
         * 토큰이 만료된 경우 예외처리
         */
        if (exception == "NotExistUser") {
            errorCode = CommonErrorCode.NOT_EXIST_USER
            setResponse(response, errorCode)
            return
        } else if (exception == "ExpiredJwtException") {
            errorCode = CommonErrorCode.EXPIRED_JWT_EXCEPTION
            setResponse(response, errorCode)
            return
        } else if (exception == "MalformedJwtException") {
            errorCode = CommonErrorCode.INVALID_TOKEN_EXCEPTION
            setResponse(response, errorCode)
            return
        } else if (exception == "HijackException") {
            errorCode = CommonErrorCode.HIJACK_JWT_TOKEN_EXCEPTION
            setResponse(response, errorCode)
            return
        }
    }

    @Throws(IOException::class)
    private fun setResponse(response: HttpServletResponse, errorCode: CommonErrorCode) {
        val json = JSONObject()
        response.contentType = "application/json;charset=UTF-8"
        response.characterEncoding = "utf-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        try {
            json.put("code", errorCode.code)
            json.put("message", errorCode.message)
            json.put("isSuccess", false)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        response.writer.print(json)
    }
}