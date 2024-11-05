package com.github.shimeoki.jfx.rasterization.demo;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class Controller {

    @FXML
    private AnchorPane root;

    @FXML
    private StackPane modePane;

    @FXML
    private RadioButton staticModeBtn;

    @FXML
    private RadioButton dynamicModeBtn;

    @FXML
    private RadioButton interactiveModeBtn;

    @FXML
    private InteractiveMode interactiveModeController;

    @FXML
    private DynamicMode dynamicModeController;

    @FXML
    private StaticMode staticModeController;

    @FXML
    private void initialize() {
        initTgBtns();
    }

    private void initTgBtns() {
        final AnchorPane interactiveMode = interactiveModeController.root();
        final AnchorPane dynamicMode = dynamicModeController.root();
        final AnchorPane staticMode = staticModeController.root();

        staticModeBtn.setOnAction(e -> {
            modePane.getChildren().clear();
            modePane.getChildren().add(staticMode);
        });

        interactiveModeBtn.setOnAction(e -> {
            modePane.getChildren().clear();
            modePane.getChildren().add(interactiveMode);
        });

        dynamicModeBtn.setOnAction(e -> {
            modePane.getChildren().clear();
            modePane.getChildren().add(dynamicMode);
        });
    }
}
