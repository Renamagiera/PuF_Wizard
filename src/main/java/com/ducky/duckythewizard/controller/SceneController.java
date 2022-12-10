package com.ducky.duckythewizard.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/*Superclass of all SceneControllers*/
public class SceneController {

    String css = Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/styles/styleMainGameView.css")).toExternalForm();

    /*die bereits beim Start erstellte Stage wird anhand des Events ermittelt. Darauf wird eine neue Scene erstellt*/
    protected void getWindowStage(ActionEvent event, Parent root) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }
}
