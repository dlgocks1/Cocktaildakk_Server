package com.falco.cocktaildakkcommon.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "jwt")
class JwtProperty {
    lateinit var accesstoken: TokenProperties
    lateinit var refreshtoken: TokenProperties

    class TokenProperties {
        lateinit var secret: String
        var expiration: Long = 0
    }
}

