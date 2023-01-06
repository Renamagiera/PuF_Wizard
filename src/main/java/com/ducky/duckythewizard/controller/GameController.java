package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.*;
import javafx.animation.AnimationTimer;
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

    private int windowWidth = this.session.getGameConfig().getWindowWidth();
    private int windowHeight = this.session.getGameConfig().getWindowHeight();
    private double cellWidth = this.session.getGameConfig().getLevel_cellWidth();
    private double cellHeight = this.session.getGameConfig().getLevel_cellHeight();
    private ArrayList<String> input = new ArrayList<>();
    private CollisionHandler collisionHandler;
    private DuckySprite ducky;
    private MyAnimationTimer animationTimer;

    @FXML
    public void initialize() {
        //System.out.println("*** Game Controller is initialized...");

        // skin
        this.session.setSkin(SceneController.getSkin());

        this.session.setRootAnchorPane(this.rootBox);
        this.session.setAnchorPaneCards(this.emptyCardSlots);
        this.session.setFightOverlay(this.fightOverlay);
        this.session.setAnchorPaneEndOverlay(this.endScene);
        this.session.getFightView().setAnchorPaneFightOverlay(this.fightOverlay);

        // fight- and end-overlay not visible
        this.fightOverlay.setVisible(false);
        this.endScene.setVisible(false);

        //zum Start des Games werden die Controller erstellt und erhalten in ihren Konstruktoren einen Verweis auf das Game-Objekt
        this.session.createGameCtrlObj(this);
        this.session.createCardCtrlObj();
        this.session.createMovementCtrlObj();
        this.session.createStoneCtrlObj();
        this.session.createFightCtrlObj();
        this.session.createSceneCtrlObj();

        // initialize cards: set card-anchor-pane, render hand-cards
        this.session.getCardCtrl().cardInit();

        // bindings fight-view
        cards.textProperty().bind(session.getCardDeck().cardsProperty);
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

        // initialize Level map
        Level level = new Level(levelGrid);
        session.objectGrid = level.getGameObjectGrid();

        // initialize stones: search for stones in levelGrid and add to ArrayList, deal Card from deck, color stones
        this.session.getStoneCtrl().initializeStones(this.levelGrid);

        mainCanvas.setHeight(windowHeight);
        mainCanvas.setWidth(windowWidth);

        // graphics context for displaying moving entities and changing texts in level
        gc = mainCanvas.getGraphicsContext2D();

        // initialize CollisionHandler
        collisionHandler = new CollisionHandler(this, session.objectGrid, cellHeight, cellWidth);

        // initialize font for texts that are shown
        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 50 );
        gc.setFont( theFont );
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE );
        gc.setLineWidth(5);

        // initialize Ducky
        ducky = new DuckySprite(3, collisionHandler, this.session.getSkin());
        ducky.duration = 0.1;
        ducky.setPosition(windowWidth /4 - ducky.getFrame(0).getWidth()/2, 0);
        ducky.setVelocity(0,100);

        // binding timerLabel to Ducky's timer
        timerLabel.textProperty().bind(ducky.timerProperty);

        // binding scoreLabel to Ducky's score
        scoreLabel.textProperty().bind(ducky.score.asString());

        // add hearts to screen representing Ducky's health points
        heartContainer.setSpacing(10.0);
        for(int i = 0; i < ducky.getHealthPoints(); i++) {
            ImageView imageView = new ImageView(new Image(this.getClass().getResourceAsStream("/com/ducky/duckythewizard/images/golden-heart_50px.png")));
            imageView.setFitHeight(cellHeight - 10);
            imageView.setPreserveRatio(true);
            heartContainer.getChildren().add(imageView);
            heartContainer.setHgrow(imageView, Priority.NEVER);
        }

        // main game loop
        animationTimer = new MyAnimationTimer();
        animationTimer.start();
    }

    @FXML
    public void handleOnKeyPressed(KeyEvent keyEvent) {
        if (!this.session.getInFight()) {
            String code = keyEvent.getCode().toString();
            if(code.equals("SPACE")){
                session.toggleIsRunning();
                if(session.getIsRunning()){
                    animationTimer.resetStartingTime();
                    //ducky.resetPlayerTimer();
                    animationTimer.start();
                    this.endPauseScene();
                }
                else {
                    animationTimer.stop();
                    this.startPauseScene();
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
        this.session.getFightCtrl().stopFight(animationTimer, ducky);
    }

    public void renderEndScene(String overlayHeadline) {
        this.session.getEndSceneView().renderEndScene(
                this.session.getAnchorPaneEndOverlay(),
                overlayHeadline,
                this.session.getGameColorObject(),
                this.ducky.getScore(),
                this);
    }

    public void restartGame(MouseEvent event) throws IOException {
        this.session.getSceneCtrl().startGame(event);
    }

    public void backToMenu(Event event) throws IOException {
        this.session.getSceneCtrl().backToMenu(event);
    }

    public void startPauseScene() {
        this.session.getEndSceneView().renderEndScene(
                this.session.getAnchorPaneEndOverlay(),
                "pause",
                this.session.getGameColorObject(),
                this.ducky.getScore(),
                this);
    }

    public void endPauseScene() {
        this.session.getEndSceneView().endScene();
        this.rootBox.requestFocus();
    }

    public void resumeGame() {
        this.rootBox.requestFocus();
        session.toggleIsRunning();
        animationTimer.resetStartingTime();
        animationTimer.start();
    }

    class MyAnimationTimer extends AnimationTimer
    {
        long startNanoTime = System.nanoTime();
        LongValue lastNanoTime = new LongValue( System.nanoTime() );
        public void handle(long currentNanoTime)
        {
            if(session.getIsRunning())
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                if (input.contains("UP") || input.contains("W")) {
                    ducky.setVelocityY(-100);   // moving UP
                } else {
                    ducky.setVelocityY(100);    // falling DOWN
                }
                if (input.contains("LEFT") || input.contains("A")) {
                    ducky.setVelocityX(-100);   // moving LEFT
                } else if (input.contains("RIGHT") || input.contains("D")) {
                    ducky.setVelocityX(100);    // moving RIGHT
                } else {
                    ducky.setVelocityX(0);      // not moving left or right
                }

                // bouncing back from left level boundary
                if (ducky.getPositionX() <= 0) {
                    ducky.setVelocity(100, 0);
                    System.out.println("LEFT");
                }
                // bouncing back from right level boundary
                if (ducky.getPositionX() >= windowWidth - ducky.getFrame(t).getWidth()) {
                    ducky.setVelocity(-100, 0);
                    System.out.println("RIGHT");
                }
                // bouncing back from upper level boundary
                if (ducky.getPositionY() <= 0) {
                    ducky.setVelocity(0, 100);
                    System.out.println("UPPER");
                }

                ducky.update(elapsedTime);

                // clear prior ducky image
                gc.clearRect(0, 0, windowWidth, windowHeight);

                // showing 'You lose' text
                // lose-scene
                if(ducky.getHealthPoints() == 0){
                    /*String pointsText = "You LOSE\nyour score is " + ducky.getScore();
                    gc.fillText(pointsText, windowWidth / 3, windowHeight / 3);
                    gc.strokeText(pointsText, windowWidth / 3, windowHeight / 3);*/

                    //ServerFacade serverFacade = new ServerFacade();
                    //serverFacade.sendHighScoreToServer("Ducky-Test-19", ducky.getScore());

                    session.toggleIsRunning();
                    renderEndScene("duckyLoss");
                }

                // remove heart if Ducky lost a health point
                // TODO find solution that uses binding???
                if(ducky.getHealthPoints() < heartContainer.getChildren().size()){
                    heartContainer.getChildren().remove(heartContainer.getChildren().size() - 1);
                }

                // drawing Ducky frame on Ducky's position
                gc.drawImage(ducky.getFrame(t), ducky.getPositionX(), ducky.getPositionY());
            }
        }

        public void resetStartingTime(){
            startNanoTime = System.nanoTime();
            lastNanoTime = new LongValue( System.nanoTime() );
        }
    }
}