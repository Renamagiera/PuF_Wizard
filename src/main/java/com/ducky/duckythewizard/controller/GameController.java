package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Game;
import com.ducky.duckythewizard.model.MyAnimationTimer;
import com.ducky.duckythewizard.model.Stone;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.util.ArrayList;

public class GameController{

    public Game session = new Game();

    @FXML
    private AnchorPane rootBox;
    @FXML
    private Canvas mainCanvas;
    @FXML
    private GridPane levelGrid;
    private GraphicsContext gc;

    @FXML
    private Label timerLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private HBox heartContainer;
    @FXML
    private Label cards;

    /*player's hand-cards*/
    @FXML
    private ImageView handCard0;
    @FXML
    private ImageView handCard1;
    @FXML
    private ImageView handCard2;
    @FXML
    private ImageView handCard3;
    @FXML
    private ImageView handCard4;

    /*fight-scene*/
    @FXML
    private AnchorPane fightOverlay;
    @FXML
    private Label trumpColorText;
    @FXML
    private Label cardChooseText;
    @FXML
    private ImageView stoneCard;
    @FXML
    private ImageView playerCard;
    @FXML
    private Label winLossLabel;
    @FXML
    private Label exitFightView;

    /*end-scene*/
    @FXML
    private AnchorPane endScene;
    @FXML
    private Label endSceneLabel;
    @FXML
    private Label score;
    @FXML
    private Label exitLabelEndView;
    @FXML
    private Label timerTextLabel;
    private ArrayList<String> input = new ArrayList<>();
    private BooleanProperty collisionDetected = new SimpleBooleanProperty(false);

