package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.domain.common.CommonResponse
import com.falco.cocktaildakk.domain.common.PageResponse
import com.falco.cocktaildakk.domain.user.User
import com.falco.cocktaildakk.service.CocktailService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "CocktailController")
@RequestMapping("cocktail")
class CocktailController(
    private val cocktailService: CocktailService
) {

    @GetMapping("/page")
    fun getAllPaging(
        @Parameter(example = "0") @RequestParam("page") page: Int,
        @Parameter(example = "10") @RequestParam("size") size: Int,
    ): CommonResponse<PageResponse<Cocktail>> {
        return CommonResponse.onSuccess(
            cocktailService.getAllPage(page, size)
        )
    }

    @GetMapping("/page/recommand")
    fun getRecommandPaging(
        @AuthenticationPrincipal user: User,
        @Parameter(example = "0") @RequestParam("page") page: Int,
        @Parameter(example = "10") @RequestParam("size") size: Int,
    ): CommonResponse<PageResponse<Cocktail>> {
        return CommonResponse.onSuccess(cocktailService.getRecommandPage(user, page, size))
    }

    @GetMapping("/{id}")
    @Operation(summary = "칵테일 디테일 정보 조회")
    fun getCocktailById(
        @PathVariable("id") id: Long,
    ): CommonResponse<Cocktail> {
        return CommonResponse.onSuccess(cocktailService.getCocktailById(id))
    }

    @GetMapping("/search")
    @Operation(summary = "검색 조회 (페이징 X - 10개 까지만)")
    fun queryCocktail(
        @RequestParam("query") query: String,
    ): CommonResponse<List<Cocktail>> {
        return CommonResponse.onSuccess(cocktailService.queryCocktail(query))
    }
}
