package com.falco.cocktaildakkapi.admin.dto

import com.falco.cocktaildakkdomain.cocktail.model.Cocktail
import org.springframework.web.multipart.MultipartFile

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