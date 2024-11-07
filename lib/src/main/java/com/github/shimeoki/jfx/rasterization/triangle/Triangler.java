package com.github.shimeoki.jfx.rasterization.triangle;

import com.github.shimeoki.jfx.rasterization.triangle.color.TriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;

import javafx.scene.canvas.GraphicsContext;

@FunctionalInterface
public interface Triangler {

    public void draw(
            final GraphicsContext ctx,
            final Triangle triangle,
            final TriangleColorer colorer);
}
