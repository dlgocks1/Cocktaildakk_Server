package com.falco.cocktaildakkapi.cocktail.service

import com.falco.cocktaildakkdomain.cocktail.model.Cocktail
import com.falco.cocktaildakkdomain.cocktail.repository.CocktailRepository
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class CocktailCacheService(
    private val cocktailRepository: CocktailRepository
) {

    @Cacheable("cocktails")
    fun getCocktails(): List<Cocktail> {
        return cocktailRepository.findAll()
    }

    @Scheduled(cron = "0 0 2 * * *")
    @CachePut("cocktails")
    fun reloadCocktails(): List<Cocktail> {
        return cocktailRepository.findAll()
    }

}