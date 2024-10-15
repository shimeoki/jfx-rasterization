package com.github.shimeoki.jfx_rasterization;

import javafx.scene.paint.Color;

@FunctionalInterface
public interface TriangleColor {

    /**
     * Gets a <code>Color</code> at specified barycentric coordinates.
     *
     * @param lambda1 first normalized barycentric coordinate.
     * @param lambda2 second normalized barycentric coordinate.
     * @param lambda3 third normalized barycentric coordinate.
     * @return <code>Color</code> if coordinates are valid;
     *         <code>null</code> otherwise.
     */
    Color get(
            double lambda1,
            double lambda2,
            double lambda3);
}
