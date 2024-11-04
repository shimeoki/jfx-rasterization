package com.github.shimeoki.jfx.rasterization.color;

import javafx.scene.paint.Color;

public interface Colorf {

    // rgb

    public float red();

    public float green();

    public float blue();

    // alpha / transparency

    public float alpha();

    public float transparency();

    // conversion

    public Color jfxColor();
}
