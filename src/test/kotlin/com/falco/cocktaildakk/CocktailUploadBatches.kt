package com.falco.cocktaildakk

//@SpringBootTest
//@ActiveProfiles(profiles = ["local"])
//class CocktailUploadBatches {
//
//    @Autowired
//    private lateinit var cocktailRepository: CocktailRepository
//
//    @Test
//    @DisplayName("잘못된 칵테일 조회 시 에러가 발생한다.")
//    fun cocktailGetTest() {
//        assertThrows<NoContentException> {
//            println(cocktailRepository.findByIdOrNull(88))
//        }
//    }
//
//    @Test
//    fun saveDumpData() {
//        val file = File("src/main/resources/dump/cocktail_recipes_dump.xlsx")
//        val workbook = WorkbookFactory.create(file)
//        cocktailRepository.deleteAll()
//
//        val sheet = workbook.getSheetAt(0)
//        for (rowIndex in 1 until sheet.physicalNumberOfRows) {
//            val row = sheet.getRow(rowIndex)
//            val cocktail = Cocktail(
//                id = rowIndex.toLong(),
//                englishName = row.getCell(1).stringCellValue,
//                koreanName = row.getCell(2).stringCellValue,
//                alcoholLevel = row.getCell(3).numericCellValue.toInt(),
//                baseLiquor = row.getCell(4).stringCellValue,
//                mixingMethod = row.getCell(5).stringCellValue,
//                ingredients = row.getCell(6).stringCellValue,
//                keywords = row.getCell(7).stringCellValue,
//                description = row.getCell(8).stringCellValue
//            )
//            cocktailRepository.save(cocktail)
//        }
//
//        workbook.close()
//    }
//}
