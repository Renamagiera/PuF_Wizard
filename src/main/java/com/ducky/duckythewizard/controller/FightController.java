package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.*;
import javafx.application.Platform;

public class FightController extends Controller {
    private Stone stone;
    public FightController(Game game) {
        super(game);
    }

    public void startFight(Stone stone) {
        if(this.getSession().getIsRunning()) {
            this.getSession().toggleIsRunning();
            this.stone = stone;
            // start new fight-object
            this.getSession().setActiveFight(new Fight(this.getSession().getGameColorObject()));
            // set stone-card and stone object to fight-object
            this.getSession().getActiveFight().setStoneCard(stone.getCard());
            this.getSession().getActiveFight().setStoneInFight(stone);

            this.getSession().getFightView().renderFightScene(
                    this.getSession().getActiveFight(),
                    this.getSession().getGameColorObject());

            // starting fight in new thread
            Thread one = new Thread() {
                public void run() {
                    try {
                        setFightHandler();
                    } catch (Exception v) {
                        System.out.println(v);
                    }
                }
            };
            one.start();
        }
    }

    public void setFightHandler() {
        this.getSession().getCardCtrl().addMouseEventHandler();
    }

    public void stopFight(GameController.MyAnimationTimer animationTimer, DuckySprite ducky){
        System.out.println("----> stopFight");
        if(!this.getSession().getIsRunning()) {
            animationTimer.resetStartingTime();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ducky.resetPlayerTimer();
                    ducky.addToScore(100); // TODO points should depend on result of fight
                    getSession().getFightView().endFightScene();
                    // get focus back
                    getSession().getRootAnchorPane().requestFocus();
                }
            });
            this.getSession().toggleIsRunning();
            stone.setInactive();
        }
    }
}
