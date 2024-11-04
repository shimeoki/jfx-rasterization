package com.github.shimeoki.jfx.rasterization.triangle.geom;

import com.github.shimeoki.jfx.rasterization.geom.FloatPoint2D;
import com.github.shimeoki.jfx.rasterization.geom.FloatVector2D;

public interface Triangle {

    public FloatVector2D v1();

    public FloatVector2D v2();

    public FloatVector2D v3();

    public TriangleBarycentrics barycentrics(final FloatPoint2D p);
}
