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

    public static int compare(final double left, final double right) {
        if (equals(left, right)) {
            return 0;
        }

        if (left > right) {
            return 1;
        } else {
            return -1;
        }
    }

    public static double confined(
            final double low, final double x, final double high) {

        return Math.min(Math.max(low, x), high);
    }
}
