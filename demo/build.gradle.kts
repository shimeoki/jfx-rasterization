plugins {
    id("jfx-rasterization.java-conventions")
    application
}

version = "0.1.0"

dependencies {
    implementation(project(":lib"))
}

javafx {
    modules("javafx.graphics", "javafx.fxml", "javafx.controls")
    configuration = "implementation"
}

application {
    mainModule = "com.github.shimeoki.jfx.rasterization.demo"
    mainClass = "com.github.shimeoki.jfx.rasterization.demo.App"
}
