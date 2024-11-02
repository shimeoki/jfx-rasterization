module com.github.shimeoki.jfx.rasterization {

    requires transitive javafx.graphics;

    // shared
    exports com.github.shimeoki.jfx.rasterization;
    exports com.github.shimeoki.jfx.rasterization.math;
    exports com.github.shimeoki.jfx.rasterization.geom;
    exports com.github.shimeoki.jfx.rasterization.color;
    exports com.github.shimeoki.jfx.rasterization.test;

    // triangle
    exports com.github.shimeoki.jfx.rasterization.triangle;
    exports com.github.shimeoki.jfx.rasterization.triangle.geom;
    exports com.github.shimeoki.jfx.rasterization.triangle.color;
}
