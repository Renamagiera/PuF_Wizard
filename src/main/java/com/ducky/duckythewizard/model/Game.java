package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.controller.*;
import com.ducky.duckythewizard.model.card.CardDeckModel;
import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class Game {

    private boolean isRunning;
    private boolean inFight;
    private boolean gameOver;

    private String sprite;
    private Player player;
    private Fight activeFight;
    private AnchorPane rootAnchorPane;
    private AnchorPane fightOverlay;
    private AnchorPane anchorPaneEndOverlay;


    private CardDeckModel cardDeckModel;
    private GameColorObject gameColorObject;

    //die Game Config wird einmalig zum Start des Games (Erstellung des Game-Klassen-Objekts) erstellt
    private GameConfig gameConfig;
    public GameObject[][] objectGrid; // [row][column] - is filled in GameController
    private ArrayList<Stone> stoneArrayList;

    private FightModel fightModel;
    private EndSceneView endSceneView;

    private GameController gameCtrl;
    private CardController cardCtrl;
    private MovementController movementCtrl;
    private StoneController stoneCtrl;
    private FightController fightCtrl;
    private SceneController sceneCtrl;

    public Game(){
        isRunning = true;
        inFight = false;
        gameOver = false;
        gameConfig = new GameConfig();
        this.gameColorObject = new GameColorObject();
        this.cardDeckModel = new CardDeckModel();
        this.player = new Player();
        this.stoneArrayList = new ArrayList<>();
        this.fightModel = new FightModel();
        this.endSceneView = new EndSceneView();
        //System.out.println("*** Game-object is created.");
    }
    public boolean getIsRunning(){
        return this.isRunning;
    }
    public boolean getInFight() {
        return this.inFight;
    }
    public boolean getGameOver() {
        return this.gameOver;
    }
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        System.out.println(this.gameOver);
    }

    public void toggleIsRunning() {
        isRunning = !isRunning;
    }
    public void toggleInFight() {
        inFight = !inFight;
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

    public String getSprite() {
        return sprite;
    }
    public CardDeckModel getCardDeckModel() {
        return this.cardDeckModel;
    }
    public Player getPlayer() {
        return this.player;
    }
    public ArrayList<Stone> getStoneArrayList() {
        return this.stoneArrayList;
    }
    public Fight getActiveFight() {
        return activeFight;
    }

    public AnchorPane getRootAnchorPane() {
        return rootAnchorPane;
    }
    public AnchorPane getAnchorPaneEndOverlay() {
        return anchorPaneEndOverlay;
    }

    public FightModel getFightView() {
        return fightModel;
    }
    public EndSceneView getEndSceneView() {
        return this.endSceneView;
    }

    public GameColorObject getGameColorObject() {
        return gameColorObject;
    }

    public void setAnchorPaneEndOverlay(AnchorPane anchorPaneEndOverlay) {
        this.anchorPaneEndOverlay = anchorPaneEndOverlay;
    }
    public void setActiveFight(Fight activeFight) {
        this.activeFight = activeFight;
    }
    public void setRootAnchorPane(AnchorPane rootAnchorPane) {
        this.rootAnchorPane = rootAnchorPane;
    }
    public void setFightOverlay(AnchorPane fightOverlay) {
        this.fightOverlay = fightOverlay;
    }
    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

}
