package com.falco.cocktaildakk

import com.falco.cocktaildakk.domain.dto.AccessToken
import com.falco.cocktaildakk.repository.AccessTokenRepository
import com.falco.cocktaildakk.repository.RefreshTokenRepository
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

}