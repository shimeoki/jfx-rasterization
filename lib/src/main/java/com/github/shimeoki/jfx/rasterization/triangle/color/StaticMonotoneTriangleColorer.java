package com.github.shimeoki.jfx.rasterization.triangle.color;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.color.Colorf;
import com.github.shimeoki.jfx.rasterization.color.RGBColorf;
import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

/**
 * "Static" implementation of the {@link TriangleColorer} interface for filling
 * triangles with one color.
 *
 * <p>
 * "Freezes" the color on creation. Doesn't store the reference to the color
 * object and contains the color values as {@code final} primitives. Even if the
 * color, passed to the constructor, changes, the colorer won't change.
 *
 * @since 1.0.0
 */
public final class StaticMonotoneTriangleColorer implements TriangleColorer {

    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;

    /**
     * Creates a new {@code StaticMonotoneTriangleColorer} instance.
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
    public StaticMonotoneTriangleColorer(final Colorf c) {
        Objects.requireNonNull(c);

        red = c.red();
        green = c.green();
        blue = c.blue();
        alpha = c.alpha();
    }

    @Override
    public Colorf get(final TriangleBarycentrics b) {
        Objects.requireNonNull(b);

        return new RGBColorf(red, green, blue, alpha);
    }
}
