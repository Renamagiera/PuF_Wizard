package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.controller.CollisionHandler;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.Objects;

public class DuckySprite extends AnimatedSprite{
    public enum MovementState {
        IDLE,
        WALK,
        FLY
    }

    private String skin;
    private int healthPoints;
    public SimpleIntegerProperty score;
    private MovementState state;
    private long resetTime;
    private int maxHealthPoints;
    private int maxTime = 10;
    public SimpleStringProperty timerProperty;
    private CollisionHandler collisionHandler;

    private Image[] imageArrayFlyRight;
    private Image[] imageArrayFlyLeft;
    private Image[] imageArrayIdleLookRight;
    private Image[] imageArrayIdleLookLeft;
    private Image[] imageArrayWalkRight;
    private Image[] imageArrayWalkLeft;

    private boolean duckyLooksLeft = false;

    public DuckySprite(int maxHealthPoints, CollisionHandler collisionHandler, String skin) {
        this.skin = skin;
        this.healthPoints = maxHealthPoints;
        this.score = new SimpleIntegerProperty(0);
        this.state = MovementState.FLY;
        this.resetTime = System.nanoTime();
        this.maxHealthPoints = maxHealthPoints;
        this.timerProperty = new SimpleStringProperty(Integer.toString(maxTime));
        this.collisionHandler = collisionHandler;
        this.initializeImageArrays();
        this.frames = imageArrayFlyRight;
    }

    // TODO Britta brauchst du das?
    /*public DuckySprite(CollisionHandler collisionHandler){
        this(10, collisionHandler);
    }*/

    @Override
    public void update(double time)
    {
        reducePlayerTimer();
        // if collision --> revert position and adjust velocity, depending on direction in which collision occurred
        positionX += velocityX * time;
        if(collisionHandler.isCollision(this.getBoundary())){
            positionX -= velocityX * time;  // revert position
            velocityX = velocityX * (-1);   // invert velocity
        }
        positionY += velocityY * time;

        if(collisionHandler.isCollision(this.getBoundary())){
            positionY -= velocityY * time;  // revert position
            if (velocityY > 0){             // if Ducky was falling, stop falling
                velocityY = 0;
            }
            else {
                velocityY = 100;            // if Ducky was flying upwards, invert velocity
            }
        }

        // set animation frames according to movement
        // WALK
        if (velocityY == 0 && velocityX != 0) {
            if (velocityX > 0) {
                this.duckyLooksLeft = false;
                this.frames = imageArrayWalkRight;
            } else if (velocityX < 0) {
                this.duckyLooksLeft = true;
                this.frames = imageArrayWalkLeft;
            }
        }
        // IDLE
        else if (velocityX == 0 && velocityY == 0) {
            if (this.duckyLooksLeft) {
                this.frames = imageArrayIdleLookLeft;
            } else {
                this.frames = imageArrayIdleLookRight;
            }
        }
        // FLY
        else {
            if (velocityX > 0) {
                this.duckyLooksLeft = false;
                this.frames = imageArrayFlyRight;
            } else if (velocityX < 0){
                this.duckyLooksLeft = true;
                this.frames = imageArrayFlyLeft;
            } else {
                // ducky is just falling
                if (this.duckyLooksLeft) {
                    this.frames = imageArrayFlyLeft;
                } else {
                    this.frames = imageArrayFlyRight;
                }
            }
        }
    }

    public void setState(MovementState state) {
        this.state = state;
    }

    public void reducePlayerTimer() {
        int time = (int)((System.nanoTime() - this.resetTime) / 1000000000.0);
        this.timerProperty.set(Integer.toString(maxTime - time <= 0 ? 0 : maxTime - time));
        if(this.timerProperty.getValue().equals("0")) {
            reducePlayerLife();
        }
    }

    public void resetPlayerTimer() {
        this.timerProperty.set(Integer.toString(maxTime));
        this.resetTime = System.nanoTime();
    }

    private void reducePlayerLife(){
        this.healthPoints = this.healthPoints <= 0 ? 0 : this.healthPoints - 1;
        resetPlayerTimer();
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getScore() {
        return score.getValue();
    }

    public void addToScore (int points) {
        score.set(score.getValue() + points);
        System.out.println("==> adding " + points + " to score, score is now: " + score.getValue());
    }

    private void initializeImageArrays() {
        imageArrayFlyRight = new Image[6];
        imageArrayFlyLeft = new Image[6];

        imageArrayIdleLookRight = new Image[4];
        imageArrayIdleLookLeft = new Image[4];

        imageArrayWalkRight = new Image[6];
        imageArrayWalkLeft = new Image[6];

        // FLY
        for (int i = 0; i < imageArrayFlyRight.length; i++) {
            Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/com/ducky/duckythewizard/images/ducky/" + this.skin + "/fly/fly_" + i + ".png")));
            imageArrayFlyRight[i] = scaleImage(image, 40 , 40, true, false);
        }
        for (int i = 0; i < imageArrayFlyRight.length; i++) {
            imageArrayFlyLeft[i] = scaleImage(imageArrayFlyRight[i], 40, 40, true, true);
        }

        // IDLE
        for (int i = 0; i < imageArrayIdleLookRight.length; i++) {
            Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/com/ducky/duckythewizard/images/ducky/" + this.skin + "/idle/idle_" + i + ".png")));
            imageArrayIdleLookRight[i] = scaleImage(image, 40, 40, true, false);
        }
        for (int i = 0; i < imageArrayIdleLookRight.length; i++) {
            imageArrayIdleLookLeft[i] = scaleImage(imageArrayIdleLookRight[i], 40, 40, true, true);
        }

        // WALK
        for (int i = 0; i < imageArrayWalkRight.length; i++) {
            Image image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/com/ducky/duckythewizard/images/ducky/" + this.skin + "/walk/walk_" + i + ".png")));
            imageArrayWalkRight[i] = scaleImage(image, 40, 40, true, false);
        }
        for (int i = 0; i < imageArrayWalkRight.length; i++) {
            imageArrayWalkLeft[i] = scaleImage(imageArrayWalkRight[i], 40, 40, true, true);
        }
    }

    private Image scaleImage(Image source, int targetWidth, int targetHeight, boolean preserveRatio, boolean mirror) {
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        ImageView imageView = new ImageView(source);
        imageView.setPreserveRatio(preserveRatio);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        if (mirror) {
            imageView.setScaleX(-1);
        }
        return imageView.snapshot(parameters, null);
    }
}
