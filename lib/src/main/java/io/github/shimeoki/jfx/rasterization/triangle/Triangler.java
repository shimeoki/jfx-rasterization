package io.github.shimeoki.jfx.rasterization.triangle;

import io.github.shimeoki.jfx.rasterization.triangle.color.TriangleFiller;
import io.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;

/**
 * An interface for triangle rasterizers.
 *
 * <p>
 * Accepts a {@link Triangle Triangle} as the shape to draw and
 * {@link TriangleFiller } to get the colors for individual
 * pixels.
 * <p>
 * The library contains some implementations in the same package.
 * <p>
 * Example usage:
 *
 * <pre>{@code
 * final Canvas canvas; // javafx canvas to render on
 *
 * final Triangler triangler = new DDATriangler(canvas.getGraphicsContext2D());
 * triangler.setFiller(new MonotoneTriangleFiller(HTMLColorf.RED));
 * // any implementation can be used
 *
 * final Triangle triangle = new Polygon3(
 *         new Point2f(0, 1),
 *         new Point2f(1, 0),
 *         new Point2f(1, 1));
 *
 * triangler.draw(triangle);
 * }</pre>
 *
 * @since 2.0.0
 *
 * @see Triangle
 * @see TriangleFiller
 */
public interface Triangler {

    /**
     * Gets a {@link TriangleFiller} currently used by this triangler.
     *
     * @return reference to the currently used filler
     *
     * @since 2.0.0
     *
     * @see TriangleFiller
     */
    public TriangleFiller filler();

    /**
     * Sets the {@link TriangleFiller} to use for the rasterization.
     *
     * @param f the filler to use
     *
     * @throws NullPointerException if {@code f} is {@code null}
     *
     * @since 2.0.0
     *
     * @see TriangleFiller
     */
    public void setFiller(final TriangleFiller f);

    /**
     * Draws the {@link Triangle} with the currently used filler.
     *
     * @param t the {@link Triangle} to draw
     *
     * @throws NullPointerException if {@code t} is {@code null}
     *
     * @since 2.0.0
     */
    public void draw(final Triangle t);
}
