package com.ducky.duckythewizard.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HelpSceneController {

    public Button playButton;
    public Button helpButton;
    @FXML
    private Button next3;

    String css = Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/styles/styleRenate.css")).toExternalForm();

    public HelpSceneController() {
    }

    private void getWindowStage(ActionEvent event, Parent root) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        setCss(scene);
        stage.setScene(scene);
        stage.show();
    }

    private void setCss(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);
    }

    public void switchToHelpScene1(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load((URL)Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/scenes/helpScenes/helpScene1.fxml")));
        getWindowStage(event, root);
    }


    public void switchToHelpScene2(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load((URL)Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/scenes/helpScenes/helpScene2.fxml")));
        getWindowStage(event, root);
    }

    public void switchToHelpScene3(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load((URL)Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/scenes/helpScenes/helpScene3.fxml")));
        getWindowStage(event, root);
    }

    public void switchToControls(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load((URL)Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/scenes/helpScenes/controls.fxml")));
        getWindowStage(event, root);
    }

    public void startGame(ActionEvent event) throws IOException {
        // Britta's Code zum Starten des Games
        AnchorPane newRoot = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/scenes/game-view.fxml")));
        //Stage primaryStage = (Stage) next3.getScene().getWindow();
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