package com.falco.cocktaildakk.security

import com.falco.cocktaildakk.service.JwtService
import com.falco.cocktaildakk.util.Contants.AUTHORIZATION_HEADER
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.filter.GenericFilterBean


class JwtFilter(
    private val jwtService: JwtService
) : GenericFilterBean() {

    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val httpServletRequest = servletRequest as HttpServletRequest
        val jwt = getJwt()
        val requestURI = httpServletRequest.requestURI
        if (!jwt.isNullOrBlank() && jwtService.validateAcessTokenFromRequest(servletRequest, jwt)) {
            val authentication = jwtService.getAuthentication(jwt)
            SecurityContextHolder.getContext().authentication = authentication
            JwtFilter.logger.info("Security Context에 '${authentication.name}' 인증 정보를 저장했습니다, uri: $requestURI")
        } else {
            JwtFilter.logger.info("유효한 JWT 토큰이 없습니다, uri: $requestURI")
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }

    private fun getJwt(): String? {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        return request.getHeader(AUTHORIZATION_HEADER)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(JwtFilter::class.java)
    }
}