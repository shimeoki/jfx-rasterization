package com.github.shimeoki.jfx.rasterization.triangle;

import com.github.shimeoki.jfx.rasterization.Point2D;
import com.github.shimeoki.jfx.rasterization.Vector2D;

public interface Triangle {

    // first vertex

    public Vector2D v1();

    public float x1();

    public float y1();

    // second vertex

    public Vector2D v2();

    public float x2();

    public float y2();

    // third vertex

    public Vector2D v3();

    public float x3();

    public float y3();

    // barycentrics

    public TriangleBarycentrics barycentrics(final Point2D p);
}
