package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.controller.scenes.MenuController;
import com.ducky.duckythewizard.model.*;
import com.ducky.duckythewizard.model.config.GameConfig;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.util.ArrayList;

public class GameController{

    public Game session = new Game();

    @FXML
    private AnchorPane rootBox;
    @FXML
    private AnchorPane fightOverlay;
    @FXML
    private Canvas mainCanvas;
    @FXML
    private GridPane levelGrid;
    @FXML
    public AnchorPane emptyCardSlots;
    private GraphicsContext gc;
    @FXML
    private ImageView stone0;
    @FXML
    private ImageView stone1;
    @FXML
    private ImageView stone2;
    @FXML
    private ImageView stone3;
    @FXML
    private ImageView stone4;
    @FXML
    private Label timerLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private HBox heartContainer;
    @FXML
    private Label cards;
    @FXML
    private Label trumpColorText;
    @FXML
    private Label cardChooseText;
    @FXML
    private ImageView stoneCard;
    @FXML
    private ImageView duckyCard;
    @FXML
    private Label winLossLabel;
    @FXML
    private AnchorPane endScene;
    @FXML
    private Label endSceneLabel;
    @FXML
    private Label score;
    @FXML
    private Label exitLabelEndView;
    private ArrayList<String> input = new ArrayList<>();
    private CollisionHandler collisionHandler;
    private AnimatedSprite ducky;

