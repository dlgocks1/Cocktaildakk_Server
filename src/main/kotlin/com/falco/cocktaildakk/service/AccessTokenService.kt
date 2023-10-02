package com.falco.cocktaildakk.service

import com.falco.cocktaildakk.repository.AccessTokenRepository
import com.falco.cocktaildakk.repository.RefreshTokenRepository
import org.springframework.stereotype.Service

@Service
class AccessTokenService(
    private val accessTokenRepository: AccessTokenRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {

}
