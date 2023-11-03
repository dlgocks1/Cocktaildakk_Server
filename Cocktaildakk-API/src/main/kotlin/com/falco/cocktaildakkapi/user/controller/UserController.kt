package com.falco.cocktaildakkapi.user.controller

import com.falco.cocktaildakkapi.auth.service.AuthService
import com.falco.cocktaildakkapi.user.dto.UserInfoReq
import com.falco.cocktaildakkcommon.model.CommonResponse
import com.falco.cocktaildakkdomain.user.model.User
import com.falco.cocktaildakkdomain.user.model.UserInfo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "UserController")
@RequestMapping("/user")
class UserController(
    private val authService: AuthService
) {
    @GetMapping("/check-preference")
    @Operation(summary = "회원가입/로그인시 유저 정보를 입력했는지 확인합니다. -> false면 유저정보 입력필요")
    fun checkUserPreference(@AuthenticationPrincipal user: User): CommonResponse<Boolean> {
        return CommonResponse.onSuccess(authService.checkUserPreference(user))
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

