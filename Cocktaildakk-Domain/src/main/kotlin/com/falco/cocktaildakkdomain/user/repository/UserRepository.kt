package com.falco.cocktaildakkdomain.user.repository

import com.falco.cocktaildakkdomain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String>
