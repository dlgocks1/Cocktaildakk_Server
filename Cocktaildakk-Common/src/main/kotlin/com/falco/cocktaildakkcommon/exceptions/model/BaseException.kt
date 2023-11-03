package com.falco.cocktaildakkcommon.exceptions.model


class BaseException(
    val errorCode: BaseErrorCode
) : RuntimeException() {
    override val message = errorCode.errorReason.message
}

