package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.Arithmetic;

import javafx.geometry.Point2D;
import javafx.scene.image.PixelWriter;

public class DDATriangler implements Triangler {

    private PixelWriter w;
    private TriangleColorer colorer;

    @Override
    public PixelWriter getPixelWriter() {
        return w;
    }

    @Override
    public void setPixelWriter(final PixelWriter w) {
        Objects.requireNonNull(w);

        this.w = w;
    }

    @Override
    public TriangleColorer getColorer() {
        return colorer;
    }

    @Override
    public void setColorer(final TriangleColorer color) {
        Objects.requireNonNull(color);

        this.colorer = color;
    }

    private List<Point2D> sortedVertices(final Triangle t) {
        final List<Point2D> vertices = new ArrayList<>();
        vertices.add(t.v1());
        vertices.add(t.v2());
        vertices.add(t.v3());

        vertices.sort(Comparator
                .comparing(Point2D::getY)
                .thenComparing(Point2D::getX));

        return vertices;
    }

    private void drawHLine(
            final Triangle t,
            final int y, final int x1, final int x2) {

        for (int x = x1; x <= x2; x++) {
            w.setColor(x, y, colorer.get(t.barycentrics(new Point2D(x, y))));
        }
    }

    private void drawFlatMax(final Triangle init, final Triangle t) {
        final double minX = t.x1();
        final double minY = t.y1();
        final double maxY = t.y2();

        final double dx1 = t.x2() - minX;
        final double dy1 = t.y2() - minY;

        final double dx2 = t.x3() - minX;
        final double dy2 = t.y3() - minY;

        final double delta1 = dx1 / dy1;
        final double delta2 = dx2 / dy2;

        double x1 = minX;
        double x2 = minX;

        for (int y = (int) minY; y <= maxY; y++) {
            // round doubles instead of floor?
            drawHLine(init, y, (int) x1, (int) x2);

            x1 += delta1;
            x2 += delta2;
        }
    }

    private void drawFlatMin(final Triangle init, final Triangle t) {
        final double maxX = t.x1();
        final double maxY = t.y1();
        final double minY = t.y2();

        final double dx1 = maxX - t.x2();
        final double dy1 = maxY - t.y2();

        final double dx2 = maxX - t.x3();
        final double dy2 = maxY - t.y3();

        final double delta1 = dx1 / dy1;
        final double delta2 = dx2 / dy2;

        double x1 = maxX;
        double x2 = maxX;

        for (int y = (int) maxY; y > minY; y--) {
            // round doubles instead of floor?
            drawHLine(init, y, (int) x1, (int) x2);

            x1 -= delta1;
            x2 -= delta2;
        }
    }

    @Override
    public void draw(final Triangle t) {
        Objects.requireNonNull(t);

        // docs:
        // https://www.sunshine2k.de/coding/java/TriangleRasterization/TriangleRasterization.html

        final List<Point2D> vertices = sortedVertices(t);

        final Point2D v1 = vertices.get(0);
        final Point2D v2 = vertices.get(1);
        final Point2D v3 = vertices.get(2);

        final double x1 = v1.getX();
        final double y1 = v1.getY();

        final double x2 = v2.getX();
        final double y2 = v2.getY();

        final double x3 = v3.getX();
        final double y3 = v3.getY();

        if (Arithmetic.equals(y2, y3)) {
            drawFlatMax(t, new Triangle3(v1, v2, v3));
            return;
        }

        if (Arithmetic.equals(y1, y2)) {
            drawFlatMin(t, new Triangle3(v3, v1, v2));
            return;
        }

        final double x4 = x1 + ((y2 - y1) / (y3 - y1)) * (x3 - x1);
        final Point2D v4 = new Point2D(x4, v2.getY());

        // non strict equality?
        if (Arithmetic.moreThan(x4, x2)) {
            drawFlatMax(t, new Triangle3(v1, v2, v4));
            drawFlatMin(t, new Triangle3(v3, v2, v4));
        } else {
            drawFlatMax(t, new Triangle3(v1, v4, v2));
            drawFlatMin(t, new Triangle3(v3, v4, v2));
        }
    }
}
