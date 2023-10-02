package com.falco.cocktaildakk

import com.falco.cocktaildakk.domain.dto.Cocktail
import com.falco.cocktaildakk.repository.CocktailRepository
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.io.File

@SpringBootTest
class CocktaildakkApplicationTests {

	@Autowired
	private lateinit var cocktailRepository : CocktailRepository

	@Test
	fun cocktailGetTest() {
		println(cocktailRepository.findByIdOrNull(87))
		println(cocktailRepository.findByIdOrNull(88))
	}

}
