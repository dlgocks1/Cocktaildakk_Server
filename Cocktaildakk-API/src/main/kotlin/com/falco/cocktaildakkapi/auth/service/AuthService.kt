package com.falco.cocktaildakkapi.auth.service

import com.falco.cocktaildakkapi.token.service.JwtService
import com.falco.cocktaildakkapi.user.dto.UserInfoReq
import com.falco.cocktaildakkcommon.exceptions.CommonErrorCode
import com.falco.cocktaildakkcommon.exceptions.model.BaseException
import com.falco.cocktaildakkdomain.token.model.TokenType
import com.falco.cocktaildakkdomain.token.model.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakkdomain.user.model.LoginType
import com.falco.cocktaildakkdomain.user.model.User
import com.falco.cocktaildakkdomain.user.model.UserInfo
import com.falco.cocktaildakkdomain.user.repository.UserInfoRepository
import com.falco.cocktaildakkdomain.user.repository.UserRepository
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