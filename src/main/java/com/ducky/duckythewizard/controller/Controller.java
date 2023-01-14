package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Game;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**Mithilfe der Superklasse Controller sollen alle Controller, die von dieser Klasse erben, die gleichen Methoden nutzen können.**/
public class Controller {

    private Game session;
    private GameConfig config;
    String css = Objects.requireNonNull(this.getClass().getResource("/com/ducky/duckythewizard/styles/style.css")).toExternalForm();


    public Controller() {

    }
    public Controller(Game game) {
        this.session = game;
        this.config = game.getGameConfig();
    }

    public Game getSession() {
        return this.session;
    }

    public GameConfig getGameConfig() {
        return this.config;
    }

    public void getWindowStage(Event event, Parent root) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        // show is always in controller class
        stage.show();
    }
}
