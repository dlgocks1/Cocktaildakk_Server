package com.falco.cocktaildakk.service

import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.domain.common.CommonErrorCode
import com.falco.cocktaildakk.exceptions.BaseException
import com.falco.cocktaildakk.repository.CocktailRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CocktailService(
    private val cocktailRepository: CocktailRepository
) {

    fun getCocktailById(id: Long): Cocktail =
        cocktailRepository.findByIdOrNull(id) ?: throw BaseException(CommonErrorCode.NOT_EXIST_COCKTAIL)
}