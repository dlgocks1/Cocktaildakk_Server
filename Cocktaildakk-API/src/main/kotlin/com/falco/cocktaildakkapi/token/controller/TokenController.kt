package com.falco.cocktaildakkapi.token.controller

import com.falco.cocktaildakkapi.auth.service.AuthService
import com.falco.cocktaildakkcommon.annotation.ApiErrorCodeExample
import com.falco.cocktaildakkcommon.exceptions.UserAuthErrorCode
import com.falco.cocktaildakkcommon.model.CommonResponse
import com.falco.cocktaildakkdomain.token.model.request.TokenRequest
import com.falco.cocktaildakkdomain.token.model.response.AccessTokenAndRefreshToken
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "TokenController")
@RequestMapping("/token")
class TokenController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    @Operation(summary = "엑세스 토큰 (자동)로그인")
    @ApiErrorCodeExample(UserAuthErrorCode::class)
    fun autoLogin(
        @RequestBody accessToken: TokenRequest
    ): CommonResponse<AccessTokenAndRefreshToken> {
        return CommonResponse.onSuccess(authService.autoLogin(accessToken.token))
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 리프레쉬(리프레쉬 토큰)")
    @ApiErrorCodeExample(UserAuthErrorCode::class)
    fun refresh(
        @RequestBody refreshToken: TokenRequest
    ): CommonResponse<AccessTokenAndRefreshToken> {
        return CommonResponse.onSuccess(authService.refresh(refreshToken.token))
    }
}