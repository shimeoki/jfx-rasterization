plugins {
    id("jfx-rasterization.java-conventions")
    application
}

version = "0.5.0"

dependencies {
    implementation(project(":lib"))
}

javafx {
    modules("javafx.graphics", "javafx.fxml", "javafx.controls")
    configuration = "implementation"
}

application {
    mainModule = "io.github.shimeoki.jfx.rasterization.demo"
    mainClass = "io.github.shimeoki.jfx.rasterization.demo.App"
}
