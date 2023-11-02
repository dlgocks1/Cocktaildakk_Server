package com.falco.cocktaildakk.repository

import com.falco.cocktaildakk.domain.token.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshToken, String> {

    fun findByToken(token: String): RefreshToken?
}