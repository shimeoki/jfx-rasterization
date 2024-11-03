package com.github.shimeoki.jfx.rasterization.geom;

public class FloatVector implements FloatVector2D {

    private float x;
    private float y;

    public FloatVector(final float x, final float y) {
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
    public void addX(final float dx) {
        setX(x + dx);
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
    public void addY(final float dy) {
        setY(y + dy);
    }
}
