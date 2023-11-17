package com.falco.cocktaildakkcommon.config.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class KakaoProperty(
    @Value("\${kakao.restApiKey}")
    val restApiKey: String
)
