package com.github.shimeoki.jfx.rasterization.math;

public final class Ints {

    private Ints() {
    }

    public static int confined(final int low, final int x, final int high) {
        return Math.min(Math.max(low, x), high);
    }

    public static int min3(final int v1, final int v2, final int v3) {
        return Math.min(v1, Math.min(v2, v3));
    }

    public static int max3(final int v1, final int v2, final int v3) {
        return Math.max(v1, Math.max(v2, v3));
    }
}
