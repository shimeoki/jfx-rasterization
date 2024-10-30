package com.github.shimeoki.jfx.rasterization;

public class FloatMath {

    public static final float EPSILON = 0.000001f;

    public static boolean equals(final float v1, final float v2) {
        return Math.abs(v1 - v2) < EPSILON;
    }

    public static boolean moreThan(final float left, final float right) {
        return (left - right) > -EPSILON;
    }

    public static boolean lessThan(final float left, final float right) {
        return (left - right) < EPSILON;
    }

    public static int compare(final float left, final float right) {
        if (equals(left, right)) {
            return 0;
        }

        if (left > right) {
            return 1;
        } else {
            return -1;
        }
    }

    public static float confined(
            final float low, final float x, final float high) {

        return Math.min(Math.max(low, x), high);
    }
}
