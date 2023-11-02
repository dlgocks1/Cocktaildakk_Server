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

}