package com.falco.cocktaildakkdomain.user.model.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserInfoResponse(
    val id: String,
    @JsonProperty("connected_at")
    val connectedAt: String,
)