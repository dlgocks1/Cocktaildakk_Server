package com.falco.cocktaildakk.domain.token

import jakarta.persistence.Entity
import jakarta.persistence.Id

interface Token {
    val userId: String
    val token: String
    val expiration: Long
}

@Entity(name = "access_token")
data class AccessToken(
    @Id
    override var userId: String,
    override val token: String,
    override val expiration: Long,
) : Token

@Entity(name = "refresh_token")
data class RefreshToken(
    @Id
    override val userId: String,
    override val token: String,
    override val expiration: Long,
) : Token
