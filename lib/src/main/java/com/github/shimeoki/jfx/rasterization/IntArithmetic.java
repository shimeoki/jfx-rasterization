package com.github.shimeoki.jfx.rasterization;

public class IntArithmetic {

    public static int confined(final int low, final int x, final int high) {
        return Math.min(Math.max(low, x), high);
    }
}
