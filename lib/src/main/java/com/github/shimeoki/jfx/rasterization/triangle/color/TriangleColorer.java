package com.github.shimeoki.jfx.rasterization.triangle.color;

import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

import javafx.scene.paint.Color;

/**
 * A functional interface for {@code TriangleRasterizer} to get colors for
 * individual pixels.
 *
 * @see {@code TriangleRasterizer}
 */
@FunctionalInterface
public interface TriangleColorer {

    /**
     * Gets a {@code Color} at specified barycentic coordinates.
     *
     * @param b barycentric coordinates.
     * 
     * @return {@code Color} at specified barycentric coordinates.
     * 
     * @throws {@code NullPointerException} if b is null.
     */
    public Color get(final TriangleBarycentrics b);
}
