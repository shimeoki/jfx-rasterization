plugins {
    id("jfx-rasterization.java-conventions")
    id("java-library")
}

version = "0.7.0"

javafx {
    modules("javafx.graphics")
    configuration = "api"
}
