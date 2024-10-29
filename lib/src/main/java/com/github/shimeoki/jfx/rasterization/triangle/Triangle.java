package com.github.shimeoki.jfx.rasterization.triangle;

import javafx.geometry.Point2D;

public interface Triangle {

    // first vertex

    public Point2D v1();

    public double x1();

    public double y1();

    // second vertex

    public Point2D v2();

    public double x2();

    public double y2();

    // third vertex

    public Point2D v3();

    public double x3();

    public double y3();
}
