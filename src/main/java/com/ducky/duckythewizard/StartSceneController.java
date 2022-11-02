
package com.ducky.duckythewizard;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartSceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public StartSceneController() {
    }

    public void switchToHelpScene1(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load((URL)Objects.requireNonNull(this.getClass().getResource("helpScene1.fxml")));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public void switchToHelpScene2(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load((URL)Objects.requireNonNull(this.getClass().getResource("helpScene2.fxml")));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public void switchToHelpScene3(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load((URL)Objects.requireNonNull(this.getClass().getResource("helpScene3.fxml")));
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public void endHelpScene(ActionEvent event) throws IOException {
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Start reload = new Start();
        reload.start(this.stage);
    }
}
