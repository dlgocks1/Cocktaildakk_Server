package com.falco.cocktaildakkcommon.annotation

import org.springframework.stereotype.Component


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class ExplainError(val value: String = "")
