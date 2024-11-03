package com.github.shimeoki.jfx.rasterization.triangle.geom;

import com.github.shimeoki.jfx.rasterization.geom.FloatPoint2D;
import com.github.shimeoki.jfx.rasterization.geom.FloatVector2D;

public interface Triangle {

    // first vertex

    public FloatVector2D v1();

    public float x1();

    public float y1();

    // second vertex

    public FloatVector2D v2();

    public float x2();

    public float y2();

    // third vertex

    public FloatVector2D v3();

    public float x3();

    public float y3();

    // barycentrics

    public TriangleBarycentrics barycentrics(final FloatPoint2D p);
}
