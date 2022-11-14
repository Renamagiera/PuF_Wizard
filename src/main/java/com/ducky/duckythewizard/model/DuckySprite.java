package com.ducky.duckythewizard.model;

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

    public DuckySprite(int maxHealthPoints) {
        this.healthPoints = maxHealthPoints;
        this.state = MovementState.FLY;
        this.resetTime = System.nanoTime();
        this.maxHealthPoints = maxHealthPoints;
    }

    public DuckySprite(){
        this(10);
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
