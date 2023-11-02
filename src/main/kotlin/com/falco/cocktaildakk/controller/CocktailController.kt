package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.domain.common.CommonResponse
import com.falco.cocktaildakk.service.CocktailService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "CocktailController")
@RequestMapping("cocktail")
class CocktailController(
    private val cocktailService: CocktailService
) {

    private val logger = LoggerFactory.getLogger(CocktailController::class.java)

    @GetMapping("/{id}")
    @Operation(summary = "칵테일 디테일 정보 조회")
    fun getCocktailById(
        @PathVariable("id") id: Long,
    ): CommonResponse<Cocktail> {
        val cocktail = cocktailService.getCocktailById(id)
        return CommonResponse.onSuccess(cocktail)
    }

    @GetMapping("/search")
    @Operation(summary = "검색 조회 (페이징 X - 10개 까지만)")
    fun queryCocktail(
        @RequestParam("query") query: String,
    ): CommonResponse<List<Cocktail>> {
        return CommonResponse.onSuccess(cocktailService.queryCocktail(query))
    }
}
