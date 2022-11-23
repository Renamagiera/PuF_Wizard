package com.ducky.duckythewizard.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class WizardMainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = (Parent)FXMLLoader.load((URL) Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/scenes/startingScene.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("Ducky the wizard");
        String css = Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/styles/styleRenate.css")).toExternalForm();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

    /*@Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(GroundApplication.class.getResource("ground-forest-view.fxml"));
        // Image fxmlImage = new Scene(FXMLLoader.load(getClass().getResource("ground-forest-view.fxml"))).snapshot(null);
        // Scene theScene = new Scene(fxmlLoader.load(), 500, 500);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-view.fxml"));
        Parent root = loader.load();
        // !!! MyPuFGameController controller = loader.getController();

        stage.setTitle("Do not die");
        Scene theScene = new Scene( root );
        stage.setScene( theScene );

        stage.show();
        root.requestFocus();
    }*/