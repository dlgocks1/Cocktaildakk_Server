package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.common.CommonResponse
import com.falco.cocktaildakk.domain.user.User
import com.falco.cocktaildakk.service.BookmarkService
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "BookmarkController")
@RequestMapping("/bookmark")
class BookmarkController(
    private val bookmarkService: BookmarkService
) {

    private val logger = LoggerFactory.getLogger(BookmarkController::class.java)

    @GetMapping("/{cocktailId}")
    fun getCocktailById(
        @AuthenticationPrincipal user: User,
        @RequestParam("cocktailId") cocktailId: Int,
    ): CommonResponse<String> {
        logger.info("$user $cocktailId")
        return CommonResponse.onSuccess("cocktail")
    }
}
