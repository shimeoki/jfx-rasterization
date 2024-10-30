package com.github.shimeoki.jfx.rasterization.triangle;

import com.github.shimeoki.jfx.rasterization.DoublePoint2;

public interface Triangle {

    // first vertex

    public DoublePoint2 v1();

    public double x1();

    public double y1();

    // second vertex

    public DoublePoint2 v2();

    public double x2();

    public double y2();

    // third vertex

    public DoublePoint2 v3();

    public double x3();

    public double y3();

    // barycentrics

    public TriangleBarycentrics barycentrics(final DoublePoint2 p);
}
