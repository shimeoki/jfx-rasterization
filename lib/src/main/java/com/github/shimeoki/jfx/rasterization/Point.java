package com.github.shimeoki.jfx.rasterization;

public class Point implements DoublePoint2 {

    private final double x;
    private final double y;

    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }
}
