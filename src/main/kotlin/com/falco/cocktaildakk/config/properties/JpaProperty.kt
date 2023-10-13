package com.falco.cocktaildakk.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties(prefix = "jpa.config")
data class JpaProperty(
    var url: String = "",
    var username: String = "",
    var password: String = "",
    var driver: String = ""
)
