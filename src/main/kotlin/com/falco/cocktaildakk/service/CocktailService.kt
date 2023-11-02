package com.falco.cocktaildakk.service

import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.domain.common.CommonErrorCode
import com.falco.cocktaildakk.exceptions.BaseException
import org.springframework.stereotype.Service

@Service
class CocktailService(
    private val cacheService: CocktailCacheService,
) {

    fun getCocktailById(id: Long): Cocktail =
        cacheService.getCocktails().find { it.id == id } ?: throw BaseException(CommonErrorCode.NOT_EXIST_COCKTAIL)

    fun queryCocktail(query: String): List<Cocktail> {
        return cacheService.getCocktails().asSequence().filter {
            // TODO 이게 맞나??
            it.baseLiquor.contains(query) ||
                    it.keywords.contains(query) ||
                    it.description.contains(query) ||
                    it.englishName.contains(query) ||
                    it.koreanName.contains(query) ||
                    it.ingredients.contains(query) ||
                    it.mixingMethod.contains(query)
        }.take(10).toList()
    }

}