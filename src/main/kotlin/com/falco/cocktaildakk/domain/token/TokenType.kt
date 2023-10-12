package com.falco.cocktaildakk.domain.token

import java.util.*


enum class TokenType {
    ACCESS, REFRESH;

    fun generate(expiration: Date, token: String, userId: String): Token {
        return when (this) {
            ACCESS -> AccessToken(userId, token, expiration)
            REFRESH -> RefreshToken(userId, token, expiration)
        }
    }
}