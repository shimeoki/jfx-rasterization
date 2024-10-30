module com.github.shimeoki.jfx.rasterization {

    requires transitive javafx.graphics;

    // shared packages
    exports com.github.shimeoki.jfx.rasterization;
    exports com.github.shimeoki.jfx.rasterization.math;
    exports com.github.shimeoki.jfx.rasterization.geom;
    exports com.github.shimeoki.jfx.rasterization.color;

    // rasterization modules
    exports com.github.shimeoki.jfx.rasterization.triangle;
}
