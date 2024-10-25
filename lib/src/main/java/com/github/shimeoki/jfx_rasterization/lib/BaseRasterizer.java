package com.github.shimeoki.jfx_rasterization.lib;

import javafx.scene.canvas.GraphicsContext;

/**
 * <code>BaseRasterizer</code> represents a blank rasterizer with cached
 * <code>GraphicsContext</code>.
 */
public interface BaseRasterizer {

    public GraphicsContext getCtx();

    public void setCtx(final GraphicsContext ctx);
}
