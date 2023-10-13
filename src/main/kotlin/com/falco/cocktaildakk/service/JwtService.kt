package com.falco.cocktaildakk.service

import com.falco.cocktaildakk.config.properties.JwtProperty
import com.falco.cocktaildakk.domain.common.CommonErrorCode
import com.falco.cocktaildakk.domain.token.AccessToken
import com.falco.cocktaildakk.domain.token.RefreshToken
import com.falco.cocktaildakk.domain.token.Token
import com.falco.cocktaildakk.domain.token.TokenType
import com.falco.cocktaildakk.domain.token.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakk.exceptions.BaseException
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
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtService(
    private val jwtProperty: JwtProperty,
    private val userRepository: UserRepository,
    private val accessTokenRepository: AccessTokenRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    // 업데이트도 동시에 됨 (키가 동일하기에)
    fun generateTokenByUserId(userId: String): AccessTokenAndRefreshToken =
        AccessTokenAndRefreshToken(
            accessToken = generateTokenByUserId(userId = userId, tokenType = TokenType.ACCESS)
                .also {
                    accessTokenRepository.save(it as AccessToken)
                }.token,
            refreshToken = generateTokenByUserId(userId = userId, tokenType = TokenType.REFRESH)
                .also {
                    refreshTokenRepository.save(it as RefreshToken)
                }.token
        )

    fun generateTokenByUserId(userId: String, tokenType: TokenType): Token {
        val (expiration, secret) = getExpirationAndSecret(tokenType)
        val expirationDate = Date(System.currentTimeMillis() + expiration)
        return tokenType.generate(
            token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secret.toByteArray()), SignatureAlgorithm.HS512)
                .compact(),
            expiration = expirationDate,
            userId = userId
        )
    }


    fun validateAcessTokenFromRequest(servletRequest: ServletRequest, token: String?): Boolean {
        try {
            val claims = Jwts.parserBuilder().setSigningKey(jwtProperty.accesstoken.secret.toByteArray()).build()
                .parseClaimsJws(token).body
            val expirationDate = claims.expiration
            return !expirationDate.before(Date())
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

    private fun getExpirationAndSecret(tokenType: TokenType) = when (tokenType) {
        TokenType.ACCESS -> jwtProperty.accesstoken.expiration to jwtProperty.accesstoken.secret
        TokenType.REFRESH -> jwtProperty.refreshtoken.expiration to jwtProperty.refreshtoken.secret
    }

    fun validateToken(token: String, tokenType: TokenType) {
        val expiration = getExpirationFromToken(token, tokenType)
            ?: throw BaseException(CommonErrorCode.EXPIRED_JWT_EXCEPTION)
        if (expiration.before(Date())) throw BaseException(CommonErrorCode.EXPIRED_JWT_EXCEPTION)
    }

    fun getUserIdFromToken(token: String, tokenType: TokenType): String {
        val (_, secret) = getExpirationAndSecret(tokenType)
        return Jwts.parserBuilder().setSigningKey(secret.toByteArray()).build()
            .parseClaimsJws(token).body.subject
    }

    fun getAuthentication(token: String?): Authentication {
        val userId = Jwts.parserBuilder().setSigningKey(jwtProperty.accesstoken.secret.toByteArray()).build()
            .parseClaimsJws(token).body.subject
        val users = userRepository.findByIdOrNull(userId)
        return UsernamePasswordAuthenticationToken(users, "", listOf(GrantedAuthority { "User" }))
    }

    private fun getExpirationFromToken(token: String, tokenType: TokenType): Date? =
        when (tokenType) {
            TokenType.ACCESS ->
                Jwts.parserBuilder().setSigningKey(jwtProperty.accesstoken.secret.toByteArray()).build()
                    .parseClaimsJws(token).body.expiration

            TokenType.REFRESH ->
                Jwts.parserBuilder().setSigningKey(jwtProperty.refreshtoken.secret.toByteArray()).build()
                    .parseClaimsJws(token).body.expiration
        }


    companion object {
        private val logger = LoggerFactory.getLogger(JwtService::class.java)
    }

}