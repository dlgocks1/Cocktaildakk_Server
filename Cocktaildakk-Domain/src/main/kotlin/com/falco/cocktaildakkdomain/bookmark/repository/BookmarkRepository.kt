package com.falco.cocktaildakkdomain.bookmark.repository

import com.falco.cocktaildakkdomain.bookmark.model.Bookmark
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookmarkRepository : JpaRepository<Bookmark, Long> {

    fun findByUserIdAndCocktailId(userId: String, cocktailId: Long): Bookmark?

    fun findAllByUserId(userId: String, pageable: Pageable): Page<Bookmark>
}
