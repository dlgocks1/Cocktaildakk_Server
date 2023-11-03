package com.falco.cocktaildakkapi.bookmark.service

import com.falco.cocktaildakkapi.cocktail.service.CocktailCacheService
import com.falco.cocktaildakkcommon.exceptions.CommonErrorCode
import com.falco.cocktaildakkcommon.exceptions.model.BaseException
import com.falco.cocktaildakkcommon.model.PageResponse
import com.falco.cocktaildakkdomain.bookmark.model.Bookmark
import com.falco.cocktaildakkdomain.bookmark.repository.BookmarkRepository
import com.falco.cocktaildakkdomain.cocktail.model.Cocktail
import com.falco.cocktaildakkdomain.user.model.User
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