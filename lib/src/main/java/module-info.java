/**
 * A JavaFX shape rasterization library.
 *
 * @version 2.0.1
 */
module io.github.shimeoki.jfx.rasterization {

    requires transitive javafx.graphics;

    exports io.github.shimeoki.jfx.rasterization;
    exports io.github.shimeoki.jfx.rasterization.math;

    // exports io.github.shimeoki.jfx.rasterization.triangle;
    exports io.github.shimeoki.jfx.rasterization.triangle.geom;
    exports io.github.shimeoki.jfx.rasterization.triangle.color;
}
