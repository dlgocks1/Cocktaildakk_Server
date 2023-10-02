package com.falco.cocktaildakk.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
data class RedisProperty(
    var host: String = "",
    var port: Int = 0
)