package com.github.shimeoki.jfx.rasterization.demo;

import java.util.LinkedList;
import java.util.List;

import com.github.shimeoki.jfx.rasterization.color.HTMLColors;
import com.github.shimeoki.jfx.rasterization.triangle.DDATriangler;
import com.github.shimeoki.jfx.rasterization.triangle.Triangler;
import com.github.shimeoki.jfx.rasterization.triangle.color.MonotoneTriangleColorer;
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
