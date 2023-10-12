package com.falco.cocktaildakk.repository

import com.falco.cocktaildakk.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String>
