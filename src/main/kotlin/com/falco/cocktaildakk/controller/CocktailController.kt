package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.dto.Cocktail
import com.falco.cocktaildakk.domain.response.CommonResponse
import com.falco.cocktaildakk.exceptions.NoContentException
import com.falco.cocktaildakk.service.CocktailService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CocktailController(
    private val cocktailService: CocktailService
) {

    private val logger = LoggerFactory.getLogger(CocktailController::class.java)

    @GetMapping("/{id}")
    fun getCocktailById(
        @PathVariable("id") id: Long,
    ): CommonResponse<Cocktail> {
        return try {
            val cocktail = cocktailService.getCocktailById(id)
            logger.info(cocktail.toString())
            CommonResponse.onSuccess(cocktail)
        } catch (e: NoContentException) {
            CommonResponse.onFailure(
                code = "2001",
                message = e.message,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            logger.error(e.stackTraceToString())
            CommonResponse.onFailure(
                code = "4001",
                message = "알 수 없는 오류"
            )
        }
    }
}
