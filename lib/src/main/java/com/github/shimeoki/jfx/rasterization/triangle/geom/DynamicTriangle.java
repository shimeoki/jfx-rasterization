package com.github.shimeoki.jfx.rasterization.triangle.geom;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.geom.FloatPoint2D;
import com.github.shimeoki.jfx.rasterization.geom.FloatVector2D;
import com.github.shimeoki.jfx.rasterization.math.Floats;

public final class DynamicTriangle implements Triangle {

    private final FloatVector2D v1;
    private final FloatVector2D v2;
    private final FloatVector2D v3;

    public DynamicTriangle(
            final FloatVector2D v1,
            final FloatVector2D v2,
            final FloatVector2D v3) {

        Objects.requireNonNull(v1);
        Objects.requireNonNull(v2);
        Objects.requireNonNull(v3);

        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    @Override
    public FloatVector2D v1() {
        return v1;
    }

    @Override
    public FloatVector2D v2() {
        return v2;
    }

    @Override
    public FloatVector2D v3() {
        return v3;
    }

    @Override
    public TriangleBarycentrics barycentrics(final FloatPoint2D p) {
        // docs:
        // https://en.wikipedia.org/wiki/Barycentric_coordinate_system#Conversion_between_barycentric_and_Cartesian_coordinates

        final float x = p.x();
        final float y = p.y();

        final float x1 = v1.x();
        final float y1 = v1.y();

        final float x2 = v2.x();
        final float y2 = v2.y();

        final float x3 = v3.x();
        final float y3 = v3.y();

        final float dx13 = x1 - x3;
        final float dy13 = y1 - y3;

        final float dx23 = x2 - x3;
        final float dy23 = y2 - y3;

        final float dx3 = x - x3;
        final float dy3 = y - y3;

        final float numerator1 = dy23 * dx3 - dx23 * dy3;
        final float numerator2 = -dy13 * dx3 + dx13 * dy3;
        final float numerator3 = (y1 - y2) * (x - x1) + (x2 - x1) * (y - y1);

        final float denominator = dy23 * dx13 - dx23 * dy13;
        if (Floats.equals(denominator, 0)) {
            return new NormalizedTriangleBarycentrics(0, 0, 0);
        }

        final float d = 1f / denominator;

        final float lambda1 = numerator1 * d;
        final float lambda2 = numerator2 * d;
        final float lambda3 = numerator3 * d;

        return new NormalizedTriangleBarycentrics(lambda1, lambda2, lambda3);
    }
}
