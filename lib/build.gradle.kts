plugins {
    `java-library`
    id("org.openjfx.javafxplugin") version "0.1.0"
}

version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

javafx {
    version = "21"
    modules("javafx.graphics")
    configuration = "api"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

