package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.controller.CollisionHandler;

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

    public DuckySprite(int maxHealthPoints, CollisionHandler collisionHandler) {
        this.healthPoints = maxHealthPoints;
        this.state = MovementState.FLY;
        this.resetTime = System.nanoTime();
        this.maxHealthPoints = maxHealthPoints;
        this.collisionHandler = collisionHandler;
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
}
