package com.github.shimeoki.jfx.rasterization.triangle.color;

import com.github.shimeoki.jfx.rasterization.color.Colorf;
import com.github.shimeoki.jfx.rasterization.triangle.geom.TriangleBarycentrics;

@FunctionalInterface
public interface TriangleColorer {

    public Colorf get(final TriangleBarycentrics b);
}
