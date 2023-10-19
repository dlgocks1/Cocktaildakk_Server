package com.falco.cocktaildakk.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "AdminController")
@RequestMapping("/admin")
class AdminController() {

    @GetMapping
    @Tag(name = "칵테일 업로드")
    fun updateCocktail(): String {
        return "준비 중"
    }

}