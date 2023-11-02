package com.falco.cocktaildakk.repository

import com.falco.cocktaildakk.domain.token.AccessToken
import org.springframework.data.jpa.repository.JpaRepository

interface AccessTokenRepository : JpaRepository<AccessToken, String> {

    fun findByToken(token: String): AccessToken?
}