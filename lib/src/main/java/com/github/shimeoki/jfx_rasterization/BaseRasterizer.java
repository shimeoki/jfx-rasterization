package com.github.shimeoki.jfx_rasterization;

import javafx.scene.canvas.GraphicsContext;

public interface BaseRasterizer {

    public GraphicsContext getCtx();

    public void setCtx(GraphicsContext ctx);
}
