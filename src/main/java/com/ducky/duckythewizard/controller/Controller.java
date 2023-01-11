package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Game;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

/**Mithilfe der Superklasse Controller sollen alle Controller, die von dieser Klasse erben, die gleichen Methoden nutzen können.**/
public class Controller {

    private Game session;
    private GameConfig config;


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
}
