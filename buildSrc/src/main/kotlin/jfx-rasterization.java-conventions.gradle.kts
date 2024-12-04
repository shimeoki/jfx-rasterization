plugins {
    id("java")
    id("org.openjfx.javafxplugin")
}

group = "io.github.shimeoki"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

javafx {
    version = "21"
}

tasks.test {
    useJUnitPlatform()
}
