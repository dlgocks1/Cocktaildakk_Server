package com.falco.cocktaildakk.domain.response


data class CommonResponse<out T>(
    val isSuccess: Boolean,
    val message: String?,
    val code: String?,
    val result: T? = null,
) {

    companion object {
        fun <T> onSuccess(data: T): CommonResponse<T> {
            return CommonResponse(true, "요청에 성공하였습니다.", "1000", data)
        }

        fun <T> onFailure(code: String, message: String?, data: T? = null): CommonResponse<T> {
            return CommonResponse(false, message, code, data)
        }
    }
}