plugins {
    id("jfx-rasterization.java-conventions")
    id("java-library")
}

version = "0.0.2"

javafx {
    modules("javafx.graphics")
    configuration = "api"
}
