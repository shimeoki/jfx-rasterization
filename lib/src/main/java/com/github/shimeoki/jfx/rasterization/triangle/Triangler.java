package com.github.shimeoki.jfx.rasterization.triangle;

import com.github.shimeoki.jfx.rasterization.triangle.color.TriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;

import javafx.scene.image.PixelWriter;

@FunctionalInterface
public interface Triangler {

    public void draw(
            final PixelWriter w,
            final Triangle t,
            final TriangleColorer c);
}
