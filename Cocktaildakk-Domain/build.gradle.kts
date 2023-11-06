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
    with(Dependency) {
        implementation(KOTLIN_REFLECT)
        implementation(SPRING_BOOT_STARTER_SECRUITY)
        implementation(SPRING_BOOT_STARTER_WEB)
        implementation(MYSQL_CONNECTOR)
        implementation(SPRING_BOOT_STARTER_DATA_JPA)
        runtimeOnly(MYSQL_CONNECTOR)
    }
//    runtimeOnly("mysql:mysql-connector-java")
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
