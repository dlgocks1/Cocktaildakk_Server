package com.falco.cocktaildakkapi.security

import com.falco.cocktaildakkapi.security.jwt.JwtAccessDeniedHandler
import com.falco.cocktaildakkapi.security.jwt.JwtAuthenticationEntryPoint
import com.falco.cocktaildakkapi.security.jwt.JwtSecurityConfig
import com.falco.cocktaildakkapi.token.service.JwtService
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class SecurityConfig(
    private val jwtService: JwtService,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring()
                .requestMatchers(
                    "/h2-console/**",
                    "/favicon.ico",
                    "/swagger-ui/**",
                    "/swagger-resources/**",
                    "/api-docs/**",
                    "/v3/api-docs/**",
                    "/auth/**",
                    "/users/**", // TODO : 인가처리시 제외하기
                    "/health/**"
                )
        }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .sessionManagement { sessionManagement: SessionManagementConfigurer<HttpSecurity?> ->
                sessionManagement.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .headers { header ->
                header.frameOptions { it.sameOrigin() }
            }
            .exceptionHandling { exceptionHandling ->
                exceptionHandling
                    .accessDeniedHandler(jwtAccessDeniedHandler)
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            }
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/webjars/**", "/image/**", "/users/refresh").permitAll()
                    .requestMatchers("/profile").permitAll()
                    .requestMatchers("/favicon.ico").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/login/**").permitAll()
                    .requestMatchers("/health/**").permitAll()
                    .requestMatchers("/test/**").permitAll()
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/")
                    .permitAll() // EB에서 HelathCheck를 "/"으로 함 200을 반환해야함 -> https://stackoverflow.com/questions/50212033/awselb-health-is-failing-or-not-available-for-all-instances
                    .requestMatchers("/admin/**").permitAll()
//                    .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                    .anyRequest()
                    .authenticated()
            }.apply(JwtSecurityConfig(jwtService))
        return http.build()
    }
}