    @FXML
    public void initialize() {
        //System.out.println("*** Game Controller is initialized...");

        this.session.setRootAnchorPane(this.rootBox);
        this.session.setFightOverlay(this.fightOverlay);
        this.session.setAnchorPaneEndOverlay(this.endScene);

        // fight- and end-overlay not visible
        this.fightOverlay.setVisible(false);
        this.endScene.setVisible(false);

        //zum Start des Games werden die Controller erstellt und erhalten in ihren Konstruktoren einen Verweis auf das Game-Objekt
        this.session.createGameCtrlObj(this);
        this.session.createCardCtrlObj();
        this.session.createStoneCtrlObj();
        this.session.createFightCtrlObj();
        this.session.createMenuCtrlObj();
        this.session.createEndSceneCtrlObj();
        this.session.createGameColorObj();

        // initialize fight-view-scene local variables
        this.session.getFightView().initLocalVariables(this.session.getGameColorObject(), this.fightOverlay);

        // initialize cards: set card-anchor-pane, render hand-cards
        this.session.getCardCtrl().cardInit();

        // bindings player's hand-cards
        this.handCard0.imageProperty().bind(this.session.getCardDeckModel().handCard0.imageProperty());
        this.handCard1.imageProperty().bind(this.session.getCardDeckModel().handCard1.imageProperty());
        this.handCard2.imageProperty().bind(this.session.getCardDeckModel().handCard2.imageProperty());
        this.handCard3.imageProperty().bind(this.session.getCardDeckModel().handCard3.imageProperty());
        this.handCard4.imageProperty().bind(this.session.getCardDeckModel().handCard4.imageProperty());

        // bindings fight-view
        this.cards.textProperty().bind(session.getCardDeckModel().cardsProperty);
        this.trumpColorText.textProperty().bind(session.getFightView().trumpColorTextProperty);
        this.trumpColorText.styleProperty().bind(session.getFightView().trumpColorTextStyleProperty);
        this.cardChooseText.textProperty().bind(session.getFightView().cardChooseTextProperty);
        this.stoneCard.imageProperty().bind(session.getFightView().stoneCardProperty.imageProperty());
        this.playerCard.imageProperty().bind(session.getFightView().playerCardProperty.imageProperty());
        this.winLossLabel.textProperty().bind(session.getFightView().winLossLabelProperty);
        this.winLossLabel.styleProperty().bind(session.getFightView().winLossLabelStyleProperty);
        this.fightOverlay.styleProperty().bind(session.getFightView().fightOverlayStyleProperty);
        this.fightOverlay.visibleProperty().bind(session.getFightView().fightOverlayVisible);
        this.exitFightView.visibleProperty().bind(session.getFightView().exitFightViewVisible);

        // bindings end-scene
        this.endSceneLabel.textProperty().bind(session.getEndSceneView().endSceneLabelProperty);
        this.endSceneLabel.styleProperty().bind(session.getEndSceneView().endSceneLabelStyleProperty);
        this.endScene.getChildren().get(0).styleProperty().bind(session.getEndSceneView().endSceneStyleProperty);
        this.score.textProperty().bind(session.getEndSceneView().scoreProperty);
        this.exitLabelEndView.textProperty().bind(session.getEndSceneView().exitLabelEndViewProperty);

        // bindings game-view
        this.timerTextLabel.styleProperty().bind(session.getPlayer().timerTextLabel);

        // initialize Level map
        this.session.createLevelObj(levelGrid);

        // initialize stones: search for stones in levelGrid and add to ArrayList, deal Card from deck, color stones
        this.session.getStoneCtrl().initializeStones(this.levelGrid);

        this.mainCanvas.setHeight(GameConfig.WINDOW_HEIGHT);
        this.mainCanvas.setWidth(GameConfig.WINDOW_WIDTH);

        // graphics context for displaying moving entities and changing texts in level
        this.gc = mainCanvas.getGraphicsContext2D();

        // initialize CollisionHandler
        this.session.createCollisionHndlrObj();

        // initialize Player's sprite
        this.session.getPlayer().createPlayerSprite(this.session.getCollisionHndlr());
        this.session.getPlayer().getPlayerSprite().setDuration(0.1);
        this.session.getPlayer().getPlayerSprite().setPosition(GameConfig.WINDOW_WIDTH/4 - this.session.getPlayer().getPlayerSprite().getFrame(0).getWidth()/2, 0);
        this.session.getPlayer().getPlayerSprite().setVelocity(0,100);


        // binding timerLabel to player's timer
        this.timerLabel.textProperty().bind(this.session.getPlayer().timerProperty.asString());
        this.timerLabel.styleProperty().bind(this.session.getPlayer().timerLabelStyle);

        // binding scoreLabel to player's score
        this.scoreLabel.textProperty().bind(this.session.getPlayer().score.asString());

        // add hearts to screen representing player's health points
        this.heartContainer.setSpacing(10.0);
        for(int i = 0; i < this.session.getPlayer().getHealthPoints(); i++) {
            ImageView imageView = new ImageView(new Image(this.getClass().getResourceAsStream("/com/ducky/duckythewizard/images/life-heart.png")));
            imageView.setFitHeight(GameConfig.LEVEL_CELL_HEIGHT - 10);
            imageView.setPreserveRatio(true);
            heartContainer.getChildren().add(imageView);
            heartContainer.setHgrow(imageView, Priority.NEVER);
        }
        // initialize end-scene local variables
        this.session.getEndSceneView().initLocalVariables(this.session.getRootAnchorPane(),
                this.session.getAnchorPaneEndOverlay(),
                this.session.getGameColorObject());

        // main game loop
        this.session.setAnimationTimer(new MyAnimationTimer(this.session, input));
        this.session.getAnimationTimer().start();

        // add listener
        this.addCollisionListener();
    }

    @FXML
    public void handleOnKeyPressed(KeyEvent keyEvent) {
        if (!this.session.getInFight() && !this.session.getGameOver()) {
            String code = keyEvent.getCode().toString();
            if(code.equals("SPACE")){
                this.session.toggleIsRunning();
                if(this.session.getIsRunning()){
                    // end pause-view
                    this.session.getAnimationTimer().resetStartingTime();
                    this.session.getAnimationTimer().start();
                    this.rootBox.requestFocus();
                    this.session.getEndSceneView().endPause();
                }
                else {
                    // start pause-view
                    this.session.getAnimationTimer().stop();
                    this.startPauseScene();
                    this.addEventEndPauseScene();
                }
            }
            else if ( session.getIsRunning() && !input.contains(code) )
                this.input.add( code );
        }
    }

