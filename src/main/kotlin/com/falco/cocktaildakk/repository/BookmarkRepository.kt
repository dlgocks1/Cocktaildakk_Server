package com.falco.cocktaildakk.repository

import com.falco.cocktaildakk.domain.bookmark.Bookmark
import org.springframework.data.jpa.repository.JpaRepository

interface BookmarkRepository : JpaRepository<Bookmark, Long>
