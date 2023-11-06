package com.falco.cocktaildakkdomain.user.repository

import com.falco.cocktaildakkdomain.user.model.UserInfo
import org.springframework.data.jpa.repository.JpaRepository

interface UserInfoRepository : JpaRepository<UserInfo, String>

