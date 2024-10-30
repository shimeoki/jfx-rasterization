package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.DoublePoint2;
import com.github.shimeoki.jfx.rasterization.Point;

public class StaticTriangle implements Triangle {

    private final double x1;
    private final double y1;

    private final double x2;
    private final double y2;

    private final double x3;
    private final double y3;

    // denominator for barycentric coordinates
    // d is "1 / denominator" for faster calculations
    private final double d;

    public StaticTriangle(
            final DoublePoint2 v1,
            final DoublePoint2 v2,
            final DoublePoint2 v3) {

        Objects.requireNonNull(v1);
        Objects.requireNonNull(v2);
        Objects.requireNonNull(v3);

        x1 = v1.x();
        y1 = v1.y();

        x2 = v2.x();
        y2 = v2.y();

        x3 = v3.x();
        y3 = v3.y();

        // check for zero?
        d = 1 / denominator();
    }

    @Override
    public DoublePoint2 v1() {
        return new Point(x1, y1);
    }

    @Override
    public double x1() {
        return x1;
    }

    @Override
    public double y1() {
        return y1;
    }

    @Override
    public DoublePoint2 v2() {
        return new Point(x2, y2);
    }

    @Override
    public double x2() {
        return x2;
    }

    @Override
    public double y2() {
        return y2;
    }

    @Override
    public DoublePoint2 v3() {
        return new Point(x3, y3);
    }

    @Override
    public double x3() {
        return x3;
    }

    @Override
    public double y3() {
        return y3;
    }

    private double denominator() {
        return (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);
    }

    @Override
    public TriangleBarycentrics barycentrics(final DoublePoint2 p) {
        // docs:
        // https://en.wikipedia.org/wiki/Barycentric_coordinate_system#Conversion_between_barycentric_and_Cartesian_coordinates

        final double x = p.x();
        final double y = p.y();

        // n stands for numerator
        final double n1 = (y2 - y3) * (x - x3) + (x3 - x2) * (y - y3);
        final double n2 = (y3 - y1) * (x - x3) + (x1 - x3) * (y - y3);
        final double n3 = (y1 - y2) * (x - x1) + (x2 - x1) * (y - y1);

        // lambdas
        final double l1 = n1 * d;
        final double l2 = n2 * d;
        final double l3 = n3 * d;

        return new DefaultTriangleBarycentrics(l1, l2, l3);
    }
}
