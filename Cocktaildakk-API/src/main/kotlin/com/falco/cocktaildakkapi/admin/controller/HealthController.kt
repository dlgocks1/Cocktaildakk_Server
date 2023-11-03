package com.falco.cocktaildakkapi.admin.controller

import com.falco.cocktaildakkapi.auth.service.AuthService
import com.falco.cocktaildakkapi.auth.service.SocialLoginService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

//    @ResponseBody
//    @GetMapping("/kakao")
//    fun kakaoCallback(@RequestParam code: String): CommonResponse<AccessTokenAndRefreshToken> {
//        // -- 클라이언트 진행 영역 --
//        println("Id Token : $code")
//        val accessToken = socialLoginService.getKakaoAccessToken(code).accessToken
//        println("Kakao AccessToken : " + accessToken)
//        // -- 클라이언트 진행 영역 --
//
//        return CommonResponse.onSuccess(authService.kakaoLogin(accessToken))
//    }
}