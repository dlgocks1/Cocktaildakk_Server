package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.common.ApiErrorCodeExample
import com.falco.cocktaildakk.domain.common.CommonResponse
import com.falco.cocktaildakk.domain.token.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakk.domain.user.LoginType
import com.falco.cocktaildakk.domain.user.UserAuthErrorCode
import com.falco.cocktaildakk.service.AuthService
import com.falco.cocktaildakk.service.JwtService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "AdminController")
@RequestMapping("/admin")
class AdminController(
    private val jwtService: JwtService,
    private val authService: AuthService
) {

    @GetMapping
    @Operation(description = "칵테일 업로드")
    fun updateCocktail(): String {
        return "준비 중"
    }

    @GetMapping("/test-register/{userId}")
    @Operation(summary = "테스트 유저 등록", description = "테스트 유저의 정보를 저장합니다.")
    @ApiErrorCodeExample(UserAuthErrorCode::class)
    fun testRegister(
        @Parameter(description = "테스트 유저아이디", example = "1234")
        @PathVariable("userId") userId: String
    ): CommonResponse<AccessTokenAndRefreshToken> {
        authService.register(userId, LoginType.KAKAO)
        return CommonResponse.onSuccess(jwtService.generateTokenByUserId(userId))
    }
}