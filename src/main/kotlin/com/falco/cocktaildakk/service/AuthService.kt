package com.falco.cocktaildakk.service

import com.falco.cocktaildakk.controller.UserInfoReq
import com.falco.cocktaildakk.domain.common.CommonErrorCode
import com.falco.cocktaildakk.domain.token.TokenType
import com.falco.cocktaildakk.domain.token.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakk.domain.user.LoginType
import com.falco.cocktaildakk.domain.user.User
import com.falco.cocktaildakk.domain.user.UserInfo
import com.falco.cocktaildakk.exceptions.BaseException
import com.falco.cocktaildakk.repository.UserInfoRepository
import com.falco.cocktaildakk.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class AuthService(
    private val userRepository: UserRepository,
    private val userInfoRepository: UserInfoRepository,
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
        val userId = jwtService.getUserIdFromToken(accessToken, TokenType.ACCESS)
        return jwtService.generateTokenByUserId(userId)
    }

    fun refresh(refreshToken: String): AccessTokenAndRefreshToken {
        val userId = jwtService.getUserIdFromToken(refreshToken, TokenType.ACCESS)
        return jwtService.generateTokenByUserId(userId)
    }

    private fun isRegistered(userId: String) = userRepository.findByIdOrNull(userId) != null

    fun register(userId: String, loginType: LoginType) {
        userRepository.save(User(id = userId, loginType = loginType))
    }

    fun checkUserPreference(user: User): Boolean {
        return userInfoRepository.findByIdOrNull(user.id) != null
    }

    fun setUserPreference(user: User, userInfoReq: UserInfoReq): UserInfo {
        return userInfoRepository.save(
            UserInfo(
                id = user.id,
                alcoholLevel = userInfoReq.alcoholLevel,
                keyword = userInfoReq.keyword,
                base = userInfoReq.base,
                weightLevel = userInfoReq.weightBase,
                weightBase = userInfoReq.weightBase,
                weightKeyword = userInfoReq.weightKeyword,
            )
        )
    }

    fun getUserPreference(user: User): UserInfo {
        return userInfoRepository.findByIdOrNull(user.id) ?: throw BaseException(CommonErrorCode.NOT_EXIST_USER)
    }

}