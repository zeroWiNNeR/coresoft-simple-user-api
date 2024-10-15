allprojects {
    group = "ru.vekovshinin"
    repositories {
        mavenCentral()
    }
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    apply(plugin = "java")
}

buildscript {
    extra.apply {
        set("springBootVersion", "3.3.4")
        set("lombokVersion", "1.18.32")
        set("jakartaPersistenceApiVersion", "3.1.0")
        set("springdocOpenapiStarterVersion", "2.6.0")
        set("springCloudStarterOpenfeignVersion", "4.1.3")
        set("springCloudStarterGatewayVersion", "4.1.5")
    }
}
