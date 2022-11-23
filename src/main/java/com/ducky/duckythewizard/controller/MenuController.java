package com.ducky.duckythewizard.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    Button closeBtn;
    public void handleCloseBtnClicked(ActionEvent actionEvent) throws IOException {
        AnchorPane newRoot = FXMLLoader.load(getClass().getResource("game-view.fxml"));
        Stage primaryStage = (Stage) closeBtn.getScene().getWindow();
        primaryStage.getScene().setRoot(newRoot);
        newRoot.requestFocus();
    }
}
