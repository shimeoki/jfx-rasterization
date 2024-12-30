package io.github.shimeoki.jfx.rasterization.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.github.shimeoki.jfx.rasterization.HTMLColorf;
import io.github.shimeoki.jfx.rasterization.geom.Point2f;
import io.github.shimeoki.jfx.rasterization.geom.Vector2f;
import io.github.shimeoki.jfx.rasterization.DDATriangler;
import io.github.shimeoki.jfx.rasterization.Triangler;
import io.github.shimeoki.jfx.rasterization.triangle.color.GradientTriangleFiller;
import io.github.shimeoki.jfx.rasterization.triangle.geom.Polygon3;
import io.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
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

    private final List<Point2f> points = new ArrayList<>();

    private final List<Triangle> triangles = new LinkedList<>();

    private Triangler triangler;

    @FXML
    private void initialize() {
        initTriangler();
        initCanvas();
        initClearBtn();
    }

    private void initTriangler() {
        triangler = new DDATriangler(canvas.getGraphicsContext2D());
        triangler.setFiller(new GradientTriangleFiller(
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

        triangles.add(new Polygon3(
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
            triangler.draw(t);
        }
    }

    public AnchorPane root() {
        return root;
    }
}
