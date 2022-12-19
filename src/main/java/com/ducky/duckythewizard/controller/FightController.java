package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.*;
import javafx.application.Platform;

public class FightController extends Controller {
    public FightController(Game game){
        super(game);
    }

    public void startFight(Stone stone) {
        if(this.getSession().getIsRunning()) {
            this.getSession().toggleIsRunning();
            // start new fight-object
            this.getSession().setActiveFight(new Fight(this.getSession().getGameColorObject()));
            // set enemy-card and stone object to fight-object
            this.getSession().getActiveFight().setStoneCard(stone.getCard());
            this.getSession().getActiveFight().setStoneInFight(stone);

            System.out.println(this.getSession().getFightOverlay());

            this.getSession().getFightScene().renderFightScene(
                    this.getSession().getFightOverlay(),
                    this.getSession().getRootAnchorPane(),
                    this.getSession().getActiveFight());

            // starting fight in new thread
            Thread one = new Thread() {
                public void run() {
                    try {
                        setFightHandler(stone, getSession().getPlayer());
                        //stopThread();
                    } catch (Exception v) {
                        System.out.println(v);
                    }
                }
            };
            one.start();
        }
    }

    public void setFightHandler(Stone stone, Player player) {
        stone.setInactive();
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
                    getSession().getFightScene().endFightScene();
                }
            });
            this.getSession().toggleIsRunning();
        }
    }
}
