import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    with(Plugins) {
        id(SPRING_BOOT_FRAMEWORK) version Versions.SPRING_BOOT_FRAMEWORK
        id(DEPENDENCY_MANAGEMENT) version Versions.DEPENDENCY_MANAGEMENT
        kotlin(JVM) version Versions.JVM
        kotlin(SPRING) version Versions.SPRING
        kotlin(JPA) version Versions.JPA
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":Cocktaildakk-Common"))
    implementation(project(":Cocktaildakk-Domain"))

    with(Dependency) {
        implementation(JASYPT)
        implementation(SPRING_BOOT_STARTER_DATA_JPA)
        implementation(SPRING_CLOUD_AWS)
        implementation(JSON)
        implementation(SPRING_BOOT_STARTER_WEB)
        implementation(SPRING_BOOT_STARTER_WEBFLUX)
        implementation(SPRING_BOOT_STARTER_WEBMVC_UI)
        implementation(SPRING_BOOT_STARTER_CACHE)
        implementation(CAFFEIN_CACHE)
        implementation(SPRING_BOOT_STARTER_SECRUITY)
        implementation(JWT_API)
        implementation(JWT_IMPL)
        implementation(JWT_JACKSON)

        //    /** String Parser */
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.google.code.gson:gson:2.8.9")
        implementation("org.json:json:20230618")
        testImplementation(SPRING_BOOT_STARTER_TEST)
        testImplementation(SPRING_BOOT_STARTER_SECRURITY_TEST)
    }
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

tasks.withType<Jar> {
    enabled = true
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.jar {
    enabled = false
}