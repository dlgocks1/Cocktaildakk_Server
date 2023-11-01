package com.falco.cocktaildakk.repository

import com.falco.cocktaildakk.domain.user.UserInfo
import org.springframework.data.jpa.repository.JpaRepository

interface UserInfoRepository : JpaRepository<UserInfo, String>

