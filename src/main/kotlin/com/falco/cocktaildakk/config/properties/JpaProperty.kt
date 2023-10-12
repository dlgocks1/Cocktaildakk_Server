package com.falco.cocktaildakk.config.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component


@Component
@PropertySource("classpath:config.properties")
data class JpaProperty(
    @Value("\${url}")
    var url: String = "",
    @Value("\${username}")
    var username: String = "",
    @Value("\${password}")
    var password: String = "",
)
