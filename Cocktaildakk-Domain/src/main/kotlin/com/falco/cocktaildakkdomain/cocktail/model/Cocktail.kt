package com.falco.cocktaildakkdomain.cocktail.model

import jakarta.persistence.*

@Entity(name = "cocktail")
data class Cocktail(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0L,
    @Column(name = "english_name")
    val englishName: String,
    @Column(name = "korean_name")
    val koreanName: String,
    @Column(name = "alcohol_level")
    val alcoholLevel: Int,
    /** ex) 보드카,데킬라 */
    @Column(name = "base_liquor")
    val baseLiquor: String,
    @Column(name = "mixing_method")
    val mixingMethod: String,
    val ingredients: String,
    /** ex) 간단한, 복잡한 */
    val keywords: String,
    val description: String,
    @Column(name = "img_url")
    val imgUrl: String = "",
    @Column(name = "list_url")
    val listImgUrl: String = "",
)