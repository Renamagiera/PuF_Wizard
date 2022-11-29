package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Game;
import com.ducky.duckythewizard.model.GameConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class WizardMainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("start-method");
        Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/scenes/startingScene.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("Ducky & the Wizard Stones");
        String css = Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/styles/styleRenate.css")).toExternalForm();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("main-method");
        // hier vielleicht am Anfang alle Game-Configurationen initialisieren?
        GameConfig.initialize();
        launch();
    }
}