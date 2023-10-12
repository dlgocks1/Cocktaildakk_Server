package com.falco.cocktaildakk.service

import com.falco.cocktaildakk.config.properties.JwtProperty
import com.falco.cocktaildakk.domain.token.AccessToken
import com.falco.cocktaildakk.domain.token.RefreshToken
import com.falco.cocktaildakk.domain.token.Token
import com.falco.cocktaildakk.domain.token.TokenType
import com.falco.cocktaildakk.domain.token.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakk.repository.AccessTokenRepository
import com.falco.cocktaildakk.repository.RefreshTokenRepository
import com.falco.cocktaildakk.repository.UserRepository
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import jakarta.servlet.ServletRequest
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtService(
    private val jwtProperty: JwtProperty,
    private val userRepository: UserRepository,
    private val accessTokenRepository: AccessTokenRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun register(userId: String): AccessTokenAndRefreshToken =
        AccessTokenAndRefreshToken(
            accessToken = generateToken(id = userId, tokenType = TokenType.ACCESS)
                .also {
                    accessTokenRepository.save(it as AccessToken)
                },
            refreshToken = generateToken(id = userId, tokenType = TokenType.REFRESH)
                .also {
                    refreshTokenRepository.save(it as RefreshToken)
                }
        )

    fun generateToken(id: String, tokenType: TokenType): Token {
        val (expiration, secret) = when (tokenType) {
            TokenType.ACCESS -> jwtProperty.accesstoken.expiration to jwtProperty.accesstoken.secret
            TokenType.REFRESH -> jwtProperty.refreshtoken.expiration to jwtProperty.refreshtoken.secret
        }
        val expirationDate = Date(System.currentTimeMillis() + expiration)
        return tokenType.generate(
            token = Jwts.builder()
                .setSubject(id)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secret.toByteArray()), SignatureAlgorithm.HS512)
                .compact(),
            expiration = expirationDate,
            userId = id
        )
    }

    fun validateToken(servletRequest: ServletRequest, token: String?): Boolean {
        try {
            val claims =
                Jwts.parserBuilder().setSigningKey(jwtProperty.accesstoken.secret.toByteArray()).build()
                    .parseClaimsJws(token).body
            val expirationDate: Date = claims.expiration
            !expirationDate.before(Date())
        } catch (e: SecurityException) {
            servletRequest.setAttribute("exception", "MalformedJwtException")
            logger.info("잘못된 JWT 서명입니다.")
        } catch (e: MalformedJwtException) {
            servletRequest.setAttribute("exception", "MalformedJwtException")
            logger.info("잘못된 JWT 서명입니다.")
        } catch (e: ExpiredJwtException) {
            servletRequest.setAttribute("exception", "ExpiredJwtException")
            logger.info("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            servletRequest.setAttribute("exception", "UnsupportedJwtException")
            logger.info("지원되지 않는 JWT 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            servletRequest.setAttribute("exception", "IllegalArgumentException")
            logger.info("JWT 토큰이 잘못되었습니다.")
        }
        return false
    }

    fun getAuthentication(token: String?): Authentication {
        val userId = Jwts.parserBuilder().setSigningKey(jwtProperty.accesstoken.secret.toByteArray()).build()
            .parseClaimsJws(token).body.subject
        val users = userRepository.findByIdOrNull(userId)
        return UsernamePasswordAuthenticationToken(users, "")
    }

    fun getIdFromToken(token: String): String =
        Jwts.parserBuilder().setSigningKey(jwtProperty.accesstoken.secret.toByteArray()).build()
            .parseClaimsJws(token).body.subject

    fun getExpirationFromToken(token: String): Date =
        Jwts.parserBuilder().setSigningKey(jwtProperty.accesstoken.secret.toByteArray()).build()
            .parseClaimsJws(token).body.expiration


    companion object {
        private val logger = LoggerFactory.getLogger(JwtService::class.java)
    }

}