package com.falco.cocktaildakk.repository

import com.falco.cocktaildakk.domain.token.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, String>