package com.falco.cocktaildakk.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.falco.cocktaildakk.config.properties.S3Property
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config(
    private val s3Property: S3Property
) {

    @Bean
    fun amazonS3Client(): AmazonS3 {
        val credentials = BasicAWSCredentials(s3Property.accessKey, s3Property.secretKey)
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withRegion(s3Property.region)
            .build()
    }
}