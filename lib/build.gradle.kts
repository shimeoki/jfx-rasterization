plugins {
    id("jfx-rasterization.java-conventions")
    id("java-library")
}

version = "1.0.0"

javafx {
    modules("javafx.graphics")
    configuration = "api"
}

tasks.jar {
    archiveBaseName.set(rootProject.name)
}
