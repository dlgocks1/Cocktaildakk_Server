package com.falco.cocktaildakk.domain.token

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.util.*

interface Token {
    val userId: String
    val token: String
    val expiration: Date
}

@RedisHash(value = "access_token", timeToLive = 1_000)
data class AccessToken(
    @Id
    override val userId: String,
    override val token: String,
    override val expiration: Date,
) : Token


@RedisHash(value = "refresh_token", timeToLive = 100_000)
data class RefreshToken(
    @Id
    override val userId: String,
    override val token: String,
    override val expiration: Date,
) : Token
