package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.controller.CollisionHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class DuckySprite extends AnimatedSprite{
    public enum MovementState {
        IDLE,
        WALK,
        FLY
    }

    private int healthPoints;
    private MovementState state;
    private long resetTime;
    private int maxHealthPoints;
    private CollisionHandler collisionHandler;

    private Image[] imageArrayFly;
    private Image[] imageArrayIdle;
    private Image[] imageArrayWalk;

    public DuckySprite(int maxHealthPoints, CollisionHandler collisionHandler) {
        this.healthPoints = maxHealthPoints;
        this.state = MovementState.FLY;
        this.resetTime = System.nanoTime();
        this.maxHealthPoints = maxHealthPoints;
        this.collisionHandler = collisionHandler;
        initializeImageArrays();
        this.frames = imageArrayFly;
    }

    public DuckySprite(CollisionHandler collisionHandler){
        this(10, collisionHandler);
    }

    @Override
    public void update(double time)
    {
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
        if (velocityY == 0 && velocityX != 0) {
            frames = imageArrayWalk;
        }
        else if (velocityX == 0 && velocityY == 0) {
            frames = imageArrayIdle;
        }
        else {
            frames = imageArrayFly;
        }
    }

    public void setState(MovementState state) {
        this.state = state;
    }

    public void reduceHealthPoints() {
        int time = (int)((System.nanoTime() - this.resetTime) / 1000000000.0);
        this.healthPoints = maxHealthPoints - time <= 0 ? 0 : maxHealthPoints - time;
        //System.out.println("Time: " + time);
    }

    public void resetHealthPoints() {
        this.healthPoints = maxHealthPoints;
        this.resetTime = System.nanoTime();
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    private void initializeImageArrays() {
        imageArrayFly = new Image[6];
        imageArrayIdle = new Image[4];
        imageArrayWalk = new Image[6];
        for (int i = 0; i < imageArrayFly.length; i++) {
            Image image = new Image(this.getClass().getResourceAsStream("/com/ducky/duckythewizard/images/ducky/fly/fly_" + i + ".png"));
            imageArrayFly[i] = scaleImage(image, 40 , 40, true);
        }
        for (int i = 0; i < imageArrayIdle.length; i++) {
            Image image = new Image( this.getClass().getResourceAsStream("/com/ducky/duckythewizard/images/ducky/idle/idle_" + i + ".png" ));
            imageArrayIdle[i] = scaleImage(image, 40, 40, true);
        }
        for (int i = 0; i < imageArrayWalk.length; i++) {
            Image image = new Image( this.getClass().getResourceAsStream("/com/ducky/duckythewizard/images/ducky/walk/walk_" + i + ".png" ));
            imageArrayWalk[i] = scaleImage(image, 40, 40, true);
        }
    }

    private Image scaleImage(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        ImageView imageView = new ImageView(source);
        imageView.setPreserveRatio(preserveRatio);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        return imageView.snapshot(parameters, null);
    }
}
