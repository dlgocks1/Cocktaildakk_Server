package com.falco.cocktaildakk.domain.bookmark

import jakarta.persistence.*

@Entity(name = "bookmark")
data class Bookmark(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(name = "user_id")
    val userId: String,
    @Column(name = "cocktail_id")
    val cocktailId: Long,
)