package com.falco.cocktaildakk.domain.user

import com.falco.cocktaildakk.domain.common.BaseErrorCode
import com.falco.cocktaildakk.domain.common.ErrorReason
import com.falco.cocktaildakk.domain.common.ExplainError
import org.springframework.http.HttpStatus

enum class UserAuthErrorCode(
    val httpStatus: HttpStatus,
    val code: String,
    val message: String
) : BaseErrorCode {

    @ExplainError("유저 권한이 없는 경우")
    ForbiddenException(HttpStatus.UNAUTHORIZED, "AUTH002", "해당 요청에 대한 권한이 없습니다."),

    @ExplainError("토큰을 입력 안했을 경우")
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "AUTH003", "로그인 후 이용가능합니다. 토큰을 입력해 주세요"),

    @ExplainError("토큰이 만료됐을 경우")
    EXPIRED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "AUTH004", "기존 토큰이 만료되었습니다. 토큰을 재발급해주세요."),

    @ExplainError("액세스 토큰과 리프레쉬 토큰이 모두 만료됐을 경우")
    RELOGIN_EXCEPTION(HttpStatus.UNAUTHORIZED, "AUTH005", "모든 토큰이 만료되었습니다. 다시 로그인해주세요."),

    @ExplainError("액세스 토큰과 리프레쉬 토큰이 모두 만료됐을 경우")
    INVALID_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "AUTH006", "토큰이 올바르지 않습니다."),

    @ExplainError("이미 로그아웃 한 리프레쉬 토큰으로 로그아웃 한 경우")
    HIJACK_JWT_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "AUTH007", "탈취된(로그아웃 된) 토큰입니다 다시 로그인 해주세요."),

    @ExplainError("리프레쉬 토큰이 만료됐을 경우")
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH009", "리프레쉬 토큰이 유효하지 않습니다. 다시 로그인 해주세요"),

    @ExplainError("토큰을 입력 안했을 경우")
    NOT_EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH010", "토큰이 비어있습니다 토큰을 보내주세요"),

    @ExplainError("토큰에 담긴 유저가 없는 경우")
    NOT_EXISTS_USER_HAVE_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH011", "해당 토큰을 가진 유저가 존재하지 않습니다."),

    @ExplainError("유저가 존재하지 않는 경우")
    NOT_EXIST_USER(HttpStatus.UNAUTHORIZED, "U009", "해당 유저가 존재하지 않습니다."),

    @ExplainError("해당 유저에게 URI 접근권한이 없을 때")
    NOT_ALLOWED_ACCESS(HttpStatus.UNAUTHORIZED, "U010", "접근 권한이 없습니다.");

    override val errorReason = ErrorReason(
        httpStatus = httpStatus,
        code = code,
        message = message
    )

}