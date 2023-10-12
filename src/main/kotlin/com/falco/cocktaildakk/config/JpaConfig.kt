package com.falco.cocktaildakk.config

import com.falco.cocktaildakk.config.properties.JpaProperty
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource

@Configuration
class JpaConfiguration(
    private val jpaProperty: JpaProperty
) {
    @Bean
    fun dataSource(): DataSource {
        val config = HikariConfig()
        config.jdbcUrl = jpaProperty.url
        config.username = jpaProperty.username
        config.password = jpaProperty.password
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