    @FXML
    public void initialize() {
        //System.out.println("*** Game Controller is initialized...");

        // skin
        //System.out.println(SceneController.getSprite());
        this.session.setSprite(MenuController.getSprite());

        this.session.setRootAnchorPane(this.rootBox);
        this.session.setFightOverlay(this.fightOverlay);
        this.session.setAnchorPaneEndOverlay(this.endScene);

        // initialize fight-view-scene local variables
        this.session.getFightView().initLocalVariables(this.session.getGameColorObject(), this.fightOverlay);

        // fight- and end-overlay not visible
        this.fightOverlay.setVisible(false);
        this.endScene.setVisible(false);

        //zum Start des Games werden die Controller erstellt und erhalten in ihren Konstruktoren einen Verweis auf das Game-Objekt
        this.session.createGameCtrlObj(this);
        this.session.createCardCtrlObj();
        this.session.createStoneCtrlObj();
        this.session.createFightCtrlObj();
        this.session.createMenuCtrlObj();
        this.session.createEndSceneCtrl();
        this.session.createFightSceneCtrl();

        // initialize cards: set card-anchor-pane, render hand-cards
        this.session.getCardCtrl().cardInit();

        // bindings fight-view
        cards.textProperty().bind(session.getCardDeckModel().cardsProperty);
        trumpColorText.textProperty().bind(session.getFightView().trumpColorTextProperty);
        trumpColorText.styleProperty().bind(session.getFightView().trumpColorTextStyleProperty);
        cardChooseText.textProperty().bind(session.getFightView().cardChooseTextProperty);
        stoneCard.imageProperty().bind(session.getFightView().stoneCardProperty.imageProperty());
        duckyCard.imageProperty().bind(session.getFightView().duckyCardProperty.imageProperty());
        winLossLabel.textProperty().bind(session.getFightView().winLossLabelProperty);
        winLossLabel.styleProperty().bind(session.getFightView().winLossLabelStyleProperty);
        fightOverlay.styleProperty().bind(session.getFightView().fightOverlayStyleProperty);

        // bindings end-scene
        endSceneLabel.textProperty().bind(session.getEndSceneView().endSceneLabelProperty);
        endSceneLabel.styleProperty().bind(session.getEndSceneView().endSceneLabelStyleProperty);
        endScene.getChildren().get(0).styleProperty().bind(session.getEndSceneView().endSceneStyleProperty);
        score.textProperty().bind(session.getEndSceneView().scoreProperty);
        exitLabelEndView.textProperty().bind(session.getEndSceneView().exitLabelEndViewProperty);

        // initialize Level map
        Level level = new Level(levelGrid);
        session.objectGrid = level.getGameObjectGrid();

        // initialize stones: search for stones in levelGrid and add to ArrayList, deal Card from deck, color stones
        this.session.getStoneCtrl().initializeStones(this.levelGrid);

        mainCanvas.setHeight(GameConfig.WINDOW_HEIGHT);
        mainCanvas.setWidth(GameConfig.WINDOW_WIDTH);

        // graphics context for displaying moving entities and changing texts in level
        gc = mainCanvas.getGraphicsContext2D();

        // initialize CollisionHandler
        collisionHandler = new CollisionHandler(this, session.objectGrid, GameConfig.LEVEL_CELL_HEIGHT, GameConfig.LEVEL_CELL_WIDTH);

        // initialize font for texts that are shown
        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 50 );
        gc.setFont( theFont );
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE );
        gc.setLineWidth(5);

        // initialize Player's sprite
        ducky = new AnimatedSprite(collisionHandler, this.session.getSpriteString(), this.session.getPlayer());
        this.session.getPlayer().setPlayerSprite(ducky);
        ducky.duration = 0.1;
        ducky.setPosition(GameConfig.WINDOW_WIDTH /4 - ducky.getFrame(0).getWidth()/2, 0);
        ducky.setVelocity(0,100);

        // binding timerLabel to Ducky's timer
        timerLabel.textProperty().bind(this.session.getPlayer().timerProperty); // LENA Player statt DUcky

        // binding scoreLabel to Ducky's score
        scoreLabel.textProperty().bind(this.session.getPlayer().score.asString()); // LENA Player statt DUcky

        // add hearts to screen representing Ducky's health points
        heartContainer.setSpacing(10.0); // PLAYER statt Ducky
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
    }

    @FXML
    public void handleOnKeyPressed(KeyEvent keyEvent) {
        if (!this.session.getInFight() && !this.session.getGameOver()) {
            String code = keyEvent.getCode().toString();
            if(code.equals("SPACE")){
                session.toggleIsRunning();
                if(session.getIsRunning()){
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
                input.add( code );
        }
    }

    @FXML
    public void handleOnKeyReleased(KeyEvent keyEvent) {
        String code = keyEvent.getCode().toString();
        input.remove( code );
    }

    public void startCollision(Stone collisionStone) {
        this.session.getFightCtrl().startFight(collisionStone);
    }

    public void endCollision() {
        this.session.getFightCtrl().stopFight(this.session.getAnimationTimer());
    }

    public void renderEndScene(boolean playerWin) {
        /*Add event-handler to minimize end-scene. Set game-over true. Show end-scene*/
        this.session.setGameOver(true);
        this.session.getEndSceneCtrl().addMinEventHandler();
        this.session.getEndSceneView().showEndScene(playerWin, this.session.getPlayer().getScore());
    }

    public void maximizeEndScene() {
        //this.session.getEndSceneCtrl().addMaxEventHandler();
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
        this.session.getEndSceneView().getExitLabel().setText("x");
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

    public void checkPlayerHealth() {
        // showing 'You lose' text
        // lose-scene
        if(this.session.getPlayer().getHealthPoints() == 0){
            session.toggleIsRunning();
            renderEndScene(false);
        }

        // remove heart if Ducky lost a health point
        if(session.getPlayer().getHealthPoints() < heartContainer.getChildren().size()){
            heartContainer.getChildren().remove(heartContainer.getChildren().size() - 1);
        }
    }

    public void clearGC() {
        gc.clearRect(0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
    }

    public void drawImageOnGC(double t) {
        gc.drawImage(ducky.getFrame(t), ducky.getPositionX(), ducky.getPositionY());
    }

    public String checkEndOfGame(boolean duckyWin) {
        /*After card was clicked, check if ducky wins or looses the game. Whether player's hand-cards are empty.
        Or card-deck is empty. Or both.*/
        int cardsLeft = this.session.getCardDeckModel().getCardDeck().size();
        if (duckyWin && cardsLeft <= 2) {
            return "win";
        } else if (!duckyWin) {
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