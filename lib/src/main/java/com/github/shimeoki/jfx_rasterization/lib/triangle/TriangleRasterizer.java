package com.github.shimeoki.jfx_rasterization.lib.triangle;

import com.github.shimeoki.jfx_rasterization.lib.BaseRasterizer;

import javafx.geometry.Point2D;

public interface TriangleRasterizer extends BaseRasterizer {

    public TriangleColor getColor();

    public void setColor(final TriangleColor color);

    public void draw(final Point2D p1, final Point2D p2, final Point2D p3);
}
