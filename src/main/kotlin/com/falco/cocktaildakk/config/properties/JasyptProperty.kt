package com.falco.cocktaildakk.config.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class JasyptProperty(
    @Value("\${jasypt.encryptor.password}")
    val jasyptPassword: String
)