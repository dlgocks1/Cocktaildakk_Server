package com.falco.cocktaildakk.service

import com.falco.cocktaildakk.domain.common.CommonErrorCode
import com.falco.cocktaildakk.domain.token.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakk.domain.user.LoginType
import com.falco.cocktaildakk.exceptions.BaseException
import com.falco.cocktaildakk.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val socialLoginService: SocialLoginService
) {

    fun register(loginType: LoginType, socialAccessToken: String): AccessTokenAndRefreshToken {
        when (loginType) {
            LoginType.KAKAO -> {
                // 카카오 유저 정보 취득
                val userId = socialLoginService.getKakaoUserInfo(socialAccessToken).id
                // 기존 소셜 회원가입 여부 파악 (크로스 체크)
                if (!isRegistered(userId)) {
                    return jwtService.register(userId)
                } else {
                    throw BaseException(CommonErrorCode.USER_ALREADY_EXISTS)
                }
            }

            LoginType.NAVER -> throw IllegalStateException("naver login is not provided")
        }
    }

    fun kakaoLogin(accessToken: String) {
        val kakaoUserInfo = socialLoginService.getKakaoUserInfo(accessToken)

//        if (isRegistered(kakaoUserInfo.id)) {
//            return jwtService.let { tokenUtil ->
//                AccessTokenAndRefreshToken(
//                    accessToken = tokenUtil.generateToken(id = userInfo.id, tokenType = TokenType.ACCESS).also {
//                        accessTokenRepository.save(it as AccessToken)
//                    },
//                    refreshToken = tokenUtil.generateToken(id = userInfo.id, tokenType = TokenType.REFRESH)
//                        .also {
//                            refreshTokenRepository.save(it as RefreshToken)
//                        }
//                )
//            }
//        } else {
//            throw BaseException(CommonErrorCode.USER_ALREADY_EXISTS)
//        }
    }

    fun tokenLogin(accessToken: String) {
    }

    private fun isRegistered(userId: String) = userRepository.findByIdOrNull(userId) == null

}