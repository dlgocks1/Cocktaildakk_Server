package com.falco.cocktaildakkapi.auth.service

import com.falco.cocktaildakkdomain.user.model.kakao.KakaoAccessTokenResponse
import com.falco.cocktaildakkdomain.user.model.kakao.KakaoUserInfoResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux


@Service
class SocialLoginService {

    private val kakaoWebClient: WebClient = WebClient.builder().build()
    private val REST_APY_KEY = "d8877d81bd155b6a7e526433c467eaf8"
    private val REDIRECT_URI = "http://localhost:9000/kakao"
    private val GRANT_TYPE = "authorization_code"
    // 카카오 로그인 URL : kauth.kakao.com/oauth/authorize?client_id=d8877d81bd155b6a7e526433c467eaf8&redirect_uri=http://localhost:9000/kakao&response_type=code

    fun getKakaoAccessToken(code: String): KakaoAccessTokenResponse {

        val uri =
            "https://kauth.kakao.com/oauth/token?grant_type=$GRANT_TYPE&client_id=$REST_APY_KEY&redirect_uri=$REDIRECT_URI&code=$code"
        val response: Flux<KakaoAccessTokenResponse> = kakaoWebClient.post()
            .uri(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(KakaoAccessTokenResponse::class.java)
        return response.blockFirst() ?: throw IllegalStateException("can't get kakao accesstoken")
    }

    fun getKakaoUserInfo(token: String): KakaoUserInfoResponse {
        val response: Flux<KakaoUserInfoResponse> = kakaoWebClient.get()
            .uri("https://kapi.kakao.com/v2/user/me")
            .header("Authorization", "Bearer $token")
            .retrieve()
            .bodyToFlux(KakaoUserInfoResponse::class.java)
        return response.blockFirst() ?: throw IllegalStateException("can't get kakao user info")
    }

}