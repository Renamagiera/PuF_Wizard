
package com.ducky.duckythewizard;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {

    public void start(Stage stage) throws IOException {
        Parent root = (Parent)FXMLLoader.load((URL)Objects.requireNonNull(this.getClass().getResource("startingScene.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ducky the wizard");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
