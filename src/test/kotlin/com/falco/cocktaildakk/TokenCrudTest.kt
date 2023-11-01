package com.falco.cocktaildakk

//@SpringBootTest
//class TokenCrudTest {
//
//    @Autowired
//    private lateinit var accessTokenRepository: AccessTokenRepository
//
//    @Autowired
//    private lateinit var refreshTokenRepository: RefreshTokenRepository
//
//    @Test
//    @DisplayName("AccessToken 생성 및 삭제 테스트")
//    fun accessTokenSaveTest() {
//        accessTokenRepository.save(
//            AccessToken(
//                userId = "-999",
//                token = "This-is-Token",
//                expiration = 155205005,
//            )
//        )
//        assert(accessTokenRepository.findByIdOrNull("-999") != null)
//        accessTokenRepository.deleteById("-999")
//        assert(accessTokenRepository.findByIdOrNull("-999") == null)
//    }
//
//    @Test
//    fun generateJwtTokenTest() {
//        println(generateToken("asdzxcwqe"))
//    }
//
//    fun generateToken(userId: String): String {
//        return createToken(userId)
//    }
//
//    private fun createToken(userId: String): String {
//        val now = Date()
//        val claims: Map<String, Any> = mapOf("userId" to userId)
//        val expirationDate = Date(now.time + 1000 * 1000)
//        return Jwts.builder()
//            .setClaims(claims)
//            .setSubject(userId)
//            .setIssuedAt(now)
//            .setExpiration(expirationDate)
//            .signWith(
//                Keys.hmacShaKeyFor("rkGU45258GGhiolLO2465TFY5345kGU45258GGhiolLO2465TFY5345rkGU45258GGhiolLO2465TFY5345kGU45258GGhiolLO2465TFY5345".toByteArray()),
//                SignatureAlgorithm.HS512
//            )
//            .compact()
//    }
//
//}