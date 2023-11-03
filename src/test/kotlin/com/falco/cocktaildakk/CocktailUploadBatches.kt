package com.falco.cocktaildakk

import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.repository.CocktailRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.io.File

@SpringBootTest
@ActiveProfiles(profiles = ["local"])
//@ContextConfiguration(locations = ["classpath:WEB-INF/application-common.xml"])
class CocktailUploadBatches {

    @Autowired
    private lateinit var cocktailRepository: CocktailRepository

    //        https://cocktaildakk-s3.s3.ap-northeast-2.amazonaws.com/list/21stCentury.webp
    //        https://cocktaildakk-s3.s3.ap-northeast-2.amazonaws.com/list/AlaskaIcedTea.webp
    val BASE_S3_URL = "https://cocktaildakk-s3.s3.ap-northeast-2.amazonaws.com"

    @Test
    fun saveDumpData() {
        cocktailRepository.deleteAll()
        val file = File("src/main/resources/dump/cocktail_raw_data.json")
        val objectMapper = jacksonObjectMapper()
        val jsonContent = file.readText()
        val cocktailList: List<Cocktail> = objectMapper.readValue(jsonContent)
        cocktailRepository.saveAll(cocktailList.map {
            it.copy(
                imgUrl = BASE_S3_URL + "/main/${it.englishName.replace(" ", "")}.webp",
                listImgUrl = BASE_S3_URL + "/list/${it.englishName.replace(" ", "")}.webp"
            )
        })
    }

}