package com.github.shimeoki.jfx.rasterization;

public interface Vector2D extends Point2D {

    public void setX(final float x);

    public void setY(final float y);

    public void copy(final Vector2D v);

    public Vector2D duplicate();
}
