package com.falco.cocktaildakkdomain.token.repository

import com.falco.cocktaildakkdomain.token.model.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshToken, String> {

    fun findByToken(token: String): RefreshToken?
}