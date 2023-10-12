package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.common.CommonResponse
import com.falco.cocktaildakk.domain.token.request.AccessTokenRequest
import com.falco.cocktaildakk.domain.token.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakk.domain.user.LoginType
import com.falco.cocktaildakk.service.AuthService
import com.falco.cocktaildakk.service.SocialLoginService
import org.springframework.web.bind.annotation.*

@RestController("/auth")
class AuthController(
    private val socialLoginService: SocialLoginService,
    private val authService: AuthService,
) {

    @ResponseBody
    @GetMapping("/kakao")
    fun kakaoCallback(@RequestParam code: String): CommonResponse<AccessTokenAndRefreshToken> {
        println("Id Token : $code")
        val accessToken = socialLoginService.getKakaoAccessToken(code)!!.accessToken
        println("Kakao AccessToken : " + accessToken)
        // -- 클라이언트 진행 --

        return CommonResponse.onSuccess(
            authService.register(
                loginType = LoginType.KAKAO,
                socialAccessToken = accessToken
            )
        )
    }

    @PostMapping("/kakao-login")
    fun login(
        @RequestBody accessToken: AccessTokenRequest
    ) {
//        return CommonResponse.onSuccess(authService.kakaoLogin(accessToken.accessToken))
    }

    @PostMapping("/token-login")
    fun loginWithJwt(
        @RequestBody accessToken: AccessTokenRequest
    ) {
//        return CommonResponse.onSuccess(authService.tokenLogin(accessToken.accessToken))
    }

    @ResponseBody
    @GetMapping("/auth")
    fun register(): String {
        return "회원가입 진행"
    }
}