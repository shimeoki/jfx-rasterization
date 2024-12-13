package io.github.shimeoki.jfx.rasterization.triangle;

import io.github.shimeoki.jfx.rasterization.triangle.color.TriangleFiller;
import io.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;

import javafx.scene.canvas.GraphicsContext;

/**
 * A functional interface for triangle rasterizers.
 *
 * <p>
 * Accepts {@link GraphicsContext GraphicsContext} from a JavaFX {@code Canvas}
 * to draw the pixels, {@link Triangle Triangle} as the shape to draw and
 * {@link TriangleFiller TriangleFiller} to get the colors for individual
 * pixels.
 * <p>
 * Example usage:
 *
 * <pre>{@code
 * // any implementations can be used, look at the interfaces
 *
 * final Triangler triangler = new DDATriangler();
 * final Canvas canvas; // javafx canvas to render on
 *
 * final GraphicsContext ctx = canvas.getGraphicsContext2D();
 * final Triangle triangle = new StaticTriangle(
 *         new Point2f(0, 1),
 *         new Point2f(1, 0),
 *         new Point2f(1, 1));
 * final TriangleFiller filler = new MonotoneTriangleFiller(HTMLColorf.RED);
 *
 * triangler.draw(ctx, triangle, filler);
 * }</pre>
 *
 * @since 1.0.0
 *
 * @see GraphicsContext
 * @see Triangle
 * @see TriangleFiller
 */
@FunctionalInterface
public interface Triangler {

    /**
     * Draws the {@code triangle} colored by {@code filler} with {@code ctx}.
     *
     * @param ctx      the {@link GraphicsContext GraphicsContext} to draw with
     * @param triangle the {@link Triangle Triangle} to draw
     * @param filler   the color mapping {@link TriangleFiller filler}
     *
     * @throws NullPointerException if at least one parameter is {@code null}
     *
     * @since 1.0.0
     */
    public void draw(
            final GraphicsContext ctx,
            final Triangle triangle,
            final TriangleFiller filler);
}
