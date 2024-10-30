package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.geom.Point2D;
import com.github.shimeoki.jfx.rasterization.geom.Vector;
import com.github.shimeoki.jfx.rasterization.geom.Vector2D;

public class StaticTriangle implements Triangle {

    private final float x1;
    private final float y1;

    private final float x2;
    private final float y2;

    private final float x3;
    private final float y3;

    // denominator for barycentric coordinates
    // d is "1 / denominator" for faster calculations
    private final float d;

    public StaticTriangle(
            final Point2D p1,
            final Point2D p2,
            final Point2D p3) {

        Objects.requireNonNull(p1);
        Objects.requireNonNull(p2);
        Objects.requireNonNull(p3);

        x1 = p1.x();
        y1 = p1.y();

        x2 = p2.x();
        y2 = p2.y();

        x3 = p3.x();
        y3 = p3.y();

        // check for zero?
        d = 1 / denominator();
    }

    @Override
    public Vector2D v1() {
        return new Vector(x1, y1);
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
    public Vector2D v2() {
        return new Vector(x2, y2);
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
    public Vector2D v3() {
        return new Vector(x3, y3);
    }

    @Override
    public float x3() {
        return x3;
    }

    @Override
    public float y3() {
        return y3;
    }

    private float denominator() {
        return (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);
    }

    @Override
    public TriangleBarycentrics barycentrics(final Point2D p) {
        // docs:
        // https://en.wikipedia.org/wiki/Barycentric_coordinate_system#Conversion_between_barycentric_and_Cartesian_coordinates

        final float x = p.x();
        final float y = p.y();

        // n stands for numerator
        final float n1 = (y2 - y3) * (x - x3) + (x3 - x2) * (y - y3);
        final float n2 = (y3 - y1) * (x - x3) + (x1 - x3) * (y - y3);
        final float n3 = (y1 - y2) * (x - x1) + (x2 - x1) * (y - y1);

        // lambdas
        final float l1 = n1 * d;
        final float l2 = n2 * d;
        final float l3 = n3 * d;

        return new DefaultTriangleBarycentrics(l1, l2, l3);
    }
}
