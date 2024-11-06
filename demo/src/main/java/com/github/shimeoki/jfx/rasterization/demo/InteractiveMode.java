package com.github.shimeoki.jfx.rasterization.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.github.shimeoki.jfx.rasterization.color.HTMLColorf;
import com.github.shimeoki.jfx.rasterization.geom.Pos2f;
import com.github.shimeoki.jfx.rasterization.geom.Vector2f;
import com.github.shimeoki.jfx.rasterization.triangle.BufferedDDATriangler;
import com.github.shimeoki.jfx.rasterization.triangle.DDATriangler;
import com.github.shimeoki.jfx.rasterization.triangle.Triangler;
import com.github.shimeoki.jfx.rasterization.triangle.color.DynamicTriangleGradient;
import com.github.shimeoki.jfx.rasterization.triangle.color.StaticGradientTriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.color.TriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.geom.StaticTriangle;
import com.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class InteractiveMode {

    @FXML
    private AnchorPane root;

    @FXML
    private Pane canvasPane;

    @FXML
    private Canvas canvas;

    @FXML
    private Button clearBtn;

    private final List<Pos2f> points = new ArrayList<>();

    private final List<Triangle> triangles = new LinkedList<>();

    private Triangler triangler;
    private PixelWriter writer;
    private TriangleColorer colorer;

    @FXML
    private void initialize() {
        initTriangler();
        initCanvas();
        initClearBtn();
    }

    private void initTriangler() {
        triangler = new BufferedDDATriangler();
        writer = canvas.getGraphicsContext2D().getPixelWriter();
        colorer = new StaticGradientTriangleColorer(
                new DynamicTriangleGradient(
                        HTMLColorf.AQUA,
                        HTMLColorf.FUCHSIA,
                        HTMLColorf.LIME));
    }

    private void initClearBtn() {
        clearBtn.setOnAction(e -> {
            points.clear();
            triangles.clear();
            clearCanvas();
        });
    }

    private void initCanvas() {
        canvasPane.widthProperty()
                .addListener((ov, oldValue, newValue) -> {
                    canvas.setWidth(newValue.doubleValue());
                    draw();
                });
        canvasPane.heightProperty()
                .addListener((ov, oldValue, newValue) -> {
                    canvas.setHeight(newValue.doubleValue());
                    draw();
                });

        canvas.setOnMousePressed(e -> {
            addPoint((float) e.getX(), (float) e.getY());
            handlePoints();
            draw();
        });
    }

    private void addPoint(final float x, final float y) {
        points.add(new Vector2f(x, y));
    }

    private void handlePoints() {
        if (points.size() != 3) {
            return;
        }

        triangles.add(new StaticTriangle(
                points.get(0),
                points.get(1),
                points.get(2)));

        points.clear();
    }

    private void clearCanvas() {
        canvas.getGraphicsContext2D()
                .clearRect(
                        0,
                        0,
                        canvas.getWidth(),
                        canvas.getHeight());
    }

    private void draw() {
        clearCanvas();

        for (final Triangle t : triangles) {
            triangler.draw(writer, t, colorer);
        }
    }

    public AnchorPane root() {
        return root;
    }
}