    @FXML
    public void handleOnKeyReleased(KeyEvent keyEvent) {
        String code = keyEvent.getCode().toString();
        this.input.remove( code );
    }

    public void startCollision(Stone collisionStone) {
        this.session.getFightCtrl().startFight(collisionStone);
    }

    public void endCollision() {
        this.session.getFightCtrl().stopFight();
    }

    public void renderEndScene(boolean playerWin) {
        /*Add event-handler to minimize end-scene. Set game-over true. Show end-scene*/
        this.session.setGameOver(true);
        this.session.getEndSceneCtrl().addMinEventHandler();
        this.session.getEndSceneView().showEndScene(playerWin, this.session.getPlayer().getScore());
    }

    public void restartGame(MouseEvent event) throws IOException {
        this.session.getMenuCtrl().startGame(event);
    }

    public void backToMenu(Event event) throws IOException {
        this.session.getMenuCtrl().backToMenu(event);
    }

    public void startPauseScene() {
        this.session.getEndSceneView().createPauseMenu(
        );
    }

    public void addEventEndPauseScene() {
        this.session.getEndSceneView().setExitEvent(mouseEvent -> {
            if (this.session.getEndSceneView().getExitEvent() != null) {
                this.session.getEndSceneView().getExitLabel().removeEventHandler(MouseEvent.MOUSE_CLICKED, this.session.getEndSceneView().getExitEvent());
            }
            this.session.getEndSceneView().endPause();
            this.rootBox.requestFocus();
            this.session.setRunning(true);
            this.session.getAnimationTimer().resetStartingTime();
            this.session.getAnimationTimer().start();
        });
        this.session.getEndSceneView().getExitLabel().addEventFilter(MouseEvent.MOUSE_CLICKED, this.session.getEndSceneView().getExitEvent());
    }

    public void addCollisionListener() {
        this.collisionDetected.bind(this.session.getCollisionHndlr().stoneHit);

        this.collisionDetected.addListener((observableValue, aBoolean, t1) -> {
            if (collisionDetected.getValue()) {
                startCollision(session.getCollisionHndlr().getCurrentCollidedStone());
            }
        });
    }

    public void checkPlayerHealth() {
        // lose-scene
        if(this.session.getPlayer().getHealthPoints() == 0){
            this.session.toggleIsRunning();
            renderEndScene(false);
        }

        // remove heart if player lost a health point
        if(this.session.getPlayer().getHealthPoints() < this.heartContainer.getChildren().size()){
            this.heartContainer.getChildren().remove(this.heartContainer.getChildren().size() - 1);
        }
    }

    public void clearGC() {
        this.gc.clearRect(0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
    }

    public void drawImageOnGC(double t) {
        this.gc.drawImage(this.session.getPlayer().getPlayerSprite().getFrame(t), this.session.getPlayer().getPlayerSprite().getPositionX(), this.session.getPlayer().getPlayerSprite().getPositionY());
    }

    public String checkEndOfGame(boolean playerWin) {
        /*After card was clicked, check if player wins or looses the game. Whether player's hand-cards are empty.
        Or card-deck is empty. Or both.*/
        int cardsLeft = this.session.getCardDeckModel().getCardDeck().size();
        if (playerWin && cardsLeft <= 2) {
            return "win";
        } else if (!playerWin) {
            this.session.getPlayer().decrementHandCardsCount();
            if (cardsLeft >= 2) {
                // card deck is not empty, check if player has hand cards
                return this.session.getPlayer().checkAvailableCards() ? "no" : "loss";
            } else {
                // card deck is now empty, if player played last card, but lost -> loss
                return this.session.getPlayer().checkAvailableCards() ? "win" : "loss";
            }
        }
        return "no";
    }
}