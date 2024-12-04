package io.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import io.github.shimeoki.jfx.rasterization.color.Colorf;
import io.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

/**
 * "Dynamic" implementation of the {@link TriangleColorer} interface for filling
 * triangles with one color.
 *
 * <p>
 * Does store the reference to the color on creation. If the color will change,
 * the fill color will change as well.
 *
 * @since 1.0.0
 */
public final class DynamicMonotoneTriangleColorer implements TriangleColorer {

    private final Colorf color;

    /**
     * Creates a new {@code DynamicMonotoneTriangleColorer} instance.
     *
     * <p>
     * All principles of the internal usage are described in the javadoc of the
     * class. It's advised to read it before use.
     *
     * @param c the color to fill the triangles with
     *
     * @throws NullPointerException if {@code c} is {@code null}
     *
     * @since 1.0.0
     */
    public DynamicMonotoneTriangleColorer(final Colorf c) {
        Objects.requireNonNull(c);

        color = c;
    }

    @Override
    public Colorf get(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        return color;
    }
}
