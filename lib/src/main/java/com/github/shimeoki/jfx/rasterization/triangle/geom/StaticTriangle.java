package com.github.shimeoki.jfx.rasterization.triangle.geom;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.geom.FloatPoint2D;
import com.github.shimeoki.jfx.rasterization.geom.FloatVector;
import com.github.shimeoki.jfx.rasterization.geom.FloatVector2D;
import com.github.shimeoki.jfx.rasterization.math.Floats;

public final class StaticTriangle implements Triangle {

    private final float x1;
    private final float y1;

    private final float x2;
    private final float y2;

    private final float x3;
    private final float y3;

    private final float dx13;
    private final float dy13;

    private final float dx23;
    private final float dy23;

    private final float d;

    public StaticTriangle(
            final FloatPoint2D p1,
            final FloatPoint2D p2,
            final FloatPoint2D p3) {

        Objects.requireNonNull(p1);
        Objects.requireNonNull(p2);
        Objects.requireNonNull(p3);

        x1 = p1.x();
        y1 = p1.y();

        x2 = p2.x();
        y2 = p2.y();

        x3 = p3.x();
        y3 = p3.y();

        dx13 = x1 - x3;
        dy13 = y1 - y3;

        dx23 = x2 - x3;
        dy23 = y2 - y3;

        final float denominator = dy23 * dx13 - dx23 * dy13;
        if (Floats.equals(denominator, 0)) {
            d = 0;
        } else {
            d = 1 / denominator;
        }
    }

    @Override
    public FloatVector2D v1() {
        return new FloatVector(x1, y1);
    }

    @Override
    public float x1() {
        return x1;
    }

    @Override
    public float y1() {
        return y1;
    }

    @Override
    public FloatVector2D v2() {
        return new FloatVector(x2, y2);
    }

    @Override
    public float x2() {
        return x2;
    }

    @Override
    public float y2() {
        return y2;
    }

    @Override
    public FloatVector2D v3() {
        return new FloatVector(x3, y3);
    }

    @Override
    public float x3() {
        return x3;
    }

    @Override
    public float y3() {
        return y3;
    }

    @Override
    public TriangleBarycentrics barycentrics(final FloatPoint2D p) {
        // docs:
        // https://en.wikipedia.org/wiki/Barycentric_coordinate_system#Conversion_between_barycentric_and_Cartesian_coordinates

        final float x = p.x();
        final float y = p.y();

        final float dx3 = x - x3;
        final float dy3 = y - y3;

        final float numerator1 = dy23 * dx3 - dx23 * dy3;
        final float numerator2 = -dy13 * dx3 + dx13 * dy3;
        final float numerator3 = (y1 - y2) * (x - x1) + (x2 - x1) * (y - y1);

        final float lambda1 = numerator1 * d;
        final float lambda2 = numerator2 * d;
        final float lambda3 = numerator3 * d;

        return new DefaultTriangleBarycentrics(lambda1, lambda2, lambda3);
    }
}
