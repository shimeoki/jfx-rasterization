package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.Objects;

import javafx.geometry.Point2D;

public class Triangle3 implements Triangle {

    private final double x1;
    private final double y1;

    private final double x2;
    private final double y2;

    private final double x3;
    private final double y3;

    public Triangle3(final Point2D v1, final Point2D v2, final Point2D v3) {
        Objects.requireNonNull(v1);
        Objects.requireNonNull(v2);
        Objects.requireNonNull(v3);

        x1 = v1.getX();
        y1 = v1.getY();

        x2 = v2.getX();
        y2 = v2.getY();

        x3 = v3.getX();
        y3 = v3.getY();
    }

    @Override
    public Point2D v1() {
        return new Point2D(x1, y1);
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
    public Point2D v2() {
        return new Point2D(x2, y2);
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
    public Point2D v3() {
        return new Point2D(x3, y3);
    }

    @Override
    public double x3() {
        return x3;
    }

    @Override
    public double y3() {
        return y3;
    }

    // TODO: implement
    @Override
    public TriangleBarycentrics barycentrics(final Point2D p) {
        return null;
    }
}
