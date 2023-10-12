package com.falco.cocktaildakk.domain.common

import org.springframework.stereotype.Component


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class ExplainError(val value: String = "")
