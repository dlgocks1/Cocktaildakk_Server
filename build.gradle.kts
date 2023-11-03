allprojects {
    group = "com.falco"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }

    subprojects {
        dependencies {
//            implementation("org.springframework.boot:spring-boot-starter-web")
        }
    }
}

