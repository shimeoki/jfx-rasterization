plugins {
    id("jfx-rasterization.java-conventions")
}

version = "0.0.1"

dependencies {
    implementation(project(":lib"))
}

javafx {
    modules("javafx.graphics", "javafx.fxml")
    configuration = "implementation"
}
