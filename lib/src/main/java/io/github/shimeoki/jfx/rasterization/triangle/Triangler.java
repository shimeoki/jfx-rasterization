package io.github.shimeoki.jfx.rasterization.triangle;

import io.github.shimeoki.jfx.rasterization.triangle.color.TriangleFiller;
import io.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;

/**
 * An interface for triangle rasterizers.
 *
 * <p>
 * Accepts a {@link Triangle Triangle} as the shape to draw and
 * {@link TriangleFiller TriangleFiller} to get the colors for individual
 * pixels.
 * <p>
 * Example usage:
 *
 * <pre>{@code
 * // any implementations can be used, look at the interfaces
 *
 * final Canvas canvas; // javafx canvas to render on
 * final Triangler triangler = new DDATriangler(canvas.getGraphicsContext2D());
 * triangler.setFiller(new MonotoneTriangleFiller(HTMLColorf.RED));
 *
 * final Triangle triangle = new Polygon3(
 *         new Point2f(0, 1),
 *         new Point2f(1, 0),
 *         new Point2f(1, 1));
 *
 * triangler.draw(triangle);
 * }</pre>
 *
 * @since 1.0.0
 *
 * @see Triangle
 * @see TriangleFiller
 */
public interface Triangler {

    public TriangleFiller filler();

    public void setFiller(final TriangleFiller f);

    /**
     * Draws the triangle {@link Triangle t}.
     *
     * @param t the {@link Triangle Triangle} to draw
     *
     * @throws NullPointerException if {@code t} is {@code null}
     *
     * @since 1.0.0
     */
    public void draw(final Triangle t);
}
