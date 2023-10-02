package com.falco.cocktaildakk.repository

import com.falco.cocktaildakk.domain.dto.Cocktail
import org.springframework.data.jpa.repository.JpaRepository

interface CocktailRepository : JpaRepository<Cocktail, Long>
