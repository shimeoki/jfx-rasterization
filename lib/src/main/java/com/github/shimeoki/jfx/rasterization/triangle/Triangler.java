package com.github.shimeoki.jfx.rasterization.triangle;

import com.github.shimeoki.jfx.rasterization.BaseRasterizer;

public interface Triangler extends BaseRasterizer {

    public TriangleColorer getColorer();

    public void setColorer(final TriangleColorer c);

    public void draw(final Triangle t);
}
