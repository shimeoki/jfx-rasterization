plugins {
    id("jfx-rasterization.java-conventions")

    id("base")
    id("java-library")
    id("java-library-distribution")
}

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
        distributionBaseName = "jfx-rasterization"
    }
}
