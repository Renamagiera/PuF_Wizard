package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.controller.CardController;
//import com.ducky.duckythewizard.controller.LevelController; //Level-Auslagerungsversuch
import com.ducky.duckythewizard.controller.MovementController;
import javafx.scene.Scene;

public class Game {

    private boolean isRunning;
    private Player player; //Attribut für Daten zum Spieler selbst (Name etc.)
    //private Sprite ducky = new DuckySprite(5, collisionHandler); //Attribut für Player-Sprite
    private CardDeck cardDeck;

    //die Game Config wird einmalig zum Start des Games (Erstellung des Game-Klassen-Objekts) erstellt
    private GameConfig gameConfig = new GameConfig();
    public GameObject[][] objectGrid; // [row][column]

    private Scene startScene;
    private Scene deathScene;
    private Scene winScene;
    private Scene playScene;

    private CardController cardCtrl;
    private MovementController movementCtrl;
    //private LevelController levelCtrl; //Level-Auslagerungsversuch

    //private Level level; //Level-Auslagerungsversuch

    //private Stage primaryStage; //wir sollten 1 Stage haben, der allen bekannt ist


    public Game(){
        isRunning = true;
    }
    public boolean getIsRunning(){

        return isRunning;
    }
    public void toggleIsRunning() {

        isRunning = !isRunning;
    }

    // create andstatt set, weil hier einmalig der Controller erstellt wird
    public void createCardCtrlObj() {
        this.cardCtrl = new CardController(this);
    }
    public void createMovementCtrlObj() {
        this.movementCtrl = new MovementController(this);
    }
    /*public void createLevelCtrlObj() {
        this.levelCtrl = new LevelController(this);
    }
    public void createLevel() {
        this.level = new Level(this);
    }*/ ////Level-Auslagerungsversuch

    public void setGameObjectGrid(GameObject[][] obj) {

        this.objectGrid = obj;
    }


    public GameConfig getGameConfig() {

        return this.gameConfig;
    }

    public CardController getCardCtrl() {

        return this.cardCtrl;
    }
    public MovementController getMovementCtrl() {

        return this.movementCtrl;
    }
    /*public LevelController getLevelCtrl() {
        return this.levelCtrl;
    }
    public Level getLevel() {
        return this.level;
    }*/ //Level-Auslagerungsversuch



}
