package com.falco.cocktaildakk.domain.bookmark

import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.domain.user.User
import jakarta.persistence.*

@Entity(name = "bookmark")
data class Bookmark(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false, updatable = false)
    val userId: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktailId", nullable = false, updatable = false)
    val cocktail: Cocktail,
)