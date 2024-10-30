package com.github.shimeoki.jfx.rasterization.triangle;

import com.github.shimeoki.jfx.rasterization.color.Colorf;

public interface TriangleGradient {

    // first color

    public Colorf color1();

    public float red1();

    public float green1();

    public float blue1();

    // second color

    public Colorf color2();

    public float red2();

    public float green2();

    public float blue2();

    // third color

    public Colorf color3();

    public float red3();

    public float green3();

    public float blue3();
}
