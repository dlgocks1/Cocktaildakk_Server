package com.falco.cocktaildakkdomain.token.model.response

data class AccessTokenAndRefreshToken(
    val accessToken: String,
    val refreshToken: String
)
