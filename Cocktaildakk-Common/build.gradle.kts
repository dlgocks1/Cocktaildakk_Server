import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    with(Plugins) {
        id(SPRING_BOOT_FRAMEWORK) version Versions.SPRING_BOOT_FRAMEWORK
        id(DEPENDENCY_MANAGEMENT) version Versions.DEPENDENCY_MANAGEMENT
        kotlin(JVM) version Versions.JVM
        kotlin(SPRING) version Versions.SPRING
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    with(Dependency) {
        implementation(SPRING_BOOT_STARTER_WEB)
        implementation(JASYPT)
        implementation(SPRING_CLOUD_AWS)
        implementation(SPRING_BOOT_STARTER_SECRUITY)
        implementation(SPRING_BOOT_STARTER_CACHE)
        implementation(CAFFEIN_CACHE)
        implementation(SPRING_BOOT_STARTER_WEBMVC_UI)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.bootJar {
    enabled = false
}
tasks.jar {
    enabled = true
}
