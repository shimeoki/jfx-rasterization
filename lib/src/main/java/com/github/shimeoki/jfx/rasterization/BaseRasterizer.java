package com.github.shimeoki.jfx.rasterization;

import javafx.scene.image.PixelWriter;

public interface BaseRasterizer {

    public PixelWriter pixelWriter();

    public void setPixelWriter(final PixelWriter w);
}
