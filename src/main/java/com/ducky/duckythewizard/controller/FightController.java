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
            this.getSession().toggleInFight();
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

    public void stopFight(MyAnimationTimer animationTimer){
        //System.out.println("----> stopFight");
        if(!this.getSession().getIsRunning()) {
            this.getSession().getAnimationTimer().resetStartingTime();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    getSession().getPlayer().resetPlayerTimer();
                    getSession().getFightView().endFightScene();
                    // get focus back
                    getSession().getRootAnchorPane().requestFocus();
                }
            });
            this.getSession().toggleIsRunning();
            this.getSession().toggleInFight();
            stone.setInactive();
        }
    }
}
