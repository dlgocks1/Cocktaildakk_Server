package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.common.CommonResponse
import com.falco.cocktaildakk.domain.token.request.AccessTokenRequest
import com.falco.cocktaildakk.domain.token.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakk.service.AuthService
import com.falco.cocktaildakk.service.SocialLoginService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val socialLoginService: SocialLoginService,
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

    @ResponseBody
    @GetMapping("/auth")
    fun register(): String {
        return "회원가입 진행"
    }
}