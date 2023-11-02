package com.falco.cocktaildakk.service

import com.falco.cocktaildakk.controller.KeywordRecommandRes
import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.domain.common.CommonErrorCode
import com.falco.cocktaildakk.domain.user.User
import com.falco.cocktaildakk.domain.user.UserInfo
import com.falco.cocktaildakk.exceptions.BaseException
import com.falco.cocktaildakk.repository.UserInfoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class RecommandService(
    private val userInfoRepository: UserInfoRepository,
    private val cacheService: CocktailCacheService
) {

    /** 사용자 정보를 이용하여 스코어 정보를 반환한다.
     * @param userInfo 유저 정보
     * @return 점수, 인덱스가 담긴 리스트 */
    fun getDynamicRecommand(user: User): List<Cocktail> {
        val userInfo = userInfoRepository.findByIdOrNull(user.id) ?: throw BaseException(CommonErrorCode.NOT_EXIST_USER)
        return cacheService.getCocktails()
            .asSequence()
            .sortedBy { cocktail ->
                var score = COCKTAIL_SCORE_ZERO
                /** 키워드 점수 계산 */
                score += calDiff(
                    cocktail.keywords,
                    userInfo,
                ) * userInfo.weightKeyword * KEYWORD_WEIGHT
                /** 기주 점수 계산 */
                score += calDiff(
                    cocktail.baseLiquor,
                    userInfo
                ) * userInfo.weightBase * BASE_WEIGHT
                /** 도수 점수 계산 */
                score += DEFAULT_LEVEL_SCORE - (abs(cocktail.alcoholLevel - userInfo.alcoholLevel) * userInfo.weightLevel * LEVEL_WEIGHT)
                score
            }.take(5).toList()
    }

    private fun calDiff(
        tags: String,
        userInfo: UserInfo,
    ): Int {
        val devidedTags = tags.split(',')
        val difference = devidedTags.toSet().minus(userInfo.base.toSet())
        return devidedTags.size - difference.size
    }

    fun randomRecommand(limit: Int = 5): List<Cocktail> {
        return cacheService.getCocktails().shuffled().take(5)
    }

    fun keywordRecommand(user: User): KeywordRecommandRes {
        val userInfo = userInfoRepository.findByIdOrNull(user.id) ?: throw BaseException(CommonErrorCode.NOT_EXIST_USER)
        val keyword = userInfo.keyword.split(",").random()
        return KeywordRecommandRes(
            keyword = keyword,
            contents = cacheService.getCocktails()
                .asSequence()
                .filter {
                    it.keywords.contains(keyword)
                }.take(5).toList()
        )
    }

    fun baseRecommand(user: User): KeywordRecommandRes {
        val userInfo = userInfoRepository.findByIdOrNull(user.id) ?: throw BaseException(CommonErrorCode.NOT_EXIST_USER)
        val keyword = userInfo.base.split(",").random()
        return KeywordRecommandRes(
            keyword = keyword,
            contents = cacheService.getCocktails()
                .asSequence()
                .filter {
                    it.baseLiquor.contains(keyword)
                }.take(5).toList()
        )
    }

    companion object {
        const val DEFAULT_LEVEL_SCORE = 3
        const val COCKTAIL_SCORE_ZERO = 0.0
        const val KEYWORD_WEIGHT = 0.8f
        const val BASE_WEIGHT = 1.2f
        const val LEVEL_WEIGHT = 0.1f
    }
}
