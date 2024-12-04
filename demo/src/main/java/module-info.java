module io.github.shimeoki.jfx.rasterization.demo {

    requires io.github.shimeoki.jfx.rasterization;

    requires transitive javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    opens io.github.shimeoki.jfx.rasterization.demo to javafx.fxml;

    exports io.github.shimeoki.jfx.rasterization.demo;
}
