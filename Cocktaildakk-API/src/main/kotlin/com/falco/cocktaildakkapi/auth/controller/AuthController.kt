package com.falco.cocktaildakkapi.auth.controller

import com.falco.cocktaildakkapi.auth.service.AuthService
import com.falco.cocktaildakkcommon.annotation.ApiErrorCodeExample
import com.falco.cocktaildakkcommon.exceptions.UserAuthErrorCode
import com.falco.cocktaildakkcommon.model.CommonResponse
import com.falco.cocktaildakkdomain.token.model.request.TokenRequest
import com.falco.cocktaildakkdomain.token.model.response.AccessTokenAndRefreshToken
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

