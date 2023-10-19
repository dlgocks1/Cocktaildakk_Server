package com.falco.cocktaildakk.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "user")
data class User(
    @Id
    @Column(name = "id")
    var id: String,
    @Column(name = "login_type")
    val loginType: String,
    @Column(name = "weight_level")
    val weightLevel: Int = 2,
    @Column(name = "weight_base")
    val weightBase: Int = 2,
    @Column(name = "weight_keyword")
    val weightKeyword: Int = 2,
)