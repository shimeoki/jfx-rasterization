package com.github.shimeoki.jfx.rasterization.triangle.color;

import com.github.shimeoki.jfx.rasterization.color.Colorf;
import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

/**
 * A functional interface for {@code TriangleRasterizer} to get colors for
 * individual pixels.
 *
 * @see {@code TriangleRasterizer}
 */
@FunctionalInterface
public interface TriangleColorer {

    /**
     * Gets a {@code Colorf} at specified barycentic coordinates.
     *
     * @param b barycentric coordinates.
     * 
     * @return {@code Colorf} at specified barycentric coordinates.
     * 
     * @throws {@code NullPointerException} if b is null.
     */
    public Colorf get(final TriangleBarycentrics b);
}
