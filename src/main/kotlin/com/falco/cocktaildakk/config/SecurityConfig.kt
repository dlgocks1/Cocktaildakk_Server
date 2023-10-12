package com.falco.cocktaildakk.config

import com.falco.cocktaildakk.security.JwtAccessDeniedHandler
import com.falco.cocktaildakk.security.JwtAuthenticationEntryPoint
import com.falco.cocktaildakk.security.JwtSecurityConfig
import com.falco.cocktaildakk.service.JwtService
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
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

    fun configure(web: WebSecurity) {
        web.ignoring()
            .requestMatchers(
                "/h2-console/**",
                "/favicon.ico",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/api-docs/**",
                "/v3/api-docs/**",
                "/users/**", // TODO : 인가처리시 제외하기
                "/health/**"
            )
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
                    .requestMatchers("/webjars/**").permitAll()
                    .requestMatchers("/webjars/**").permitAll()
                    .requestMatchers("/image/**").permitAll()
                    .requestMatchers("/users/refresh").permitAll()
                    .requestMatchers("/profile").permitAll()
                    .requestMatchers("/login/**").permitAll()
                    .requestMatchers("/test/**").permitAll()
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
            }.apply(JwtSecurityConfig(jwtService))
        return http.build()
    }

}