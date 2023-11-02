package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.common.CommonResponse
import com.falco.cocktaildakk.domain.token.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakk.service.AuthService
import com.falco.cocktaildakk.service.SocialLoginService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "HealthController")
@RequestMapping
class HealthController(
    private val socialLoginService: SocialLoginService,
    private val authService: AuthService
) {

    @GetMapping("/")
    fun healthTest(): String {
        return "I'm Healthy!"
    }

    @ResponseBody
    @GetMapping("/kakao")
    fun kakaoCallback(@RequestParam code: String): CommonResponse<AccessTokenAndRefreshToken> {
        // -- 클라이언트 진행 영역 --
        println("Id Token : $code")
        val accessToken = socialLoginService.getKakaoAccessToken(code).accessToken
        println("Kakao AccessToken : " + accessToken)
        // -- 클라이언트 진행 영역 --

        return CommonResponse.onSuccess(authService.kakaoLogin(accessToken))
    }
}