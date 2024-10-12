package com.github.shimeoki.jfx_rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface BaseRasterizer {

    public GraphicsContext getCtx();

    public void setCtx(GraphicsContext ctx);

    public Color getColor();

    public void setColor(Color color);
}
