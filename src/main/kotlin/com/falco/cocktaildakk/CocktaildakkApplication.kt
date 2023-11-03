package com.falco.cocktaildakk

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableEncryptableProperties
class CocktaildakkApplication

fun main(args: Array<String>) {
    runApplication<CocktaildakkApplication>(*args)
}
