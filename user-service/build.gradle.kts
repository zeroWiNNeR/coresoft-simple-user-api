plugins {
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

version = "0.0.1"

val springCloudStarterOpenfeignVersion = rootProject.extra["springCloudStarterOpenfeignVersion"]
val springdocOpenapiStarterVersion = rootProject.extra["springdocOpenapiStarterVersion"]
val lombokVersion = rootProject.extra["lombokVersion"]

dependencies {
    implementation(project(":common"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:$springCloudStarterOpenfeignVersion")

    // Database
    implementation("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocOpenapiStarterVersion")

    // Other
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
