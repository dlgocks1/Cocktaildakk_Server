package com.falco.cocktaildakk.repository

import com.falco.cocktaildakk.domain.cocktail.Cocktail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CocktailRepository : JpaRepository<Cocktail, Long> {

    @Query(value = "SELECT * FROM cocktail ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    fun findRandomCocktails(@Param("limit") limit: Int): List<Cocktail>

    fun findTop5ByKeywordsContains(keyword: String): List<Cocktail>
    
    fun findTop5ByBaseLiquorContaining(keyword: String): List<Cocktail>
}
