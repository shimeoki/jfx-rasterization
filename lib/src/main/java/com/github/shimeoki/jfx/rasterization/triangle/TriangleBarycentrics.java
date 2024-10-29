package com.github.shimeoki.jfx.rasterization.triangle;

public interface TriangleBarycentrics {

    public double lambda1();

    public double lambda2();

    public double lambda3();

    public boolean inside();
}
