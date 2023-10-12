package com.falco.cocktaildakk.exceptions

import com.falco.cocktaildakk.domain.common.BaseErrorCode


class BaseException(
    val errorCode: BaseErrorCode
) : RuntimeException() {
    override val message = errorCode.errorReason.message
}

