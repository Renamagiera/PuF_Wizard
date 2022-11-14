package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.DuckySprite;
import com.ducky.duckythewizard.model.LongValue;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {
    @FXML
    private Group backgroundImageGroup;
    @FXML
    private Canvas mainCanvas;
    @FXML
    private AnchorPane rootBox;
    @FXML
    private GridPane levelGrid;

    private int bgWidth = 800;
    private int bgHeight = 650;

    private ArrayList<String> input = new ArrayList<>();

    @FXML
    public void initialize() {
        mainCanvas.setHeight(bgHeight);
        mainCanvas.setWidth(bgWidth);

        GraphicsContext gc = mainCanvas.getGraphicsContext2D();

        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        gc.setFont( theFont );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(1);

        DuckySprite ducky = new DuckySprite(5);
        Image[] imageArrayFly = new Image[6];
        Image[] imageArrayIdle = new Image[4];
        Image[] imageArrayWalk = new Image[6];
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
        ducky.setPosition(bgWidth/2 - ducky.getFrame(0).getWidth()/2, 0);
        ducky.setVelocity(0,100);

        final long startNanoTime = System.nanoTime();
        LongValue lastNanoTime = new LongValue( System.nanoTime() );

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                if (input.contains("UP")){
                    if(wouldIntersectWithLevel(ducky, 0, -1)){
                        ducky.setVelocityY(100);
                    }
                    else {
                        ducky.setVelocityY(-100);
                    }

                }
                else if (!wouldIntersectWithLevel(ducky, 0, 1)) {
                    ducky.setVelocity(0,100); // falling ducky
                }
                else {
                    ducky.setVelocityY(0);
                }
                if (input.contains("LEFT")) {
                    if (!wouldIntersectWithLevel(ducky, -1, 0)) {
                        ducky.setVelocityX(-100);
                    }
                }

                if (input.contains("RIGHT")) {
                    if(!wouldIntersectWithLevel(ducky, 1, 0)) {
                        ducky.setVelocityX(100);
                    }
                }


                // left level boundary
                if(ducky.getPositionX() <= 0 ){
                    ducky.setVelocity(100,0);
                    System.out.println("LEFT");
                }
                // right level boundary
                if(ducky.getPositionX() >= bgWidth - ducky.getFrame(t).getWidth()){
                    ducky.setVelocity(-100,0);
                    System.out.println("RIGHT");
                }
                // upper level boundary
                if(ducky.getPositionY() <= 0){
                    ducky.setVelocity(0,100);
                    System.out.println("UPPER");
                }

//                if( intersectsWithLevel(ducky)){//ducky.getBoundary().intersects(0, bgHeight, bgWidth, 100)){
////                    ducky.setVelocity(0, 0);
////                    ducky.resetHealthPoints();
//                    if(!(input.contains("UP") || input.contains("LEFT") || input.contains("RIGHT"))){
//                        ducky.setVelocity(0,0);
//                    }
//                    else {
//                        ducky.invertVelocity();
//                    }
//                }



                ducky.update(elapsedTime);
                ducky.reduceHealthPoints();

//                for(BackgroundImage image : rootBox.getBackground().getImages()){
//                    gc.drawImage(image.getImage(), 0, 0);
//                }
//                for(Node node : backgroundImageGroup.getChildren()){
//                    gc.drawImage(((ImageView)node).getImage(), 0, 0);
//                }

                // clear prior ducky image
                gc.clearRect(0,0,bgWidth,bgHeight);

                String healthText = "Health: " + ducky.getHealthPoints();
                gc.fillText( healthText, bgWidth/3, bgHeight/4 );
                gc.strokeText( healthText, bgWidth/3, bgHeight/4 );

                if(ducky.getHealthPoints() == 0) {
                    String pointsText = "You lose";
                    gc.fillText( pointsText, bgWidth/2, bgHeight/3 );
                    gc.strokeText( pointsText, bgWidth/2, bgHeight/3 );
                }




                if (ducky.getVelocityY() == 0 && (input.contains("LEFT") || input.contains("RIGHT"))) {
                    //System.out.println("WALK");
                    ducky.frames = imageArrayWalk;
                }
                else if (ducky.getVelocityX() == 0 && ducky.getVelocityY() == 0) {
                    ducky.frames = imageArrayIdle;
                }
                else {
                    ducky.frames = imageArrayFly;
                }

                gc.drawImage( ducky.getFrame(t), ducky.getPositionX(), ducky.getPositionY() );
            }
        }.start();
    }

    @FXML
    public void handleOnKeyPressed(KeyEvent keyEvent) throws IOException {
        String code = keyEvent.getCode().toString();
        if(code.equals("ESCAPE")) {
            AnchorPane newRoot = FXMLLoader.load(getClass().getResource("menu-view.fxml"));
            Stage primaryStage = (Stage) mainCanvas.getScene().getWindow();
            primaryStage.getScene().setRoot(newRoot);
            newRoot.requestFocus();
        }
        else {
            if ( !input.contains(code) )
                input.add( code );
        }

    }

    @FXML
    public void handleOnKeyReleased(KeyEvent keyEvent) {
        String code = keyEvent.getCode().toString();
        input.remove( code );
    }

    public boolean intersectsWithLevel(DuckySprite ducky) {
        for (Node node : levelGrid.getChildren()) {
            if (ducky.intersects(levelGrid.getRowIndex(node), levelGrid.getColumnIndex(node), 50.0, 50.0)){
                System.out.println("==> COLLISION");
                return true;
            }
        }
        return false;
    }

    public boolean wouldIntersectWithLevel(DuckySprite ducky, double x, double y) {
        double xBegin = ducky.getPositionX() + x;
        double duckyWidth = ducky.getFrame(0).getWidth();
        double yBegin = ducky.getPositionY() + y;
        double duckyHeight = ducky.getFrame(0).getHeight();
        //System.out.println("Ducky: x: " + xBegin + ", y: " + yBegin + ", width: " + duckyWidth + ", height: " + duckyHeight);
        Rectangle2D duckyBoundary = new Rectangle2D(xBegin,yBegin,duckyWidth,duckyHeight);
        for (Node node : levelGrid.getChildren()) {
           // System.out.println("node: x: " + levelGrid.getColumnIndex(node)*50.0 + ", y: " + levelGrid.getRowIndex(node)*50.0 );
            Rectangle2D imageBoundary = new Rectangle2D(levelGrid.getColumnIndex(node)*50.0, levelGrid.getRowIndex(node)*50.0, 50.0, 50.0);
            if (duckyBoundary.intersects(imageBoundary)){
                System.out.println(" ! COLLISION !");
                return true;
            }
        }
        return false;
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
}