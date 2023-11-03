package com.falco.cocktaildakkdomain.token.repository

import com.falco.cocktaildakkdomain.token.model.AccessToken
import org.springframework.data.jpa.repository.JpaRepository

interface AccessTokenRepository : JpaRepository<AccessToken, String> {

    fun findByToken(token: String): AccessToken?
}