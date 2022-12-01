package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.*;
import com.ducky.duckythewizard.model.config.GameConfig;
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

public class GameController {
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

    private int bgWidth = 800;
    private int bgHeight = 650;
    private double cellWidth = 50.0;
    private double cellHeight = 50.0;
    private ArrayList<Rectangle2D> earthTiles = new ArrayList<>();

    private ArrayList<String> input = new ArrayList<>();
    private Game game = new Game();
    private CollisionHandler collisionHandler;
    private DuckySprite ducky;
    private GraphicsContext gc;
    private MyAnimationTimer animationTimer;

    @FXML
    public void initialize() {
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
        ducky.setPosition(bgWidth/2 - ducky.getFrame(0).getWidth()/2, 0);
        ducky.setVelocity(0,100);

        // main game loop
        animationTimer = new MyAnimationTimer();
        animationTimer.start();
    }

    @FXML
    public void handleOnKeyPressed(KeyEvent keyEvent) throws IOException {
        String code = keyEvent.getCode().toString();
        if(code.equals("SPACE")){
           game.toggleIsRunning();
           if(game.getIsRunning()){
               animationTimer.resetStartingTime();
               ducky.resetHealthPoints();
               animationTimer.start();
           }
           else {
               animationTimer.stop();
               String pauseText = "PAUSE";
               gc.fillText( pauseText, bgWidth/2 - 50, bgHeight/4 );
               gc.strokeText( pauseText, bgWidth/2 - 50, bgHeight/4 );
           }
        }
        else if ( game.getIsRunning() && !input.contains(code) )
            input.add( code );
    }

    @FXML
    public void handleOnKeyReleased(KeyEvent keyEvent) {
        String code = keyEvent.getCode().toString();
        input.remove( code );
    }

    public void cardStuff() {
        // TO-DO-RENATE: passt das hierher oder wo muss das alternativ hin?
        GameConfig.anchorPaneCards = emptyCardSlots;
        GameConfig.deckObject.addAndRenderALlCards(GameConfig.anchorPaneCards);
    }

    public void cardClicked(MouseEvent event) {
        int handCardPosition = GameConfig.CARD_SLOT_POSITION.get(((ImageView)event.getSource()).getId());
        Card clickedCard = GameConfig.handedCards.get(handCardPosition);
        if (clickedCard.color().getName().equals("none")) {
            GameConfig.handedCards.remove(handCardPosition);
            GameConfig.handedCards.add(handCardPosition, GameConfig.deckObject.dealNewCardFromDeck());
            GameConfig.deckObject.renderHandCardImages(GameConfig.anchorPaneCards);
        } else {
            GameConfig.deckObject.removeCardAddDummy(GameConfig.CARD_SLOT_POSITION.get(((ImageView)event.getSource()).getId()));
        }
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
            if(ducky.getPositionX() >= bgWidth - ducky.getFrame(t).getWidth()){
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
            gc.clearRect(0,0,bgWidth,bgHeight);

            // showing Health text
            String healthText = "Health: " + ducky.getHealthPoints();
            gc.fillText( healthText, 10, 50);
            gc.strokeText( healthText, 10, 50 );

            // showing 'You lose' text
            if(ducky.getHealthPoints() == 0) {
                String pointsText = "You lose";
                gc.fillText( pointsText, bgWidth/2, bgHeight/3 );
                gc.strokeText( pointsText, bgWidth/2, bgHeight/3 );
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