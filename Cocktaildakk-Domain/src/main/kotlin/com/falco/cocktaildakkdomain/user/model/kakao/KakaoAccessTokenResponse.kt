package com.falco.cocktaildakkdomain.user.model.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoAccessTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("expires_in")
    val expiresIn: Int,
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: Int,
    @JsonProperty("token_type")
    val tokenType: String
)