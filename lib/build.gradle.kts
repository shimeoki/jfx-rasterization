plugins {
    id("jfx-rasterization.java-conventions")
    id("java-library")
}

version = "0.1.0"

javafx {
    modules("javafx.graphics")
    configuration = "api"
}
