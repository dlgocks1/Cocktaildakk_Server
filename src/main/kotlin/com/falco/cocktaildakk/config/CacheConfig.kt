package com.falco.cocktaildakk.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit


@Configuration
@EnableCaching
class CacheConfig {
    @Bean
    fun cacheManager(): CacheManager {
        return CaffeineCacheManager()
            .apply {
                setCaffeine(
                    Caffeine.newBuilder()
                        .expireAfterWrite(24, TimeUnit.HOURS) // 캐시 만료 시간 설정
                        .maximumSize(150) // 최대 캐시 크기 설정
                )
                setCacheNames(listOf("cocktails"))
            }
    }
}