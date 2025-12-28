plugins {
    kotlin("jvm") version "2.2.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.register<JavaExec>("runMain") {
    group = "application"
    description = "Run a Kotlin main class with Gradle runtimeClasspath (-PmainClass=...)"

    // Usage: ./gradlew runMain -PmainClass=adventOfCode2025.Day08Kt
    mainClass.set(providers.gradleProperty("mainClass").orElse("adventOfCode2025.Day01Kt"))

    maxHeapSize = providers.gradleProperty("heap").orElse("4g").get()

    classpath = sourceSets["main"].runtimeClasspath
    workingDir = project.projectDir
    standardInput = System.`in`
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}