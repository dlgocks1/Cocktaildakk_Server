package com.falco.cocktaildakk.domain.token

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

interface Token {
    val userId: String
    val token: String
    val expiration: Long
}

/** Redis내부에 access_token:userId 형식으로 저장 */
@RedisHash(value = "access_token", timeToLive = 1_000)
data class AccessToken(
    @Id
    override val userId: String,
    override val token: String,
    @TimeToLive
    override val expiration: Long,
) : Token


@RedisHash(value = "refresh_token", timeToLive = 100_000)
data class RefreshToken(
    @Id
    override val userId: String,
    override val token: String,
    @TimeToLive
    override val expiration: Long,
) : Token
