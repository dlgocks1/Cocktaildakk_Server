package com.falco.cocktaildakk.service

import com.falco.cocktaildakk.controller.KeywordRecommandRes
import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.domain.common.CommonErrorCode
import com.falco.cocktaildakk.domain.user.User
import com.falco.cocktaildakk.exceptions.BaseException
import com.falco.cocktaildakk.repository.UserInfoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RecommandService(
    private val userInfoRepository: UserInfoRepository,
    private val cacheService: CocktailCacheService,
    private val cocktailRecommandCore: CocktailRecommandCore
) {

    fun getDynamicRecommand(user: User): List<Cocktail> {
        val userInfo = userInfoRepository.findByIdOrNull(user.id) ?: throw BaseException(CommonErrorCode.NOT_EXIST_USER)
        return cacheService.getCocktails()
            .sortedBy { cocktailRecommandCore.getScoreByUser(userInfo = userInfo, cocktail = it) }
            .take(5)
            .toList()
    }

    fun randomRecommand(): List<Cocktail> {
        return cacheService.getCocktails().shuffled().take(10)
    }

    fun keywordRecommand(user: User): KeywordRecommandRes {
        val userInfo = userInfoRepository.findByIdOrNull(user.id) ?: throw BaseException(CommonErrorCode.NOT_EXIST_USER)
        val keyword = userInfo.keywordList.random()
        return KeywordRecommandRes(
            keyword = keyword,
            contents = cacheService.getCocktails()
                .asSequence()
                .filter { it.keywords.contains(keyword) }
                .take(5)
                .toList()
        )
    }

    fun baseRecommand(user: User): KeywordRecommandRes {
        val userInfo = userInfoRepository.findByIdOrNull(user.id) ?: throw BaseException(CommonErrorCode.NOT_EXIST_USER)
        val keyword = userInfo.baseList.random()
        return KeywordRecommandRes(
            keyword = keyword,
            contents = cacheService.getCocktails()
                .asSequence()
                .filter { it.baseLiquor.contains(keyword) }
                .take(5)
                .toList()
        )
    }


}
