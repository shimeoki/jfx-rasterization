package com.github.shimeoki.jfx_rasterization.rasterizers;

import com.github.shimeoki.jfx_rasterization.TriangleColor;
import com.github.shimeoki.jfx_rasterization.TriangleRasterizer;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class StandardTriangleRasterizer implements TriangleRasterizer {

    private GraphicsContext ctx;
    private TriangleColor color;

    @Override
    public GraphicsContext getCtx() {
        return ctx;
    }

    @Override
    public void setCtx(final GraphicsContext ctx) {
        if (ctx == null) {
            // exception?
            return;
        }

        this.ctx = ctx;
    }

    @Override
    public TriangleColor getColor() {
        return color;
    }

    @Override
    public void setColor(final TriangleColor color) {
        if (color == null) {
            // exception?
            return;
        }

        this.color = color;
    }

    @Override
    public void draw(final Point2D p1, final Point2D p2, final Point2D p3) {
        // TODO: implement
    }
}
