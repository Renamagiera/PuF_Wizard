package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.IOException;
import java.util.ArrayList;

import com.ducky.duckythewizard.controller.CardController;
import com.ducky.duckythewizard.controller.CollisionHandler;

public class GameController{

    public Game session = new Game();

    @FXML
    public AnchorPane emptyCardSlots;

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
    private Canvas mainCanvas;
    @FXML
    private AnchorPane rootBox;
    @FXML
    private GridPane levelGrid;

    private int windowWidth = this.session.getGameConfig().getWindowWidth();
    private int windowHeight = this.session.getGameConfig().getWindowHeight();

    private double cellWidth = this.session.getGameConfig().getLevel_cellWidth();
    private double cellHeight = this.session.getGameConfig().getLevel_cellHeight();

    private ArrayList<Rectangle2D> earthTiles = new ArrayList<>();

    private ArrayList<String> input = new ArrayList<>();

    private CollisionHandler collisionHandler;
    private DuckySprite ducky;
    private GraphicsContext gc;
    private MyAnimationTimer animationTimer;

    @FXML
    public void initialize() {
        //zum Start des Games werden die Controller erstellt und erhalten in ihren Konstruktoren einen Verweis auf das Game-Objekt
        this.session.createCardCtrlObj();
        this.session.createMovementCtrlObj();
        //this.session.createLevelCtrlObj(); //Level-Auslagerungsversuch
        //weitere Controller sollten hier dann folgen

        cardStuff();

        // initialize game.objectGrid
        Level level = new Level(levelGrid);
        game.objectGrid = level.getGameObjectGrid();

        mainCanvas.setHeight(bgHeight);
        mainCanvas.setWidth(bgWidth);

        // graphics context for displaying moving entities and changing texts in level
        gc = mainCanvas.getGraphicsContext2D();

        // initialize CollisionHandler
        collisionHandler = new CollisionHandler(game.objectGrid, cellHeight, cellWidth);

        // initialize font for texts that are shown
        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 50 );
        gc.setFont( theFont );
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE );
        gc.setLineWidth(5);

        // initialize Ducky
        ducky = new DuckySprite(5, collisionHandler);
        ducky.duration = 0.1;
        ducky.setPosition(windowWidth /2 - ducky.getFrame(0).getWidth()/2, 0);
        ducky.setVelocity(0,100);

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
               ducky.resetHealthPoints();
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

    publ
    }

    public void cardStuff() {
        // TO-DO-RENATE: passt das hierher oder wo muss das alternativ hin?
        GameConfig.anchorPane = emptyCardSlots;
        GameConfig.deckObject.addAndRenderALlCards(GameConfig.anchorPane);
    }

    // TO-DO-RENATE: Card-click action
    public void cardClicked(MouseEvent event) {
        this.session.getCardCtrl().cardClicked(event);
    }

    class MyAnimationTimer extends AnimationTimer
    {
        long startNanoTime = System.nanoTime();
        LongValue lastNanoTime = new LongValue( System.nanoTime() );
        public void handle(long currentNanoTime)
        {

            double t = (currentNanoTime - startNanoTime) / 1000000000.0;
            // calculate time since last update.
            double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
            lastNanoTime.value = currentNanoTime;

            if (input.contains("UP")) {
                ducky.setVelocityY(-100);   // moving UP
            }
            else {
                ducky.setVelocityY(100);    // falling DOWN
            }
            if (input.contains("LEFT")) {
                ducky.setVelocityX(-100);   // moving LEFT
            }
            else if (input.contains("RIGHT")) {
                ducky.setVelocityX(100);    // moving RIGHT
            }
            else {
                ducky.setVelocityX(0);      // not moving left or right
            }

            // bouncing back from left level boundary
            if(ducky.getPositionX() <= 0 ){
                ducky.setVelocity(100,0);
                System.out.println("LEFT");
            }
            // bouncing back from right level boundary
            if(ducky.getPositionX() >= windowWidth - ducky.getFrame(t).getWidth()){
                ducky.setVelocity(-100,0);
                System.out.println("RIGHT");
            }
            // bouncing back from upper level boundary
            if(ducky.getPositionY() <= 0){
                ducky.setVelocity(0,100);
                System.out.println("UPPER");
            }

            ducky.update(elapsedTime);
            ducky.reduceHealthPoints(); // TODO should be reduced automatically by timer/counter?

            // clear prior ducky image
            gc.clearRect(0,0,windowWidth,windowHeight);

            // showing Health text
            String healthText = "Health: " + ducky.getHealthPoints();
            gc.fillText( healthText, 10, 50);
            gc.strokeText( healthText, 10, 50 );

            // showing 'You lose' text
            if(ducky.getHealthPoints() == 0) {
                String pointsText = "You lose";
                gc.fillText( pointsText, windowWidth/2, windowHeight/3 );
                gc.strokeText( pointsText, windowWidth/2, windowHeight/3 );
            }

            // drawing Ducky frame on Ducky's position
            gc.drawImage( ducky.getFrame(t), ducky.getPositionX(), ducky.getPositionY() );
        }

        public void resetStartingTime(){
            startNanoTime = System.nanoTime();
            lastNanoTime = new LongValue( System.nanoTime() );
        }
    }
}