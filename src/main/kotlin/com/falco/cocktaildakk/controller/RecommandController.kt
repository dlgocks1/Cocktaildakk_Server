package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.domain.user.User
import com.falco.cocktaildakk.service.RecommandService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/recommand")
class RecommandController(
    private val recommandService: RecommandService
) {

    @GetMapping("/dynamic")
    fun getDynamicRecommand(@AuthenticationPrincipal user: User): List<Cocktail> {
        return recommandService.getDynamicRecommand(user)
    }

    @GetMapping("/random")
    fun getRandomReccommand(): List<Cocktail> = recommandService.randomRecommand()

    @GetMapping("/keyword")
    fun getKeywordReccommand(
        @AuthenticationPrincipal user: User,
    ): KeywordRecommandRes = recommandService.keywordRecommand(user)

    @GetMapping("/base")
    fun getBaseReccommand(
        @AuthenticationPrincipal user: User,
    ): KeywordRecommandRes = recommandService.baseRecommand(user)

}

data class KeywordRecommandRes(
    val keyword: String,
    val contents: List<Cocktail>
)