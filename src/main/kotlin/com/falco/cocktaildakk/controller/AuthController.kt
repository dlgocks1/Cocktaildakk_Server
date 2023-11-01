package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.common.ApiErrorCodeExample
import com.falco.cocktaildakk.domain.common.CommonResponse
import com.falco.cocktaildakk.domain.token.request.TokenRequest
import com.falco.cocktaildakk.domain.token.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakk.domain.user.UserAuthErrorCode
import com.falco.cocktaildakk.service.AuthService
import com.falco.cocktaildakk.service.JwtService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "AuthController")
@RequestMapping("/auth")
class AuthController(
    private val jwtService: JwtService,
    private val authService: AuthService,
) {

    @PostMapping("/kakao-login")
    @Operation(summary = "카카오 로그인 / 회원가입")
    @ApiErrorCodeExample(UserAuthErrorCode::class)
    fun login(
        @Parameter(name = "카카오 엑세스 토큰을 의미합니다.")
        @RequestBody accessToken: TokenRequest
    ): CommonResponse<AccessTokenAndRefreshToken> {
        return CommonResponse.onSuccess(authService.kakaoLogin(accessToken.token))
    }

}

