package com.falco.cocktaildakk.service

import org.springframework.stereotype.Service

@Service
class JwtTokenService(
    private val jwtTokenUtil: JwtTokenUtil,
) {
//    fun generateToken(user: User): String {
//        val userDetails = MyUserDetails(user)
//        val token = jwtTokenUtil.generateToken(userDetails)
//        val expiration = jwtTokenUtil.getExpirationDateFromToken(token)
//
//        val jwtToken = JwtToken(token, expiration, user.id)
//
//        // Store the JWT token in Redis
//        redisTemplate.opsForValue().set(user.id, jwtToken, expiration.time, TimeUnit.MILLISECONDS)
//
//        return token
//    }
}