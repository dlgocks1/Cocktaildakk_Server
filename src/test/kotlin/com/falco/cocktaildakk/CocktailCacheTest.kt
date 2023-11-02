package com.falco.cocktaildakk

import com.falco.cocktaildakk.service.CocktailCacheService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles(profiles = ["local"])
internal class CocktailCacheTest {

    @Autowired
    private lateinit var cacheService: CocktailCacheService

    @Test
    fun cacheTest() {
        println("-------------1")
        cacheService.getCocktails() // 초기 DB 1회 요청
        println("-------------2")
        cacheService.getCocktails() // DB 요청 X
        println("-------------3")
        cacheService.putCocktails() // DB 요청 후 캐싱 재정비
        println("-------------4")
        cacheService.getCocktails() // DB 요청 X
    }
}