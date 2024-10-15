version = "0.0.1"

java {
    withSourcesJar()
}

val springBootVersion = rootProject.extra["springBootVersion"]
val lombokVersion = rootProject.extra["lombokVersion"]
val jakartaPersistenceApiVersion = rootProject.extra["jakartaPersistenceApiVersion"]

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")

    // Other
    implementation("jakarta.persistence:jakarta.persistence-api:$jakartaPersistenceApiVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
}
