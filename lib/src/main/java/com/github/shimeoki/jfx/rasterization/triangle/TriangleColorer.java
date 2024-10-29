package com.github.shimeoki.jfx.rasterization.triangle;

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
     * Coordinates are barycentric, so they should be normalized. Because of
     * that, coordinates are considered invalid if their sum is not equal to 1.
     * If that's the case, {@code IllegalArgumentException} should be thrown.
     *
     * <br>
     * <br>
     *
     * As a special case, if corresponding {@code TriangleRasterizer} uses some
     * kind of an anti-aliasing and renders pixels outside of the triangle,
     * coordinates can be checked one by one and the requirement above can be
     * ignored.
     *
     * @param l1 (lambda 1) first normalized barycentric coordinate.
     * @param l2 (lambda 2) second normalized barycentric coordinate.
     * @param l3 (lambda 3) third normalized barycentric coordinate.
     * 
     * @return {@code Color} at specified barycentric coordinates.
     * 
     * @throws {@code IllegalArgumentException} if coordinates are not valid.
     */
    public Color get(final double l1, final double l2, final double l3);
}
