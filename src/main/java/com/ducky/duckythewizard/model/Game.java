package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.controller.*;
import com.ducky.duckythewizard.controller.scenes.EndSceneController;
import com.ducky.duckythewizard.controller.scenes.MenuController;
import com.ducky.duckythewizard.model.card.CardDeckModel;
import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

/**This class constructs a Game object which in turn is the heart of
 each running game client. Every important Game object such as sprites and scenes, controllers etc.
 is saved into the game's attributes.*/

public class Game {

    private boolean isRunning;
    private boolean inFight;
    private boolean gameOver;

    private Player player;
    private Fight activeFight;
    private AnchorPane rootAnchorPane;
    private AnchorPane anchorPaneEndOverlay;


    private CardDeckModel cardDeckModel;
    private GameColorObject gameColorObject;

    //die Game Config wird einmalig zum Start des Games (Erstellung des Game-Klassen-Objekts) erstellt
    private GameConfig gameConfig;
    public GameObject[][] objectGrid; // [row][column] - is filled in GameController
    private ArrayList<Stone> stoneArrayList;

    private FightSceneModel fightSceneModel;
    private EndSceneModel endSceneModel;

    private GameController gameCtrl;
    private CardController cardCtrl;
    private StoneController stoneCtrl;
    private FightController fightCtrl;
    private MenuController menuCtrl;
    private EndSceneController endSceneCtrl;
    private CollisionController collisionController;

    private MyAnimationTimer animationTimer;

    public Game(){
        this.isRunning = true;
        this.inFight = false;
        this.gameOver = false;
        this.gameConfig = new GameConfig();
        //this.gameColorObject = new GameColorObject();
        this.cardDeckModel = new CardDeckModel();
        this.player = Host.getInstance();
        this.player.resetPlayer();
        this.player.setSession(this);
        this.stoneArrayList = new ArrayList<>();
        this.fightSceneModel = new FightSceneModel();
        this.endSceneModel = new EndSceneModel();
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
    }

    public void toggleIsRunning() {
        this.isRunning = !isRunning;
    }
    public void toggleInFight() {
        this.inFight = !inFight;
    }

    // create anstatt set, weil hier ein neues Controller-Objekt erstellt wird
    public void createGameCtrlObj(GameController gameCtrl) {
        this.gameCtrl = gameCtrl;
    }
    public void createCardCtrlObj() {
        this.cardCtrl = new CardController(this);
    }
    public void createStoneCtrlObj() {
        this.stoneCtrl = new StoneController(this);
    }
    public void createFightCtrlObj() {
        this.fightCtrl = new FightController(this);
    }
    public void createMenuCtrlObj() {
        this.menuCtrl = new MenuController();
    }
    public void createEndSceneCtrlObj() {
        this.endSceneCtrl = new EndSceneController(this);
    }
    public void createGameColorObj() {
        this.gameColorObject = new GameColorObject();
    }
    public void createCollisionCtrlObj() { this.collisionController = new CollisionController(this, GameConfig.LEVEL_CELL_HEIGHT, GameConfig.LEVEL_CELL_WIDTH);}

    public GameObject[][] getObjectGrid() {return this.objectGrid;}
    public GameConfig getGameConfig() {
        return this.gameConfig;
    }

    public GameController getGameCtrl() {
        return this.gameCtrl;
    }
    public CardController getCardCtrl() {
        return this.cardCtrl;
    }
    public StoneController getStoneCtrl() {
        return this.stoneCtrl;
    }
    public FightController getFightCtrl() {
        return this.fightCtrl;
    }
    public MenuController getMenuCtrl() {
        return this.menuCtrl;
    }
    public EndSceneController getEndSceneCtrl() {
        return this.endSceneCtrl;
    }
    public GameColorObject getGameColorObject() {
        return this.gameColorObject;
    }
    public CollisionController getCollisionCtrl() { return this.collisionController;}

    public MyAnimationTimer getAnimationTimer() { return this.animationTimer;}

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
        return this.activeFight;
    }

    public AnchorPane getRootAnchorPane() {
        return rootAnchorPane;
    }
    public AnchorPane getAnchorPaneEndOverlay() {
        return anchorPaneEndOverlay;
    }

    public FightSceneModel getFightView() {
        return this.fightSceneModel;
    }
    public EndSceneModel getEndSceneView() {
        return this.endSceneModel;
    }
    public void setObjectGrid(GameObject[][] grid) {
        this.objectGrid = grid;
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
    }
    public void setAnimationTimer(MyAnimationTimer timer) { this.animationTimer = timer;}

}
