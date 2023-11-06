package com.falco.cocktaildakkapi.admin.controller

import com.falco.cocktaildakkapi.admin.dto.UploadCocktailReq
import com.falco.cocktaildakkapi.admin.service.AdminService
import com.falco.cocktaildakkapi.auth.service.AuthService
import com.falco.cocktaildakkapi.token.service.JwtService
import com.falco.cocktaildakkcommon.annotation.ApiErrorCodeExample
import com.falco.cocktaildakkcommon.exceptions.UserAuthErrorCode
import com.falco.cocktaildakkcommon.model.CommonResponse
import com.falco.cocktaildakkdomain.cocktail.model.Cocktail
import com.falco.cocktaildakkdomain.token.model.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakkdomain.user.model.LoginType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "AdminController")
@RequestMapping("/admin")
class AdminController(
    private val jwtService: JwtService,
    private val authService: AuthService,
    private val adminService: AdminService
) {

    @Operation(summary = "칵테일 업로드")
    @RequestMapping(value = ["/upload"], consumes = ["multipart/form-data"], method = [RequestMethod.POST])
    fun uploadCocktail(
        @ModelAttribute uploadCocktailReq: UploadCocktailReq,
    ): CommonResponse<Cocktail> {
        return CommonResponse.onSuccess(adminService.uploadCocktail(uploadCocktailReq))
    }

    @GetMapping("/test-register/{userId}")
    @Operation(summary = "테스트 유저 등록", description = "테스트 유저의 정보를 저장합니다.")
    @ApiErrorCodeExample(UserAuthErrorCode::class)
    fun testRegister(
        @Parameter(description = "테스트 유저아이디", example = "1234")
        @PathVariable("userId") userId: String
    ): CommonResponse<AccessTokenAndRefreshToken> {
        authService.register(userId, LoginType.KAKAO) // Register User
        return CommonResponse.onSuccess(jwtService.generateTokenByUserId(userId)) // Register Token
    }
}

