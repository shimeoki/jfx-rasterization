package com.github.shimeoki.jfx.rasterization;

public class Arithmetic {

    public static final double EPSILON = 0.000000000001;

    public static boolean equals(final double v1, final double v2) {
        return Math.abs(v1 - v2) < EPSILON;
    }

    public static boolean moreThan(final double left, final double right) {
        return (left - right) > -EPSILON;
    }

    public static boolean lessThan(final double left, final double right) {
        return (left - right) < EPSILON;
    }
}
