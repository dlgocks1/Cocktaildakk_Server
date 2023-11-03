package com.falco.cocktaildakkdomain.user.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity(name = "user")
data class User(
    @Id
    @Column(name = "id")
    var id: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "login_type")
    val loginType: LoginType,
    @Enumerated(EnumType.STRING)
    var gender: Gender = Gender.UNKNOWN,
    var age: Int = -1,
    var nickname: String = "익명",
    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.ROLE_USER,
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return role.value.split(",").map { SimpleGrantedAuthority(it) }
    }

    override fun getPassword(): String = "password"
    override fun getUsername(): String = nickname

    override fun isAccountNonExpired(): Boolean {
        return false
    }

    override fun isAccountNonLocked(): Boolean {
        return false
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    override fun isEnabled(): Boolean {
        return false
    }

}
