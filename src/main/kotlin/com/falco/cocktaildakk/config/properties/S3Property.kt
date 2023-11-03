package com.falco.cocktaildakk.config.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class S3Property(
    @Value("\${aws.credentials.accessKey}")
    val accessKey: String,
    @Value("\${aws.credentials.secretKey}")
    val secretKey: String,
    @Value("\${aws.region.static}")
    val region: String
)