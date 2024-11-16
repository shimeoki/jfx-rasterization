package com.github.shimeoki.jfx.rasterization.triangle;

import com.github.shimeoki.jfx.rasterization.triangle.color.TriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;

import javafx.scene.canvas.GraphicsContext;

/**
 * A functional interface for triangle rasterizers.
 *
 * <p>
 * Accepts {@link GraphicsContext GraphicsContext} from a JavaFX {@code Canvas}
 * to draw the pixels, {@link Triangle Triangle} as the shape to draw and
 * {@link TriangleColorer TriangleColorer} to get the colors for individual
 * pixels.
 *
 * @since 1.0.0
 *
 * @see GraphicsContext
 * @see Triangle
 * @see TriangleColorer
 */
@FunctionalInterface
public interface Triangler {

    /**
     * Draws the {@code triangle} colored by {@code colorer} with {@code ctx}.
     *
     * @param ctx      the {@link GraphicsContext GraphicsContext} to draw with
     * @param triangle the {@link Triangle Triangle} to draw
     * @param colorer  the color mapping {@link TriangleColorer TriangleColorer}
     *
     * @since 1.0.0
     */
    public void draw(
            final GraphicsContext ctx,
            final Triangle triangle,
            final TriangleColorer colorer);
}
