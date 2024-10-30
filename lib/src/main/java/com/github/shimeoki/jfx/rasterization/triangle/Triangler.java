package com.github.shimeoki.jfx.rasterization.triangle;

import com.github.shimeoki.jfx.rasterization.BaseRasterizer;
import com.github.shimeoki.jfx.rasterization.triangle.color.TriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;

public interface Triangler extends BaseRasterizer {

    public TriangleColorer colorer();

    public void setColorer(final TriangleColorer c);

    public void draw(final Triangle t);
}
