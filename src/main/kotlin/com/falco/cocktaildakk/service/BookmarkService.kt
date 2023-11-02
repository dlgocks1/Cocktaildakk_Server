package com.falco.cocktaildakk.service

import com.falco.cocktaildakk.domain.bookmark.Bookmark
import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.domain.common.CommonErrorCode
import com.falco.cocktaildakk.domain.common.PageResponse
import com.falco.cocktaildakk.domain.user.User
import com.falco.cocktaildakk.exceptions.BaseException
import com.falco.cocktaildakk.repository.BookmarkRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class BookmarkService(
    private val bookmarkRepository: BookmarkRepository,
    private val cacheService: CocktailCacheService
) {
    fun bookmark(user: User, cocktailId: Long): String {
        val bookmark = bookmarkRepository.findByUserIdAndCocktailId(user.id, cocktailId)
        return if (bookmark == null) {
            bookmarkRepository.save(
                Bookmark(
                    user = user,
                    cocktail = cacheService.getCocktails().find { it.id == cocktailId }
                        ?: throw BaseException(CommonErrorCode.NOT_EXIST_COCKTAIL)
                )
            )
            "북마크하였습니다."
        } else {
            bookmarkRepository.delete(bookmark)
            "북마크를 취소하였습니다."
        }
    }

    fun getBookmarkedCocktails(user: User, page: Int, size: Int): PageResponse<Cocktail> {
        val bookmarks = bookmarkRepository.findAllByUserId(
            userId = user.id,
            pageable = PageRequest.of(
                page, size,
                Sort.by("id").descending()
            )
        )
        val cocktailIds = bookmarks.toList().map { it.cocktail.id }
        val cocktails = cacheService.getCocktails().filter { cocktailIds.contains(it.id) }
        return PageResponse(
            isLast = bookmarks.isLast,
            totalCnt = bookmarks.totalElements,
            contents = cocktails
        )
    }

}