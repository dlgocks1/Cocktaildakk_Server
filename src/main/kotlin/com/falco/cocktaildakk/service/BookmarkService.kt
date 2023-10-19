package com.falco.cocktaildakk.service

import com.falco.cocktaildakk.repository.BookmarkRepository
import org.springframework.stereotype.Service

@Service
class BookmarkService(
    private val bookmarkRepository: BookmarkRepository
) {

}