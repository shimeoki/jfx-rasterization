package com.github.shimeoki.jfx.rasterization.triangle.geom;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.geom.FloatPoint2D;
import com.github.shimeoki.jfx.rasterization.geom.FloatVector2D;

public class DynamicTriangle implements Triangle {

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
    public float x1() {
        return v1.x();
    }

    @Override
    public float y1() {
        return v1.y();
    }

    @Override
    public FloatVector2D v2() {
        return v2;
    }

    @Override
    public float x2() {
        return v2.x();
    }

    @Override
    public float y2() {
        return v2.y();
    }

    @Override
    public FloatVector2D v3() {
        return v3;
    }

    @Override
    public float x3() {
        return v3.x();
    }

    @Override
    public float y3() {
        return v3.y();
    }

    @Override
    public TriangleBarycentrics barycentrics(final FloatPoint2D p) {
        // docs:
        // https://en.wikipedia.org/wiki/Barycentric_coordinate_system#Conversion_between_barycentric_and_Cartesian_coordinates

        final float x = p.x();
        final float y = p.y();

        final float x1 = x1();
        final float y1 = y1();

        final float x2 = x2();
        final float y2 = y2();

        final float x3 = x3();
        final float y3 = y3();

        // n stands for numerator
        final float n1 = (y2 - y3) * (x - x3) + (x3 - x2) * (y - y3);
        final float n2 = (y3 - y1) * (x - x3) + (x1 - x3) * (y - y3);
        final float n3 = (y1 - y2) * (x - x1) + (x2 - x1) * (y - y1);

        // d = 1 / denominator
        final float d = 1 / ((y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3));

        // lambdas
        final float l1 = n1 * d;
        final float l2 = n2 * d;
        final float l3 = n3 * d;

        return new DefaultTriangleBarycentrics(l1, l2, l3);
    }
}
