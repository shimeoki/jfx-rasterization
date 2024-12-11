plugins {
    id("jfx-rasterization.java-conventions")

    id("base")
    id("java-library")
    id("java-library-distribution")

    id("maven-publish")
}

version = "1.0.2"

base {
    archivesName = rootProject.name
}

java {
    withJavadocJar()
    withSourcesJar()
}

javafx {
    modules("javafx.graphics")
    configuration = "api"
}

distributions {
    main {
        distributionBaseName = rootProject.name
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = rootProject.name

            pom {
                name = rootProject.name
                description = "A JavaFX shape rasterization library."
                url = "https://github.com/shimeoki/jfx-rasterization"

                licenses {
                    license {
                        name = "MIT License"
                        url = "https://github.com/shimeoki/jfx-rasterization/blob/main/LICENSE"
                    }
                }
            }

            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "github-packages"
            url = uri("https://maven.pkg.github.com/shimeoki/jfx-rasterization")

            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
