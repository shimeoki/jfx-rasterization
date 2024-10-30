package com.github.shimeoki.jfx.rasterization.triangle;

public interface TriangleBarycentrics {

    public float lambda1();

    public float lambda2();

    public float lambda3();

    public boolean inside();
}
