package com.falco.cocktaildakk.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id


@Entity(name = "user_info")
data class UserInfo(
    @Id
    @Column(name = "id")
    val id: String,
    @Column(name = "alcohol_level")
    var alcoholLevel: Int = DEFAULT_LEVEL,
    var keyword: String = "",
    var base: String = "",
    @Column(name = "weight_level")
    val weightLevel: Int = 2,
    @Column(name = "weight_base")
    val weightBase: Int = 2,
    @Column(name = "weight_keyword")
    val weightKeyword: Int = 2,
) {

    companion object {

        const val DEFAULT_LEVEL = 10
    }
}