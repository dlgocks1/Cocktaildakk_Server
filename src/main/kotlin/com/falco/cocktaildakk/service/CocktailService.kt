package com.falco.cocktaildakk.service

import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.exceptions.NoContentException
import com.falco.cocktaildakk.repository.CocktailRepository
import com.falco.cocktaildakk.util.Contants.ERROR
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CocktailService(
    private val cocktailRepository: CocktailRepository
) {

    fun getCocktailById(id: Long): Cocktail {
        return cocktailRepository.findByIdOrNull(id)
            ?: throw NoContentException(ERROR.format("아이디가 ${id}인 칵테일이 존재하지 않습니다."))
    }
}