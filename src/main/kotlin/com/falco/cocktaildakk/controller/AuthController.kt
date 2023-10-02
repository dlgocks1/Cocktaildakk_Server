package com.falco.cocktaildakk.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("auth")
class AuthController {


    @PostMapping("/login")
    fun login() {

    }

}