package com.ducky.duckythewizard.model;

import javafx.animation.AnimationTimer;
import java.util.ArrayList;

public class MyAnimationTimer extends AnimationTimer
{
    long startNanoTime = System.nanoTime();
    LongValue lastNanoTime = new LongValue( System.nanoTime() );
    Game session;
    ArrayList<String> input;

    public MyAnimationTimer(Game game, ArrayList<String> input) {
        this.session = game;
        this.input = input;
    }

    public void handle(long currentNanoTime)
    {
        if(this.session.getIsRunning())
        {
            double t = (currentNanoTime - this.startNanoTime) / 1000000000.0;
            // calculate time since last update.
            double elapsedTime = (currentNanoTime - this.lastNanoTime.value) / 1000000000.0;
            this.lastNanoTime.value = currentNanoTime;

            AnimatedSprite ducky = this.session.getPlayer().getPlayerSprite();
            ducky.translateKeyPressesIntoMovement(this.input);
            ducky.checkLevelBoundaryContact(t);
            ducky.update(elapsedTime);

            // clear prior ducky image
            this.session.getGameCtrl().clearGC();
            this.session.getGameCtrl().checkPlayerHealth();

            // drawing Ducky frame on Ducky's position
            this.session.getGameCtrl().drawImageOnGC(t);
        }
    }

    public void resetStartingTime(){
        this.startNanoTime = System.nanoTime();
        this.lastNanoTime = new LongValue( System.nanoTime() );
    }
}