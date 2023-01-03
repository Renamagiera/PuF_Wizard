package com.ducky.duckythewizard.controller;

import java.io.IOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelpSceneController extends SceneController {

    public HelpSceneController() {
    }

    @FXML
     /*anhand des Events wird die ID des Buttons ermittelt und die passende FMXL-Scene als root übergeben*/
    private void switchToScene(Event event) throws IOException {
        String labelId = ((Label)event.getSource()).getId();
        String scene = switch (labelId) {
            case "helpButton", "backTo1" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene1.fxml";
            case "nextTo2", "backTo2" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene2.fxml";
            case "nextTo3" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene3.fxml";
            case "controlsButton" -> "/com/ducky/duckythewizard/scenes/helpScenes/controls.fxml";
            default -> "";
        };
        Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource(scene)));
        super.getWindowStage(event, root);
    }

    public void startGame(Event event) throws IOException {
        super.startGame(event);
    }

    public void backToMenu(Event event) throws IOException {
        super.backToMenu(event);
    }

}