package com.falco.cocktaildakkcommon.annotation

import com.falco.cocktaildakkcommon.exceptions.model.BaseErrorCode
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiErrorCodeExample(
    vararg val value: KClass<out BaseErrorCode>
)
