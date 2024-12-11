/**
 * A JavaFX shape rasterization library.
 *
 * @version 1.0.1
 */
module io.github.shimeoki.jfx.rasterization {

    requires transitive javafx.graphics;

    // shared
    exports io.github.shimeoki.jfx.rasterization.math;
    exports io.github.shimeoki.jfx.rasterization.geom;
    exports io.github.shimeoki.jfx.rasterization.color;

    // triangle
    exports io.github.shimeoki.jfx.rasterization.triangle;
    exports io.github.shimeoki.jfx.rasterization.triangle.geom;
    exports io.github.shimeoki.jfx.rasterization.triangle.color;
}
