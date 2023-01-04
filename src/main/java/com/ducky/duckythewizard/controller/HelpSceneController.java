package com.ducky.duckythewizard.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.util.Objects;

public class HelpSceneController extends SceneController {

    private Parent root;

    @FXML
     /*anhand des Events wird die ID des Buttons ermittelt und die passende FMXL-Scene als root übergeben*/
    private void switchToScene(Event event) throws IOException {
        String labelId = ((Label)event.getSource()).getId();
        String scene = switch (labelId) {
            case "helpButton", "backTo1" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene1.fxml";
            case "nextTo2", "backTo2" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene2.fxml";
            case "nextTo3" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene3.fxml";
            case "controlsButton" -> "/com/ducky/duckythewizard/scenes/helpScenes/settings.fxml";
            default -> "";
        };
        this.root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource(scene)));
        super.getWindowStage(event, this.root);
        addRadioButtonsToGroup();
    }

    public void startGame(Event event) throws IOException {
        super.startGame(event);
    }

    public void backToMenu(Event event) throws IOException {
        super.backToMenu(event);
    }

    public void switchToControls(Event event) throws IOException {
        this.root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/scenes/helpScenes/controls.fxml")));
        super.getWindowStage(event, this.root);
    }

    private void addRadioButtonsToGroup() {
        if (this.root.lookup("#red") != null) {
            ToggleGroup group = new ToggleGroup();

            RadioButton red = (RadioButton) this.root.lookup("#red");
            RadioButton blue = (RadioButton) this.root.lookup("#blue");
            RadioButton normal = (RadioButton) this.root.lookup("#normal");

            red.setToggleGroup(group);
            blue.setToggleGroup(group);
            normal.setToggleGroup(group);

            if (SceneController.getSkin().equals("normal")) {
                normal.setSelected(true);
            }

            switch (SceneController.getSkin()) {
                case "normal" -> normal.setSelected(true);
                case "red" -> red.setSelected(true);
                case "blue" -> blue.setSelected(true);
            }

            group.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
                RadioButton clickedButton = (RadioButton) group.getSelectedToggle();
                if (clickedButton != null) {
                    super.setSkin(clickedButton.getText());
                }
            });
        }
    }
}