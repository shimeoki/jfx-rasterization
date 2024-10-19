package com.github.shimeoki.jfx_rasterization;

import javafx.scene.paint.Color;

@FunctionalInterface
public interface TriangleColor {

    /**
     * Gets a <code>Color</code> at specified barycentric coordinates.
     *
     * @param l1 (lambda 1) first normalized barycentric coordinate.
     * @param l2 (lambda 2) second normalized barycentric coordinate.
     * @param l3 (lambda 3) third normalized barycentric coordinate.
     * @return <code>Color</code> if coordinates are valid;
     *         <code>null</code> otherwise.
     */
    Color get(final double l1, final double l2, final double l3);
}
