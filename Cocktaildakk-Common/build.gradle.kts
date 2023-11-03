import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
}
group = "com.falco"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("javax.persistence:javax.persistence-api:2.2")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    /** Jasypt */
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    /** aws-s3 */
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

    /** Caching */
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")

    /** Authority */
    implementation("org.springframework.boot:spring-boot-starter-security")

    /** Data */
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    /** String Parser */
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.json:json:20230618")

    /** JWT */
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    runtimeOnly("mysql:mysql-connector-java")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootJar {
    enabled = false
}
tasks.jar {
    enabled = true
}
