package com.github.shimeoki.jfx.rasterization.triangle;

import com.github.shimeoki.jfx.rasterization.DoubleArithmetic;

public class DefaultTriangleBarycentrics implements TriangleBarycentrics {

    private final double l1;
    private final double l2;
    private final double l3;

    public DefaultTriangleBarycentrics(
            final double l1, final double l2, final double l3) {

        final double sum = l1 + l2 + l3;
        if (!DoubleArithmetic.equals(sum, 1)) {
            throw new IllegalArgumentException("coordinates are not normalized");
        }

        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
    }

    @Override
    public double lambda1() {
        return l1;
    }

    @Override
    public double lambda2() {
        return l2;
    }

    @Override
    public double lambda3() {
        return l3;
    }
}
