package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.*;
import javafx.application.Platform;

public class FightController extends Controller {
    private Stone stone;
    private Thread fightThread;
    public FightController(Game game) {
        super(game);
    }

    public Thread getFightThread() {
        return this.fightThread;
    }

    public void startFight(Stone stone) {
        if(this.getSession().getIsRunning()) {
            this.getSession().toggleIsRunning();
            this.getSession().toggleInFight();
            this.stone = stone;
            // start new fight-object
            this.getSession().setActiveFight(new Fight());
            // set stone-card and stone object to fight-object
            this.getSession().getActiveFight().setStoneCard(stone.getCard());
            this.getSession().getActiveFight().setStoneInFight(stone);
            this.getSession().getActiveFight().setTrump(stone);

            this.getSession().getFightView().showFightScene(
                    this.getSession().getActiveFight()
            );

            // starting fight in new thread
            this.fightThread = new Thread(() -> {
                try {
                    this.getSession().getStoneCtrl().getChangeTrumpThread().interrupt();
                    setFightHandler();
                } catch (Exception v) {
                    System.out.println(v);
                }
            });
            this.fightThread.start();
        }
    }

    public void setFightHandler() {
        this.getSession().getCardCtrl().addMouseEventHandler();
    }

    public void stopFight(){
        if(!this.getSession().getIsRunning()) {
            this.getSession().getAnimationTimer().resetStartingTime();
            Platform.runLater(() -> {
                getSession().getPlayer().resetPlayerTimer();
            });
            this.getSession().getFightView().endFightScene();
            this.getSession().getRootAnchorPane().requestFocus();
            this.getSession().toggleIsRunning();
            this.getSession().toggleInFight();
            this.getSession().getCollisionHndlr().stoneHit.set(false);
            this.getSession().getStoneCtrl().setInactive(stone);
        }
    }
}
