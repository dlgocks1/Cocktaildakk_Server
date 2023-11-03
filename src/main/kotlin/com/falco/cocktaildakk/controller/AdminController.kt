package com.falco.cocktaildakk.controller

import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.domain.common.ApiErrorCodeExample
import com.falco.cocktaildakk.domain.common.CommonResponse
import com.falco.cocktaildakk.domain.token.response.AccessTokenAndRefreshToken
import com.falco.cocktaildakk.domain.user.LoginType
import com.falco.cocktaildakk.domain.user.UserAuthErrorCode
import com.falco.cocktaildakk.service.AdminService
import com.falco.cocktaildakk.service.AuthService
import com.falco.cocktaildakk.service.JwtService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

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

data class UploadCocktailReq(
    val englishName: String,
    val koreanName: String,
    val alcoholLevel: Int,
    val baseLiquor: String,
    val mixingMethod: String,
    val ingredients: String,
    val keywords: String,
    val description: String,
    val image: MultipartFile,
    val listImage: MultipartFile,
) {
    fun convertToCocktail(iamgeUrl: String, listImageUrl: String): Cocktail {
        return Cocktail(
            englishName = this.englishName,
            koreanName = this.koreanName,
            alcoholLevel = this.alcoholLevel,
            baseLiquor = this.baseLiquor,
            mixingMethod = this.mixingMethod,
            ingredients = this.ingredients,
            keywords = this.keywords,
            description = this.description,
            imgUrl = iamgeUrl,
            listImgUrl = listImageUrl
        )
    }
}