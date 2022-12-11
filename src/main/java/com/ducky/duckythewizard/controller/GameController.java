package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.*;
import com.ducky.duckythewizard.view.FightScene;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GameController{

    public Game session = new Game();

    @FXML
    private AnchorPane rootBox;
    @FXML
    private Canvas mainCanvas;
    @FXML
    private GridPane levelGrid;
    @FXML
    public AnchorPane emptyCardSlots;
    private GraphicsContext gc;

    @FXML
    private ImageView rock1;
    @FXML
    private ImageView rock2;
    @FXML
    private ImageView rock3;
    @FXML
    private ImageView rock4;
    @FXML
    private ImageView rock5;
    @FXML
    private Label timerLabel;
    @FXML
    private HBox heartContainer;


    private int windowWidth = this.session.getGameConfig().getWindowWidth();
    private int windowHeight = this.session.getGameConfig().getWindowHeight();

    private double cellWidth = this.session.getGameConfig().getLevel_cellWidth();
    private double cellHeight = this.session.getGameConfig().getLevel_cellHeight();

    private ArrayList<String> input = new ArrayList<>();

    private CollisionHandler collisionHandler;
    private FightController fightController;
    private DuckySprite ducky;
    private MyAnimationTimer animationTimer;

    @FXML
    public void initialize() {
        //System.out.println("*** Game Controller is initialized...");

        this.session.setRootAnchorPane(this.rootBox);

        //zum Start des Games werden die Controller erstellt und erhalten in ihren Konstruktoren einen Verweis auf das Game-Objekt
        this.session.createCardCtrlObj();
        this.session.createMovementCtrlObj();
        //weitere Controller sollten hier dann folgen

        // get card-anchor-pane, render hand-cards
        cards();

        // initialize Level map
        Level level = new Level(levelGrid);
        session.objectGrid = level.getGameObjectGrid();

        // search for stones in levelGrid and add to ArrayList, deal Card from deck, color stones
        stones();

        mainCanvas.setHeight(windowHeight);
        mainCanvas.setWidth(windowWidth);

        // graphics context for displaying moving entities and changing texts in level
        gc = mainCanvas.getGraphicsContext2D();

        // initialize CollisionHandler
        collisionHandler = new CollisionHandler(this, session.objectGrid, cellHeight, cellWidth);

        //initialize FightController
        fightController = new FightController(this, session.getCardCtrl());

        // initialize font for texts that are shown
        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 50 );
        gc.setFont( theFont );
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE );
        gc.setLineWidth(5);

        // initialize Ducky
        ducky = new DuckySprite(3, collisionHandler);
        ducky.duration = 0.1;
        ducky.setPosition(windowWidth /4 - ducky.getFrame(0).getWidth()/2, 0);
        ducky.setVelocity(0,100);

        // binding timerLabel to ducky's timer
        timerLabel.textProperty().bind(ducky.timerProperty);

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
    public void handleOnKeyPressed(KeyEvent keyEvent) throws IOException {
        String code = keyEvent.getCode().toString();
        if(code.equals("SPACE")){
            session.toggleIsRunning();
            if(session.getIsRunning()){
                animationTimer.resetStartingTime();
                ducky.resetPlayerTimer();
                animationTimer.start();
            }
            else {
                animationTimer.stop();
                String pauseText = "PAUSE";
                gc.fillText( pauseText, windowWidth/2 - 50, windowHeight/4 );
                gc.strokeText( pauseText, windowWidth/2 - 50, windowHeight/4 );
            }
        }
        else if ( session.getIsRunning() && !input.contains(code) )
            input.add( code );
    }

    @FXML
    public void handleOnKeyReleased(KeyEvent keyEvent) {
        String code = keyEvent.getCode().toString();
        input.remove( code );
    }

    public void cards() {
        this.session.setAnchorPaneCards(emptyCardSlots);
        this.session.getCardDeck().renderAllHandCardImages(this.session.getPlayer().getHandCards(), this.session.getAnchorPaneCards());
    }

    // TODO stone-color-view-class
    public void stones() {
        addStonesToArrayList();
        this.session.getCardCtrl().addCardToStones();
        // color the stones
        for (int i = 0; i < levelGrid.getChildren().size(); i++) {
            for (int x = 1; x <= session.getStoneArrayList().size(); x++) {
                if (levelGrid.getChildren().get(i).getId()!=null && levelGrid.getChildren().get(i).getId().equals("rock" + x)) {
                    ImageView imgView = (ImageView) levelGrid.getChildren().get(i);
                    String stoneColor = session.getStoneArrayList().get(x - 1).getCard().color().getHexCode();
                    Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/ducky/duckythewizard/images/forest/redMask.png")));
                    imgView.setImage(img);
                }
            }
        }
    }


    public void addStonesToArrayList() {
        for (int i = 0; i < session.objectGrid.length; i++) {
            for (int y = 0; y < session.objectGrid[i].length; y++) {
                if (session.objectGrid[i][y] instanceof Stone) {
                    session.stoneArrayList.add((Stone)session.objectGrid[i][y]);
                }
            }
        }
    }

    public void startFight(Stone stone) {
        System.out.println("--> startFight, before IF");
        if(session.getIsRunning()) {
            System.out.println("--> startFight, inside IF");
            System.out.println("FIGHT");
            //animationTimer.stop();
            session.toggleIsRunning();
            // start new fight, set fight scene visibility true
            FightScene fightScene = this.session.getFightScene();
            fightScene.renderFightScene(this.session.getRootAnchorPane());
            // starting fight in new thread
            Thread one = new Thread() {
                public void run() {
                    try {
                        fightController.startFight(stone, session.getPlayer(), fightScene);
                        //stopThread();
                    } catch (Exception v) {
                        System.out.println(v);
                    }
                }
            };
            one.start();
        }
    }

    public void stopThread() {
        animationTimer.resetStartingTime();
        Platform.runLater(new Runnable() {
            @Override public void run() {
                ducky.resetPlayerTimer();
            }
        });
        //animationTimer.start();
        session.toggleIsRunning();
        System.out.println("Thread stopped, thread ID: " + Thread.currentThread().getName());
    }

    public void stopFight(FightScene fightScene){
        System.out.println("----> stopFight");
        if(!session.getIsRunning()) {
            animationTimer.resetStartingTime();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ducky.resetPlayerTimer();
                    fightScene.endFightScene();
                }
            });
            session.toggleIsRunning();
        }
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

                if (input.contains("UP")) {
                    ducky.setVelocityY(-100);   // moving UP
                } else {
                    ducky.setVelocityY(100);    // falling DOWN
                }
                if (input.contains("LEFT")) {
                    ducky.setVelocityX(-100);   // moving LEFT
                } else if (input.contains("RIGHT")) {
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
                if(ducky.getHealthPoints() == 0){
                    String pointsText = "You LOSE";
                    gc.fillText(pointsText, windowWidth / 3, windowHeight / 3);
                    gc.strokeText(pointsText, windowWidth / 3, windowHeight / 3);
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