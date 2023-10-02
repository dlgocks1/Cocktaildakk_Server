package com.falco.cocktaildakk.domain.dto

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.util.*


@RedisHash(value = "refresh_token", timeToLive = 1_000)
data class RefreshToken(
    @Id
    val userId: String,
    val token: String,
    val expiration: Date,
)
