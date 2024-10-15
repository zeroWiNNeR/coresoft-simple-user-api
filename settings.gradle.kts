pluginManagement {
    repositories {
        maven("https://plugins.gradle.org/m2/")
        gradlePluginPortal()
    }
}

rootProject.name = "coresoft-simple-user-api"

include(":common")
include(":user-service")
include(":role-service")
include(":gateway-service")
