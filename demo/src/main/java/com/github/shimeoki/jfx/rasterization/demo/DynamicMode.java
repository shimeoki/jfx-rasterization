package com.github.shimeoki.jfx.rasterization.demo;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.github.shimeoki.jfx.rasterization.color.HTMLColors;
import com.github.shimeoki.jfx.rasterization.geom.Vector;
import com.github.shimeoki.jfx.rasterization.geom.Vector2D;
import com.github.shimeoki.jfx.rasterization.triangle.DDATriangler;
import com.github.shimeoki.jfx.rasterization.triangle.Triangler;
import com.github.shimeoki.jfx.rasterization.triangle.color.MonotoneTriangleColorer;
import com.github.shimeoki.jfx.rasterization.triangle.geom.DynamicTriangle;
import com.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class DynamicMode {

    @FXML
    private AnchorPane root;

    @FXML
    private Pane canvasPane;

    @FXML
    private Canvas canvas;

    @FXML
    private Button addBtn;

    @FXML
    private Button clearBtn;

    private final Random rnd = new Random();

    private final List<Vector2D> vertices = new LinkedList<>();

    private final List<Triangle> triangles = new LinkedList<>();

    private Triangler triangler;

    @FXML
    private void initialize() {
        initTriangler();
        initCanvas();
        initClearBtn();
    }

    private void initTriangler() {
        triangler = new DDATriangler(
                canvas.getGraphicsContext2D().getPixelWriter(),
                new MonotoneTriangleColorer(HTMLColors.BLUE));
    }

    private void initClearBtn() {
        clearBtn.setOnAction(e -> {
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
    }

    private void clearCanvas() {
        canvas.getGraphicsContext2D()
                .clearRect(
                        0,
                        0,
                        canvas.getWidth(),
                        canvas.getHeight());
    }

    private Vector2D randomVector() {
        final float x = rnd.nextFloat((float) canvas.getWidth());
        final float y = rnd.nextFloat((float) canvas.getHeight());

        return new Vector(x, y);
    }

    private void addTriangle() {
        final Vector2D v1 = randomVector();
        final Vector2D v2 = randomVector();
        final Vector2D v3 = randomVector();

        final Triangle t = new DynamicTriangle(v1, v2, v3);

        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        triangles.add(t);
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
