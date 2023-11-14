package com.falco.cocktaildakkapi.cocktail.service

import com.falco.cocktaildakkapi.recommand.CocktailRecommandCore
import com.falco.cocktaildakkcommon.exceptions.CommonErrorCode
import com.falco.cocktaildakkcommon.exceptions.model.BaseException
import com.falco.cocktaildakkcommon.model.PageResponse
import com.falco.cocktaildakkdomain.cocktail.model.Cocktail
import com.falco.cocktaildakkdomain.user.model.User
import com.falco.cocktaildakkdomain.user.repository.UserInfoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CocktailService(
    private val cacheService: CocktailCacheService,
    private val recommandCore: CocktailRecommandCore,
    private val userInfoRepository: UserInfoRepository
) {

    fun getCocktailById(id: Long): Cocktail =
        cacheService.getCocktails().find { it.id == id } ?: throw BaseException(CommonErrorCode.NOT_EXIST_COCKTAIL)

    fun queryCocktail(query: String): List<Cocktail> {
        return cacheService.getCocktails().asSequence().filter {
            // TODO 이게 맞나??
            it.baseLiquor.contains(query) ||
                    it.keywords.contains(query) ||
                    it.englishName.contains(query) ||
                    it.koreanName.contains(query) ||
                    it.ingredients.contains(query) ||
                    it.mixingMethod.contains(query)
//                    it.description.contains(query)
        }.take(10).toList()
    }

    fun getAllPage(page: Int, size: Int): PageResponse<Cocktail> {
        val cocktails = cacheService.getCocktails()
        val content = cocktails.asSequence().drop(page * size).take(size).toList()
        return PageResponse(
            isLast = content.count() <= page * size,
            totalCnt = cocktails.size.toLong(),
            contents = content
        )
    }

    fun getRecommandPage(user: User, page: Int, size: Int): PageResponse<Cocktail> {
        val cocktails = cacheService.getCocktails()
        val userInfo = userInfoRepository.findByIdOrNull(user.id) ?: throw BaseException(CommonErrorCode.NOT_EXIST_USER)
        val content = cocktails
            .sortedBy { recommandCore.getScoreByUser(userInfo = userInfo, cocktail = it) }
            .drop(page * size)
            .take(size)
        return PageResponse(
            isLast = content.count() <= page * size,
            totalCnt = cocktails.size.toLong(),
            contents = content
        )
    }

    fun reloadCocktail(): String {
        cacheService.reloadCocktails()
        return "캐시 리로드 성공"
    }
}