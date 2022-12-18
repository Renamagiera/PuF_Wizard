package com.ducky.duckythewizard.controller;

import java.io.IOException;
import java.util.Objects;

import com.ducky.duckythewizard.view.GameScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HelpSceneController extends SceneController {

    public HelpSceneController() {
    }

    @FXML
     /*anhand des Events wird die ID des Buttons ermittelt und die passende FMXL-Scene als root übergeben*/
    private void switchToScene(ActionEvent event) throws IOException {
        String btnId = ((Button)event.getSource()).getId();
        String scene = switch (btnId) {
            case "helpButton", "backTo1" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene1.fxml";
            case "nextTo2", "backTo2" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene2.fxml";
            case "nextTo3" -> "/com/ducky/duckythewizard/scenes/helpScenes/helpScene3.fxml";
            case "controlsButton" -> "/com/ducky/duckythewizard/scenes/helpScenes/controls.fxml";
            default -> "";
        };
        Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource(scene)));
        super.getWindowStage(event, root);
    }

    public void startGame(ActionEvent event) throws IOException {
        AnchorPane newRoot = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/scenes/game-view.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(newRoot);
        newRoot.requestFocus();
    }

    public void endHelpScenes(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WizardMainApplication reload = new WizardMainApplication();
        reload.start(stage);
    }

}