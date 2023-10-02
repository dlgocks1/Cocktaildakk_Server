package com.falco.cocktaildakk.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {

    @GetMapping
    fun healthTest(): String {
        return "I'm Healthy!"
    }
}