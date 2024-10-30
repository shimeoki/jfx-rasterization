package com.github.shimeoki.jfx.rasterization.geom;

public class Vector implements Vector2D {

    private float x;
    private float y;

    public Vector(final float x, final float y) {
        setX(x);
        setY(y);
    }

    @Override
    public float x() {
        return x;
    }

    @Override
    public void setX(final float x) {
        this.x = x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public void setY(final float y) {
        this.y = y;
    }

    @Override
    public void copy(final Vector2D v) {
        setX(v.x());
        setY(v.y());
    }

    @Override
    public Vector2D duplicate() {
        return new Vector(x, y);
    }
}
