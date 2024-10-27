package com.github.shimeoki.jfx_rasterization.app;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

public class RasterizationController {

    @FXML
    private AnchorPane root;

    @FXML
    private TilePane tiles;

    @FXML
    private void initialize() {
        initTiles();
    }

    private void initTiles() {
        tiles.setPrefRows(3);
        tiles.setPrefColumns(3);
    }
}
