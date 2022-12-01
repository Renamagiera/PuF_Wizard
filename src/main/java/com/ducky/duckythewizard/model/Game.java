package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.Scene;

public class Game {

    private boolean isRunning;
    private Player player;
    private CardDeck cardDeck;
    private GameConfig gameConfig;
    public GameObject[][] objectGrid; // [row][column]
    private Scene startScene;
    private Scene deathScene;
    private Scene winScene;
    private Scene playScene;


    public Game(){
        isRunning = true;
    }
    public boolean getIsRunning(){
        return isRunning;
    }
    public void toggleIsRunning() {
        isRunning = !isRunning;
    }
}
