package com.github.shimeoki.jfx.rasterization.triangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jfx.rasterization.Arithmetic;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class DDATriangler implements Triangler {

    private GraphicsContext ctx;
    private TriangleColorer colorer;

    @Override
    public GraphicsContext getCtx() {
        return ctx;
    }

    @Override
    public void setCtx(final GraphicsContext ctx) {
        Objects.requireNonNull(ctx);

        this.ctx = ctx;
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

    class Triangle {

        private final PixelWriter w;
        private final TriangleColorer colorer;

        private final Point2D p1;
        private final Point2D p2;
        private final Point2D p3;

        // denominator for barycentric coordinates
        // d is "1 / denominator" for faster calculations
        private final double d;

        private Triangle(
                final PixelWriter w,
                final TriangleColorer colorer,
                final Point2D p1,
                final Point2D p2,
                final Point2D p3) {
            this.w = w;
            this.colorer = colorer;

            // points should be valid
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;

            // call after assigning points
            this.d = 1 / denominator();
        }

        private double denominator() {
            final double x1 = p1.getX();
            final double y1 = p1.getY();

            final double x2 = p2.getX();
            final double y2 = p2.getY();

            final double x3 = p3.getX();
            final double y3 = p3.getY();

            return (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);
        }

        private Color pointColor(final double x, final double y) {
            // DRY for x and y?

            final double x1 = p1.getX();
            final double y1 = p1.getY();

            final double x2 = p2.getX();
            final double y2 = p2.getY();

            final double x3 = p3.getX();
            final double y3 = p3.getY();

            // docs:
            // https://en.wikipedia.org/wiki/Barycentric_coordinate_system#Conversion_between_barycentric_and_Cartesian_coordinates

            // n stands for numerator
            final double n1 = (y2 - y3) * (x - x3) + (x3 - x2) * (y - y3);
            final double n2 = (y3 - y1) * (x - x3) + (x1 - x3) * (y - y3);
            final double n3 = (y1 - y2) * (x - x1) + (x2 - x1) * (y - y1);

            // lambdas
            final double l1 = n1 * d;
            final double l2 = n2 * d;
            final double l3 = n3 * d;

            return colorer.get(new DefaultTriangleBarycentrics(l1, l2, l3));
        }

        private void drawPixel(final int x, final int y) {
            w.setColor(x, y, pointColor(x, y));
        }

        private void drawHLine(final int y, final int x1, final int x2) {
            for (int x = x1; x <= x2; x++) {
                drawPixel(x, y);
            }
        }

        private void drawFlatMax(
                final Point2D min,
                final Point2D max1,
                final Point2D max2) {
            final double minX = min.getX();
            final double minY = min.getY();
            final double maxY = max1.getY();

            final double dx1 = max1.getX() - minX;
            final double dy1 = max1.getY() - minY;

            final double dx2 = max2.getX() - minX;
            final double dy2 = max2.getY() - minY;

            final double delta1 = dx1 / dy1;
            final double delta2 = dx2 / dy2;

            double x1 = minX;
            double x2 = minX;

            for (int y = (int) minY; y <= maxY; y++) {
                // round doubles instead of floor?
                drawHLine(y, (int) x1, (int) x2);

                x1 += delta1;
                x2 += delta2;
            }
        }

        private void drawFlatMin(
                final Point2D max,
                final Point2D min1,
                final Point2D min2) {
            final double maxX = max.getX();
            final double maxY = max.getY();
            final double minY = min1.getY();

            final double dx1 = maxX - min1.getX();
            final double dy1 = maxY - min1.getY();

            final double dx2 = maxX - min2.getX();
            final double dy2 = maxY - min2.getY();

            final double delta1 = dx1 / dy1;
            final double delta2 = dx2 / dy2;

            double x1 = maxX;
            double x2 = maxX;

            for (int y = (int) maxY; y > minY; y--) {
                // round doubles instead of floor?
                drawHLine(y, (int) x1, (int) x2);

                x1 -= delta1;
                x2 -= delta2;
            }
        }

        private List<Point2D> sortedPoints() {
            final List<Point2D> points = new ArrayList<>();
            points.add(p1);
            points.add(p2);
            points.add(p3);

            points.sort(Comparator
                    .comparing(Point2D::getY)
                    .thenComparing(Point2D::getX));

            return points;
        }

        // docs:
        // https://www.sunshine2k.de/coding/java/TriangleRasterization/TriangleRasterization.html
        public void draw() {
            final List<Point2D> points = sortedPoints();

            final Point2D p1 = points.get(0);
            final Point2D p2 = points.get(1);
            final Point2D p3 = points.get(2);

            final double x1 = p1.getX();
            final double y1 = p1.getY();

            final double x2 = p2.getX();
            final double y2 = p2.getY();

            final double x3 = p3.getX();
            final double y3 = p3.getY();

            if (Arithmetic.equals(y2, y3)) {
                drawFlatMax(p1, p2, p3);
                return;
            }

            if (Arithmetic.equals(y1, y2)) {
                drawFlatMin(p3, p1, p2);
                return;
            }

            final double x4 = x1 + ((y2 - y1) / (y3 - y1)) * (x3 - x1);
            final Point2D p4 = new Point2D(x4, p2.getY());

            // non strict equality?
            if (Arithmetic.moreThan(x4, x2)) {
                drawFlatMax(p1, p2, p4);
                drawFlatMin(p3, p2, p4);
            } else {
                drawFlatMax(p1, p4, p2);
                drawFlatMin(p3, p4, p2);
            }
        }
    }

    @Override
    public void draw(final Point2D p1, final Point2D p2, final Point2D p3) {
        Objects.requireNonNull(p1);
        Objects.requireNonNull(p2);
        Objects.requireNonNull(p3);

        final Triangle triangle = new Triangle(
                getCtx().getPixelWriter(),
                getColorer(),
                p1, p2, p3);

        triangle.draw();
    }
}
