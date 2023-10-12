package com.falco.cocktaildakk.domain.user.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserInfoResponse(
    val id: String,
    @JsonProperty("connected_at")
    val connectedAt: String,
)