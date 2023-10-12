package com.falco.cocktaildakk

import com.falco.cocktaildakk.domain.token.AccessToken
import com.falco.cocktaildakk.repository.AccessTokenRepository
import com.falco.cocktaildakk.repository.RefreshTokenRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@SpringBootTest
class TokenCrudTest {

    @Autowired
    private lateinit var accessTokenRepository: AccessTokenRepository

    @Autowired
    private lateinit var refreshTokenRepository: RefreshTokenRepository

    @Test
    @DisplayName("AccessToken 생성 및 삭제 테스트")
    fun accessTokenSaveTest() {
        accessTokenRepository.save(
            AccessToken(
                userId = "-999",
                token = "This-is-Token",
                expiration = Date(),
            )
        )
        assert(accessTokenRepository.findByIdOrNull("-999") != null)
        accessTokenRepository.deleteById("-999")
        assert(accessTokenRepository.findByIdOrNull("-999") == null)
    }

    @Test
    fun generateJwtTokenTest() {
        println(generateToken("asdzxcwqe"))
    }

    fun generateToken(userId: String): String {
        return createToken(userId)
    }

    private fun createToken(userId: String): String {
        val now = Date()
        val claims: Map<String, Any> = mapOf("userId" to userId)
        val expirationDate = Date(now.time + 1000 * 1000)
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userId)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(
                Keys.hmacShaKeyFor("rkGU45258GGhiolLO2465TFY5345kGU45258GGhiolLO2465TFY5345rkGU45258GGhiolLO2465TFY5345kGU45258GGhiolLO2465TFY5345".toByteArray()),
                SignatureAlgorithm.HS512
            )
            .compact()
    }

}