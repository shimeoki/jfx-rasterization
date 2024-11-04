package com.github.shimeoki.jfx.rasterization.triangle.geom;

import com.github.shimeoki.jfx.rasterization.geom.Pos2f;
import com.github.shimeoki.jfx.rasterization.geom.Point2f;

public interface Triangle {

    public Point2f v1();

    public Point2f v2();

    public Point2f v3();

    public TriangleBarycentrics barycentrics(final Pos2f p);
}
