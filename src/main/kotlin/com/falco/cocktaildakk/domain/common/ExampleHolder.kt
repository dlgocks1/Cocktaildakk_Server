package com.falco.cocktaildakk.domain.common

import io.swagger.v3.oas.models.examples.Example

data class ExampleHolder(
    val holder: Example,
    val name: String,
    val code: Int = 0
)