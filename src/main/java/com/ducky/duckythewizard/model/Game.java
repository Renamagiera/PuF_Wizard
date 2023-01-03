package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.controller.*;
import com.ducky.duckythewizard.model.card.Card;
import com.ducky.duckythewizard.model.card.CardDeck;
import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class Game {

    private boolean isRunning;
    private boolean keyInput;

    private Player player; //Attribut für Daten zum Spieler selbst (Name etc.)
    private Card clickedCardFight;
    private Fight activeFight;
    //private Sprite ducky = new DuckySprite(5, collisionHandler); //Attribut für Player-Sprite
    private AnchorPane rootAnchorPane;
    private AnchorPane fightOverlay;
    private AnchorPane anchorPaneCards;
    private AnchorPane anchorPaneEndOverlay;


    private CardDeck cardDeck;
    private GameColorObject gameColorObject;

    //die Game Config wird einmalig zum Start des Games (Erstellung des Game-Klassen-Objekts) erstellt
    private GameConfig gameConfig;
    public GameObject[][] objectGrid; // [row][column] - is filled in GameController
    private ArrayList<Stone> stoneArrayList;

    private FightView fightView;
    private EndSceneView endSceneView;

    private GameController gameCtrl;
    private CardController cardCtrl;
    private MovementController movementCtrl;
    private StoneController stoneCtrl;
    private FightController fightCtrl;
    private SceneController sceneCtrl;

    public Game(){
        isRunning = true;
        keyInput = true;
        gameConfig = new GameConfig();
        this.gameColorObject = new GameColorObject();
        this.cardDeck = new CardDeck(this.gameColorObject);
        this.player = new Player(this.cardDeck);
        this.stoneArrayList = new ArrayList<>();
        this.fightView = new FightView();
        this.endSceneView = new EndSceneView();
        //System.out.println("*** Game-object is created.");
    }
    public boolean getIsRunning(){
        return isRunning;
    }
    public void toggleIsRunning() {
        isRunning = !isRunning;
    }
    public boolean getKeyInput() {
        return keyInput;
    }
    public void toggleKeyInput() {
        keyInput = !keyInput;
    }

    // create anstatt set, weil hier ein neues Controller-Objekt erstellt wird
    public void createGameCtrlObj(GameController gameCtrl) {
        this.gameCtrl = gameCtrl;
    }
    public void createCardCtrlObj() {
        this.cardCtrl = new CardController(this);
    }
    public void createMovementCtrlObj() {
        this.movementCtrl = new MovementController(this);
    }
    public void createStoneCtrlObj() {
        this.stoneCtrl = new StoneController(this);
    }
    public void createFightCtrlObj() {
        this.fightCtrl = new FightController(this);
    }
    public void createSceneCtrlObj() {
        this.sceneCtrl = new SceneController(); }

    public GameConfig getGameConfig() {
        return this.gameConfig;
    }

    public GameController getGameCtrl() {
        return gameCtrl;
    }
    public CardController getCardCtrl() {
        return this.cardCtrl;
    }
    public MovementController getMovementCtrl() {
        return this.movementCtrl;
    }
    public StoneController getStoneCtrl() {
        return stoneCtrl;
    }
    public FightController getFightCtrl() {
        return fightCtrl;
    }
    public SceneController getSceneCtrl() {
        return this.sceneCtrl;
    }

    public CardDeck getCardDeck() {
        return this.cardDeck;
    }

    public AnchorPane getAnchorPaneCards() {
        return this.anchorPaneCards;
    }
    public AnchorPane getFightOverlay() {
        return this.fightOverlay;
    }
    public AnchorPane getAnchorPaneEndOverlay() {
        return anchorPaneEndOverlay;
    }

    public EndSceneView getEndSceneView() {
        return this.endSceneView;
    }

    public void setAnchorPaneCards(AnchorPane anchorPane) {
        this.anchorPaneCards = anchorPane;
    }

    public AnchorPane getRootAnchorPane() {
        return rootAnchorPane;
    }

    public void setRootAnchorPane(AnchorPane rootAnchorPane) {
        this.rootAnchorPane = rootAnchorPane;
    }
    public void setFightOverlay(AnchorPane fightOverlay) {
        this.fightOverlay = fightOverlay;
    }

    public void setAnchorPaneEndOverlay(AnchorPane anchorPaneEndOverlay) {
        this.anchorPaneEndOverlay = anchorPaneEndOverlay;
    }

    public void setEndSceneView(EndSceneView endSceneView) {
        this.endSceneView = endSceneView;
    }

    public GameColorObject getGameColorObject() {
        return gameColorObject;
    }


    public Player getPlayer() {
        return this.player;
    }

    public ArrayList<Stone> getStoneArrayList() {
        return this.stoneArrayList;
    }

    public Card getClickedCardFight() {
        return clickedCardFight;
    }

    public void setClickedCardFight(Card clickedCardFight) {
        this.clickedCardFight = clickedCardFight;
    }

    public Fight getActiveFight() {
        return activeFight;
    }

    public void setActiveFight(Fight activeFight) {
        this.activeFight = activeFight;
    }

    public FightView getFightView() {
        return fightView;
    }
}
