package com.falco.cocktaildakkapi.recommand

import com.falco.cocktaildakkdomain.cocktail.model.Cocktail
import com.falco.cocktaildakkdomain.user.model.UserInfo
import org.springframework.stereotype.Component
import kotlin.math.abs

@Component
class CocktailRecommandCore {

    fun getScoreByUser(userInfo: UserInfo, cocktail: Cocktail): Double {
        var score = COCKTAIL_SCORE_ZERO
        /** 키워드 점수 계산 */
        score += calDiff(
            cocktailsTags = cocktail.keywords.split(","),
            userTags = userInfo.keywordList,
        ) * userInfo.weightKeyword * KEYWORD_WEIGHT
        /** 기주 점수 계산 */
        score += calDiff(
            cocktailsTags = cocktail.baseLiquor.split(","),
            userTags = userInfo.baseList,
        ) * userInfo.weightBase * BASE_WEIGHT
        /** 도수 점수 계산 */
        score += DEFAULT_LEVEL_SCORE - (abs(cocktail.alcoholLevel - userInfo.alcoholLevel) * userInfo.weightLevel * LEVEL_WEIGHT)
        return score
    }

    fun compareBy(selector: (Cocktail) -> Comparable<*>?): Comparator<Cocktail> =
        Comparator { a, b -> compareValuesBy(a, b, selector) }

    private fun calDiff(
        cocktailsTags: List<String>,
        userTags: List<String>,
    ): Int {
        return cocktailsTags.toSet().intersect(userTags.toSet()).size
    }

    companion object {
        const val DEFAULT_LEVEL_SCORE = 3
        const val COCKTAIL_SCORE_ZERO = 0.0
        const val KEYWORD_WEIGHT = 0.8f
        const val BASE_WEIGHT = 1.2f
        const val LEVEL_WEIGHT = 0.1f
    }
}
