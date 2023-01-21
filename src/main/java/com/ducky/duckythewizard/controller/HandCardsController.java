package com.ducky.duckythewizard.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HandCardsController extends VBox {

    @FXML
    private AnchorPane emptyCardSlots2;

    public HandCardsController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/com/ducky/duckythewizard/scenes/handCards.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
