package com.falco.cocktaildakk.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenUtil(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration}") private val expiration: Long
) {
    fun generateToken(userId: String): String {
        return createToken(userId)
    }

    private fun createToken(userId: String): String {
        val now = Date()
        val claims: Map<String, Any> = mapOf("userId" to userId)
        val expirationDate = Date(now.time + expiration * 1000)
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userId)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray()), SignatureAlgorithm.HS512)
            .compact()
    }

//    fun validateToken(token: String, userId: String): Boolean {
//        val username = getUsernameFromToken(token)
//        return username == userDetails.username && !isTokenExpired(token)
//    }

//    fun getUsernameFromToken(token: String): String {
//        return getClaimFromToken(token, Claims::getSubject)
//    }
//
//    private fun getClaimFromToken(token: String, claimsResolver: Function<Claims, String>): String {
//        val claims = getAllClaimsFromToken(token)
//        return claimsResolver.apply(claims)
//    }
//
//    private fun getAllClaimsFromToken(token: String): Claims {
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
//    }
//
//
//    private fun isTokenExpired(token: String): Boolean {
//        val expiration = getExpirationDateFromToken(token)
//        return expiration.before(Date())
//    }
//
//    private fun getExpirationDateFromToken(token: String): Date {
//        return getClaimFromToken(token, Claims::getExpiration)
//    }

}