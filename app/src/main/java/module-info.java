module com.github.shimeoki.jfx_rasterization.app {

    requires com.github.shimeoki.jfx.rasterization;

    requires transitive javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    opens com.github.shimeoki.jfx_rasterization.app to javafx.fxml;

    exports com.github.shimeoki.jfx_rasterization.app;
}
