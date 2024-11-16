/**
 * A JavaFX shape rasterization library.
 *
 * @version 1.0.1
 */
module com.github.shimeoki.jfx.rasterization {

    requires transitive javafx.graphics;

    // shared
    exports com.github.shimeoki.jfx.rasterization.math;
    exports com.github.shimeoki.jfx.rasterization.geom;
    exports com.github.shimeoki.jfx.rasterization.color;
    exports com.github.shimeoki.jfx.rasterization.test;

    // triangle
    exports com.github.shimeoki.jfx.rasterization.triangle;
    exports com.github.shimeoki.jfx.rasterization.triangle.geom;
    exports com.github.shimeoki.jfx.rasterization.triangle.color;
}
