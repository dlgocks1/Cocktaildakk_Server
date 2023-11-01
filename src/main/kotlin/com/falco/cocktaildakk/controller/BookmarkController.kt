package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.domain.common.CommonResponse
import com.falco.cocktaildakk.domain.common.PageResponse
import com.falco.cocktaildakk.domain.user.User
import com.falco.cocktaildakk.service.BookmarkService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "BookmarkController")
@RequestMapping("/bookmark")
class BookmarkController(
    private val bookmarkService: BookmarkService
) {

    @GetMapping("/{cocktailId}")
    @Operation(summary = "칵테일 북마크 추가/삭제")
    fun getCocktailById(
        @AuthenticationPrincipal user: User,
        @Parameter(example = "1") @PathVariable cocktailId: String,
    ): CommonResponse<String> {
        return CommonResponse.onSuccess(bookmarkService.bookmark(user, cocktailId.toLong()))
    }

    @GetMapping
    @Operation(summary = "북마크 칵테일 페이징 조회")
    fun getCocktailPaing(
        @AuthenticationPrincipal user: User,
        @Parameter(example = "0") @RequestParam("page") page: Int,
        @Parameter(example = "10") @RequestParam("size") size: Int,
    ): PageResponse<Cocktail> = bookmarkService.getBookmarkedCocktails(user, page, size)
}
