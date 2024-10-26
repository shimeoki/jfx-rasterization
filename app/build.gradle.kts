plugins {
    id("jfx-rasterization.java-conventions")
    application
}

version = "0.0.1"

dependencies {
    implementation(project(":lib"))
}

javafx {
    modules("javafx.graphics", "javafx.fxml")
    configuration = "implementation"
}

application {
    // mainModule = "com.github.shimeoki.jfx_rasterization.app"
    mainClass = "com.github.shimeoki.jfx_rasterization.app.RasterizationApp"
}
