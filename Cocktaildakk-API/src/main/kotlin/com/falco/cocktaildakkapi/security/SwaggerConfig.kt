package com.falco.cocktaildakkapi.security

import com.falco.cocktaildakkcommon.annotation.ApiErrorCodeExample
import com.falco.cocktaildakkcommon.exceptions.ExampleHolder
import com.falco.cocktaildakkcommon.exceptions.model.BaseErrorCode
import com.falco.cocktaildakkcommon.exceptions.model.ErrorReason
import com.falco.cocktaildakkcommon.util.Contants.AUTHORIZATION_HEADER
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.slf4j.LoggerFactory
import org.springdoc.core.customizers.OperationCustomizer
import org.springdoc.core.utils.SpringDocUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.CookieValue
import java.util.function.Consumer
import kotlin.reflect.KClass

@Configuration
class OpenApiConfig {
    init {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(AuthenticationPrincipal::class.java, CookieValue::class.java)
    }

    @Bean
    fun openAPI(): OpenAPI {
        val info = Info()
            .title("Cocktaildakk API Documents")
            .version("0.0.1")
            .description("Server for Cocktaildakk")

        // Security 스키마 설정 (Header Authentication with X-AUTH-TOKEN)
        val headerAuth = SecurityScheme()
            .type(SecurityScheme.Type.APIKEY)
            .name(AUTHORIZATION_HEADER)
            .`in`(SecurityScheme.In.HEADER)

        // Security 요청 설정
        val addSecurityItem = SecurityRequirement()
        addSecurityItem.addList(AUTHORIZATION_HEADER)

        return OpenAPI()
            // Security 인증 컴포넌트 설정
            .components(Components().addSecuritySchemes(AUTHORIZATION_HEADER, headerAuth))
            // API 마다 Security 인증 컴포넌트 설정
            .addSecurityItem(addSecurityItem)
            .info(info)
    }

    @Bean
    fun customize(): OperationCustomizer {
        return OperationCustomizer { operation, handlerMethod ->
            val apiErrorCodeExample = handlerMethod.getMethodAnnotation(ApiErrorCodeExample::class.java)
            // ApiErrorCodeExample 어노테이션 단 메소드 적용
            if (apiErrorCodeExample != null) {
                val errorCodes: Array<out KClass<out BaseErrorCode>> = apiErrorCodeExample.value
                generateErrorCodeResponseExample(operation, errorCodes)
            }
            operation
        }
    }

    private fun generateErrorCodeResponseExample(
        operation: Operation, errorCodeList: Array<out KClass<out BaseErrorCode>>
    ) {
        val responses = operation.responses
        val statusWithExampleHolders: MutableMap<Int, MutableList<ExampleHolder>> = HashMap()

        for (errorType in errorCodeList) {
            val enumConstants = errorType.java.enumConstants // Access enum constants
            for (baseErrorCode in enumConstants) {
                val errorReason = baseErrorCode?.errorReason

                if (errorReason != null) {
                    val code = errorReason.httpStatus.value()
                    val name = errorReason.code
                    val explainError = baseErrorCode.errorReason.message
                    val exampleHolder = ExampleHolder(
                        holder = getSwaggerExample(explainError, errorReason),
                        code = code,
                        name = name
                    )
                    statusWithExampleHolders
                        .computeIfAbsent(code) { ArrayList() }
                        .add(exampleHolder)
                }
            }
        }
        addExamplesToResponses(responses, statusWithExampleHolders)
    }

    private fun getSwaggerExample(value: String, errorReason: ErrorReason): Example {
        return Example().apply {
            description(value)
            this.value = errorReason
        }
    }

    private fun addExamplesToResponses(
        responses: ApiResponses, statusWithExampleHolders: Map<Int, List<ExampleHolder>>
    ) {
        statusWithExampleHolders.forEach { (status, v) ->
            val content = Content()
            val mediaType = MediaType()
            val apiResponse = ApiResponse()
            v.forEach(
                Consumer { exampleHolder: ExampleHolder ->
                    mediaType.addExamples(
                        exampleHolder.name, exampleHolder.holder
                    )
                })
            content.addMediaType("application/json", mediaType)
            apiResponse.content = content
            responses.addApiResponse(status.toString(), apiResponse)
        }
    }

    companion object {

        private val log = LoggerFactory.getLogger(OpenApiConfig::class.java)
    }
}
