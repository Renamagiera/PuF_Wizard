package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.config.GameConfig;
import com.ducky.duckythewizard.controller.CardController;
//import com.ducky.duckythewizard.controller.LevelController; //Level-Auslagerungsversuch
import com.ducky.duckythewizard.controller.MovementController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class Game {

    private boolean isRunning;
    private Player player; //Attribut für Daten zum Spieler selbst (Name etc.)
    //private Sprite ducky = new DuckySprite(5, collisionHandler); //Attribut für Player-Sprite
    private CardDeck cardDeck;
    private ArrayList<Card> handCards;
    private AnchorPane anchorPaneCards;
    private GameColors gameColors;
    private ArrayList<TrumpColor> trumpColors;

    //die Game Config wird einmalig zum Start des Games (Erstellung des Game-Klassen-Objekts) erstellt
    private GameConfig gameConfig;
    public GameObject[][] objectGrid; // [row][column] - is filled in GameController

    private Scene startScene;
    private Scene deathScene;
    private Scene winScene;
    private Scene playScene;

    private CardController cardCtrl;
    private MovementController movementCtrl;

    public Game(){
        isRunning = true;
        gameConfig = new GameConfig();
        gameColors = new GameColors();
        trumpColors = this.gameColors.getTrumpColors();
        cardDeck = new CardDeck(gameColors);
        handCards = this.cardDeck.dealHandCards(this.cardDeck.getCardDeck());  // TODO starting game == dealing 5 new hand cards?
        //System.out.println("*** Game-object is created.");
    }
    public boolean getIsRunning(){

        return isRunning;
    }
    public void toggleIsRunning() {
        isRunning = !isRunning;
    }

    // create anstatt set, weil hier ein neues Controller-Objekt erstellt wird
    public void createCardCtrlObj() {
        this.cardCtrl = new CardController(this);
    }
    public void createMovementCtrlObj() {
        this.movementCtrl = new MovementController(this);
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

    public CardDeck getCardDeck() {
        return this.cardDeck;
    }

    public ArrayList<Card> getHandCards() {
        return this.handCards;
    }

    public AnchorPane getAnchorPaneCards() {
        return anchorPaneCards;
    }

    public void setAnchorPaneCards(AnchorPane anchorPane) {
        this.anchorPaneCards = anchorPane;
    }

}
