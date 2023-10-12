package com.falco.cocktaildakk.domain.token.response

import com.falco.cocktaildakk.domain.token.Token

data class AccessTokenAndRefreshToken(
    val accessToken: Token,
    val refreshToken: Token
)
