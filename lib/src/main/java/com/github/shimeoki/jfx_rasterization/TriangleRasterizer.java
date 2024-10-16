package com.github.shimeoki.jfx_rasterization;

import javafx.geometry.Point2D;

public interface TriangleRasterizer extends BaseRasterizer {

    TriangleColor getColor();

    void setColor(TriangleColor color);

    void draw(
            Point2D p1,
            Point2D p2,
            Point2D p3);
}
