package com.falco.cocktaildakkapi

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@ComponentScan(
    basePackages = [
        "com.falco.cocktaildakkcommon",
        "com.falco.cocktaildakkdomain",
        "com.falco.cocktaildakkapi",
    ],
)
@EnableScheduling
@EnableEncryptableProperties
class CocktaildakkApplication

fun main(args: Array<String>) {
    runApplication<CocktaildakkApplication>(*args)
}
