plugins {
    id("jfx-rasterization.java-conventions")

    id("base")
    id("java-library")
    id("java-library-distribution")

    id("maven-publish")
}

group = "com.github.shimeoki"
version = "1.0.0"

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

            from(components["java"])
        }
    }
}
