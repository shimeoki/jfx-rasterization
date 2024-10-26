package com.github.shimeoki.jfx_rasterization.app;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

public class RasterizationController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty()
                .addListener(
                        (ov, oldValue, newValue) -> canvas.setWidth(
                                newValue.doubleValue()));
        anchorPane.prefHeightProperty()
                .addListener(
                        (ov, oldValue, newValue) -> canvas.setHeight(
                                newValue.doubleValue()));
    }
}
