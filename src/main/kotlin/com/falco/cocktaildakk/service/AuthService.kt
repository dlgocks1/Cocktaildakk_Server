package com.falco.cocktaildakk.service

import com.falco.cocktaildakk.domain.token.TokenType
import com.falco.cocktaildakk.domain.token.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakk.domain.user.LoginType
import com.falco.cocktaildakk.domain.user.User
import com.falco.cocktaildakk.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val socialLoginService: SocialLoginService
) {
    fun kakaoLogin(accessToken: String): AccessTokenAndRefreshToken {
        val userId = socialLoginService.getKakaoUserInfo(accessToken).id
        if (!isRegistered(userId)) {
            register(userId, LoginType.KAKAO)
        }
        return jwtService.generateTokenByUserId(userId)
    }

    fun autoLogin(accessToken: String): AccessTokenAndRefreshToken {
        jwtService.validateToken(accessToken, TokenType.ACCESS)
        val userId = jwtService.getUserIdFromToken(accessToken, TokenType.ACCESS)
        return jwtService.generateTokenByUserId(userId)
    }

    private fun isRegistered(userId: String) = userRepository.findByIdOrNull(userId) != null

    private fun register(userId: String, loginType: LoginType) = userRepository.save(User(userId, loginType.name))
}