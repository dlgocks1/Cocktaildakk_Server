package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.common.CommonResponse
import com.falco.cocktaildakk.domain.token.request.AccessTokenRequest
import com.falco.cocktaildakk.domain.token.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakk.service.AuthService
import com.falco.cocktaildakk.service.JwtService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val jwtService: JwtService,
    private val authService: AuthService,
) {

    @PostMapping("/kakao-login")
    fun login(
        @RequestBody accessToken: AccessTokenRequest // 카카오 엑세스 토큰을 의미
    ): CommonResponse<AccessTokenAndRefreshToken> {
        return CommonResponse.onSuccess(authService.kakaoLogin(accessToken.accessToken))
    }

    @PostMapping("/auto-login")
    fun autoLogin(
        @RequestBody accessToken: AccessTokenRequest
    ): CommonResponse<AccessTokenAndRefreshToken> {
        return CommonResponse.onSuccess(authService.autoLogin(accessToken.accessToken))
    }

    @GetMapping("/test-register/{userId}")
    fun testRegister(@PathVariable("userId") userId: String): CommonResponse<AccessTokenAndRefreshToken> {
        return CommonResponse.onSuccess(jwtService.generateTokenByUserId(userId))
    }
}