package com.falco.cocktaildakkcommon.exceptions.model

import org.springframework.http.HttpStatus

data class ErrorReason(
    val httpStatus: HttpStatus,
    val code: String,
    val message: String,
    val isSuccess: Boolean = false,
    val result: Map<String, String>? = null
)

