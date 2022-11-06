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

public class HelpSceneController {
    private Stage stage;
    private Parent root;

    String css = Objects.requireNonNull(this.getClass().getResource("com/ducky/duckythewizard/styles/styleRenate.css")).toExternalForm();

    public HelpSceneController() {
    }

    private void getWindowStage(ActionEvent event, Parent root) {
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        setCss(scene);
        this.stage.setScene(scene);
        this.stage.show();
    }

    private void setCss(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);
    }

    public void switchToHelpScene1(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load((URL)Objects.requireNonNull(this.getClass().getResource("helpScenes/helpScene1.fxml")));
        getWindowStage(event, root);
    }


    public void switchToHelpScene2(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load((URL)Objects.requireNonNull(this.getClass().getResource("helpScenes/helpScene2.fxml")));
        getWindowStage(event, root);
    }

    public void switchToHelpScene3(ActionEvent event) throws IOException {
        Parent root = (Parent)FXMLLoader.load((URL)Objects.requireNonNull(this.getClass().getResource("helpScenes/helpScene3.fxml")));
        getWindowStage(event, root);
    }

    public void endHelpScene(ActionEvent event) throws IOException {
        this.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        WizardMainApplication reload = new WizardMainApplication();
        reload.start(this.stage);
    }
}