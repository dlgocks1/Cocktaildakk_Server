package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.common.ApiErrorCodeExample
import com.falco.cocktaildakk.domain.common.CommonResponse
import com.falco.cocktaildakk.domain.token.request.AccessTokenRequest
import com.falco.cocktaildakk.domain.token.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakk.domain.user.UserAuthErrorCode
import com.falco.cocktaildakk.service.AuthService
import com.falco.cocktaildakk.service.JwtService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "AuthController")
@RequestMapping("/auth")
class AuthController(
    private val jwtService: JwtService,
    private val authService: AuthService,
) {

    @PostMapping("/kakao-login")
    @Operation(summary = "회원가입")
    @ApiErrorCodeExample(UserAuthErrorCode::class)
    fun login(
        @RequestBody accessToken: AccessTokenRequest // 카카오 엑세스 토큰을 의미
    ): CommonResponse<AccessTokenAndRefreshToken> {
        return CommonResponse.onSuccess(authService.kakaoLogin(accessToken.accessToken))
    }

    @PostMapping("/auto-login")
    @Operation(summary = "자동 로그인")
    @ApiErrorCodeExample(UserAuthErrorCode::class)
    fun autoLogin(
        @RequestBody accessToken: AccessTokenRequest
    ): CommonResponse<AccessTokenAndRefreshToken> {
        return CommonResponse.onSuccess(authService.autoLogin(accessToken.accessToken))
    }

    @GetMapping("/test-register/{userId}")
    @Operation(summary = "테스트 유저 등록", description = "해당 유저의 AccessToken를 Redis에 저장합니다.(DB 저장 X)")
    @ApiErrorCodeExample(UserAuthErrorCode::class)
    fun testRegister(
        @Parameter(description = "테스트 유저아이디", example = "1234")
        @PathVariable("userId") userId: String
    ): CommonResponse<AccessTokenAndRefreshToken> {
        return CommonResponse.onSuccess(jwtService.generateTokenByUserId(userId))
    }
}