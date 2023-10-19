package com.falco.cocktaildakk.service

import com.falco.cocktaildakk.domain.bookmark.Bookmark
import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.domain.common.PageResponse
import com.falco.cocktaildakk.domain.user.User
import com.falco.cocktaildakk.repository.BookmarkRepository
import com.falco.cocktaildakk.repository.CocktailRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class BookmarkService(
    private val bookmarkRepository: BookmarkRepository,
    private val cocktailRepository: CocktailRepository
) {
    fun bookmark(user: User, cocktailId: Long): String {
        val bookmark = bookmarkRepository.findByUserIdAndCocktailId(user.id, cocktailId)
        return if (bookmark == null) {
            bookmarkRepository.save(
                Bookmark(
                    userId = user.id,
                    cocktailId = cocktailId
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
        val cocktailIds = bookmarks.toList().map { it.cocktailId }
        val cocktails = cocktailRepository.findAllById(cocktailIds)
        return PageResponse(
            isLast = bookmarks.isLast,
            totalCnt = bookmarks.totalElements,
            contents = cocktails
        )
    }

}