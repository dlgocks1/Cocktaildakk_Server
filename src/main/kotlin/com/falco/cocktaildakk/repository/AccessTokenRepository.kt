package com.falco.cocktaildakk.repository

import com.falco.cocktaildakk.domain.dto.AccessToken
import org.springframework.data.repository.CrudRepository

interface AccessTokenRepository : CrudRepository<AccessToken, String>