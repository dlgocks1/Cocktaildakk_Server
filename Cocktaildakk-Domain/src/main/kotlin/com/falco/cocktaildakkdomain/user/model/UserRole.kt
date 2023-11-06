package com.falco.cocktaildakkdomain.user.model

enum class UserRole(
    val value: String,
    val role: String
) {
    ROLE_USER("ROLE_USER", "유저"),
    ROLE_ADMIN("ROLE_ADMIN", "관리자");
}

