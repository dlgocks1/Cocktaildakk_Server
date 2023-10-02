package com.falco.cocktaildakk.config

import com.falco.cocktaildakk.config.properties.JpaProproperty
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource

@Configuration
class JpaConfiguration(
    private val jpaProproperty: JpaProproperty
) {
    @Bean
    fun dataSource(): DataSource {
        val config = HikariConfig()
        config.jdbcUrl = jpaProproperty.url
        config.username = jpaProproperty.username
        config.password = jpaProproperty.password
        config.driverClassName = "com.mysql.cj.jdbc.Driver" // Set your driver class name
        return HikariDataSource(config)
    }

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource()
        em.setPackagesToScan("com.falco.cocktaildakk.domain")
        em.jpaVendorAdapter = HibernateJpaVendorAdapter()
        return em
    }
}