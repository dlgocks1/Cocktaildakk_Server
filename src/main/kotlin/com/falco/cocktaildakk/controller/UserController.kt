package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.common.CommonResponse
import com.falco.cocktaildakk.domain.token.request.TokenRequest
import com.falco.cocktaildakk.domain.user.User
import com.falco.cocktaildakk.domain.user.UserInfo
import com.falco.cocktaildakk.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "UserController")
@RequestMapping("/user")
class UserController(
    private val authService: AuthService
) {
    @PostMapping("/check-preference")
    @Operation(summary = "회원가입/로그인시 유저 정보를 입력했는지 확인합니다. false면 유저정보 입력필요")
    fun checkUserPreference(
        @Parameter(name = "엑세스 토큰을 의미합니다.")
        @RequestBody accessToken: TokenRequest
    ): CommonResponse<Boolean> {
        return CommonResponse.onSuccess(authService.checkUserPreference(accessToken.token))
    }

    @PostMapping("/preference")
    @Operation(summary = "유저 정보를 Upsert")
    fun setUserPreference(
        @AuthenticationPrincipal user: User,
        @Parameter(name = "키워드 및 베이스(기주)는 ','로 구분합니다.")
        @RequestBody userInfoReq: UserInfoReq
    ): CommonResponse<UserInfo> {
        return CommonResponse.onSuccess(authService.setUserPreference(user, userInfoReq))
    }

    @GetMapping("/preference")
    @Operation(summary = "유저 정보 조회")
    fun getUserPreference(@AuthenticationPrincipal user: User): CommonResponse<UserInfo> {
        return CommonResponse.onSuccess(authService.getUserPreference(user))
    }
}

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