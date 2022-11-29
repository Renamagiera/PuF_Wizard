package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

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

    // TODO should be in DuckySprite ???
    private Image[] imageArrayFly;
    private Image[] imageArrayIdle;
    private Image[] imageArrayWalk;

    public Game getSession() {

        return this.session;
    }

    @FXML
    public void initialize() {
        //zum Start des Games werden die Controller erstellt und erhalten in ihren Konstruktoren einen Verweis auf das Game-Objekt
        this.session.createCardCtrlObj();
        this.session.createMovementCtrlObj();
        //this.session.createLevelCtrlObj(); //Level-Auslagerungsversuch
        //weitere Controller sollten hier dann folgen

        cardStuff();

        //this.session.createLevel(); //Level-Auslagerungsversuch
        //this.levelGrid = this.session.getLevel().getLevelGrid(); //Level-Auslagerungsversuch//


        //session.getLevelCtrl().createObjectGrid(); //Level-Auslagerungsversuch
        session.objectGrid = new GameObject[levelGrid.getRowCount()][levelGrid.getColumnCount()];

        mainCanvas.setHeight(windowHeight);
        mainCanvas.setWidth(windowWidth);

        // graphics context for displaying moving entities and changing texts in level
        gc = mainCanvas.getGraphicsContext2D();

        // initialize session.objectGrid
        for(Node node: levelGrid.getChildren()) {
            int row = levelGrid.getRowIndex(node);
            int column = levelGrid.getColumnIndex(node);
            if(node.getStyleClass().contains("rock")){
                session.objectGrid[row][column] = new Stone();
            }
            else { // if (node.getStyleClass().contains("earth-tile")) // TODO add styleclass to earth tiles??
                session.objectGrid[row][column] = new GameObject(false);
            }
        }

        // initialize CollisionHandler
        collisionHandler = new CollisionHandler(session.objectGrid, cellHeight, cellWidth);
        //this.session.getLevelCtrl().createCollisionHandler(); //Level-Auslagerungsversuch

        // initialize font for texts that are shown
        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 50 );
        gc.setFont( theFont );
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE );
        gc.setLineWidth(5);

        // initialize Ducky
        ducky = new DuckySprite(5, collisionHandler);

        // loading and scaling images for Ducky
        // TODO should not be in GameController --> move to DuckySprite ???
        imageArrayFly = new Image[6];
        imageArrayIdle = new Image[4];
        imageArrayWalk = new Image[6];
        for (int i = 0; i < imageArrayFly.length; i++) {
            Image image = new Image(this.getClass().getResourceAsStream("/com/ducky/duckythewizard/images/ducky/fly/fly_" + i + ".png"));
            imageArrayFly[i] = scaleImage(image, 40 , 40, true);
//            imageArrayFly[i] = new Image(this.getClass().getResourceAsStream("images/ducky/fly/fly_" + i + ".png"));
        }
        for (int i = 0; i < imageArrayIdle.length; i++) {
            Image image = new Image( this.getClass().getResourceAsStream("/com/ducky/duckythewizard/images/ducky/idle/idle_" + i + ".png" ));
            imageArrayIdle[i] = scaleImage(image, 40, 40, true);
//            imageArrayIdle[i] = new Image( this.getClass().getResourceAsStream("images/ducky/idle/idle_" + i + ".png" ));
        }
        for (int i = 0; i < imageArrayWalk.length; i++) {
            Image image = new Image( this.getClass().getResourceAsStream("/com/ducky/duckythewizard/images/ducky/walk/walk_" + i + ".png" ));
            imageArrayWalk[i] = scaleImage(image, 40, 40, true);
//            imageArrayWalk[i] = new Image( this.getClass().getResourceAsStream("images/ducky/walk/walk_" + i + ".png" ));
        }

        ducky.frames = imageArrayFly;
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
//        if(code.equals("ESCAPE")) {
//            AnchorPane newRoot = FXMLLoader.load(getClass().getResource("menu-view.fxml"));
//            Stage primaryStage = (Stage) mainCanvas.getScene().getWindow();
//            primaryStage.getScene().setRoot(newRoot);
//            newRoot.requestFocus();
//        }
//        else {
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
//        }
    }

    @FXML
    public void handleOnKeyReleased(KeyEvent keyEvent) {
        String code = keyEvent.getCode().toString();
        input.remove( code );
    }

    public Image scaleImage(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        ImageView imageView = new ImageView(source);
        imageView.setPreserveRatio(preserveRatio);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        return imageView.snapshot(parameters, null);
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
            ducky.reduceHealthPoints();

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

            // setting DUcky images according to movement
            // TODO should not be in GameController -> move to DuckySprite???
            if (ducky.getVelocityY() == 0 && (input.contains("LEFT") || input.contains("RIGHT"))) {
                ducky.frames = imageArrayWalk;
            }
            else if (ducky.getVelocityX() == 0 && ducky.getVelocityY() == 0) {
                ducky.frames = imageArrayIdle;
            }
            else {
                ducky.frames = imageArrayFly;
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