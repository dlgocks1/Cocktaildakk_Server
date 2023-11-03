package com.falco.cocktaildakkdomain.bookmark.model

import com.falco.cocktaildakkdomain.cocktail.model.Cocktail
import com.falco.cocktaildakkdomain.user.model.User
import jakarta.persistence.*

@Entity(name = "bookmark")
data class Bookmark(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    val user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id", nullable = false, updatable = false)
    val cocktail: Cocktail,
)