package com.falco.cocktaildakk.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "user")
data class User(
    @Id
    var id: String,
    @Column(name = "login_type")
    val loginType: LoginType,
)