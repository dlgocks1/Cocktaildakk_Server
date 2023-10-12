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
        val userId = when (loginType) {
            LoginType.KAKAO -> socialLoginService.getKakaoUserInfo(socialAccessToken).id
            LoginType.NAVER -> throw IllegalStateException("naver login is not provided")
        }

        // 기존 소셜 회원가입 여부 파악 (크로스 체크)
        if (isRegistered(userId)) {
            throw BaseException(CommonErrorCode.USER_ALREADY_EXISTS)
        } else {
            return jwtService.generateToken(userId)
        }
    }

    fun kakaoLogin(accessToken: String): AccessTokenAndRefreshToken {
        val userId = socialLoginService.getKakaoUserInfo(accessToken).id
        return if (isRegistered(userId)) {
            if (validateAccessToken(userId)) {
                jwtService.updateToken(userId)
            } else {
                jwtService.generateToken(userId)
            }
        } else {
            throw BaseException(CommonErrorCode.NOT_EXIST_USER)
        }
    }

    private fun validateAccessToken(userId: String): Boolean = jwtService.validateAccessToken(userId)

    private fun isRegistered(userId: String) = userRepository.findByIdOrNull(userId) == null

}