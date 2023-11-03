package com.falco.cocktaildakkdomain.token.model

import java.util.*


enum class TokenType {
    ACCESS, REFRESH;

    fun generate(expiration: Date, token: String, userId: String): Token {
        return when (this) {
            ACCESS -> AccessToken(
                userId,
                token,
                expiration.time
            )

            REFRESH -> RefreshToken(
                userId,
                token,
                expiration.time
            )
        }
    }
}