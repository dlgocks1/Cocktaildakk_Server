package com.falco.cocktaildakkapi.user.dto

import com.falco.cocktaildakkdomain.user.model.UserInfo
import io.swagger.v3.oas.annotations.media.Schema

data class UserInfoReq(
    @Schema(example = "10")
    var alcoholLevel: Int = UserInfo.DEFAULT_LEVEL,
    @Schema(example = "상큼한,간단한")
    var keyword: String,
    @Schema(example = "보드카,데킬라")
    var base: String,
    @Schema(example = "2")
    val weightLevel: Int = 2,
    @Schema(example = "2")
    val weightBase: Int = 2,
    @Schema(example = "2")
    val weightKeyword: Int = 2,
)