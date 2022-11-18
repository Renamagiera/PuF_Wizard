package com.ducky.duckythewizard.model;

import javafx.scene.Scene;

public class Game {

    private boolean isRunning;
    private Player player;
    private CardDeck cardDeck;
    private GameConfig gameConfig;
    // private Sprite[][] levelGrid; ??? save level tiles and rocks in a grid variable ???
    private Scene startScene;
    private Scene deathScene;
    private Scene winScene;
    private Scene playScene;
}
