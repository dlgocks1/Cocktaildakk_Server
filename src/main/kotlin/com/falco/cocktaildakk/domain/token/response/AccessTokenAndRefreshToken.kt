package com.falco.cocktaildakk.domain.token.response

data class AccessTokenAndRefreshToken(
    val accessToken: String,
    val refreshToken: String
)
