package com.falco.cocktaildakkapi.recommand.dto

import com.falco.cocktaildakkdomain.cocktail.model.Cocktail

data class KeywordRecommandRes(
    val keyword: String,
    val contents: List<Cocktail>
)