package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.*;
import com.ducky.duckythewizard.view.FightScene;
import javafx.application.Platform;

public class FightController extends Controller {
    GameController myGameController;
    public FightController(GameController gameController, Game game){
        super(game);
        myGameController = gameController;
    }

    public void setFightHandler(Stone stone, Player player, FightScene fightScene) {
        stone.setInactive();
        this.getSession().getCardCtrl().addMouseEventHandler(myGameController, fightScene);
    }

    public void startFight(Stone stone) {
        System.out.println("--> startFight, before IF");
        if(myGameController.session.getIsRunning()) {
            System.out.println("--> startFight, inside IF");
            System.out.println("FIGHT");
            //animationTimer.stop();
            myGameController.session.toggleIsRunning();
            // start new fight-scene, set fight scene visibility true
            FightScene fightScene = myGameController.session.getFightScene();
            // start new fight-object
            myGameController.session.setActiveFight(new Fight(myGameController.session.getGameColorObject()));
            // set enemy-card and stone object to fight-object
            myGameController.session.getActiveFight().setStoneCard(stone.getCard());
            myGameController.session.getActiveFight().setStoneInFight(stone);
            fightScene.renderFightScene(myGameController.session.getRootAnchorPane(), myGameController.session.getActiveFight().getStoneInFight().getRandomTrumpColor(), myGameController.session.getGameColorObject());
            // starting fight in new thread
            Thread one = new Thread() {
                public void run() {
                    try {
                        setFightHandler(stone, myGameController.session.getPlayer(), fightScene);
                        //stopThread();
                    } catch (Exception v) {
                        System.out.println(v);
                    }
                }
            };
            one.start();
        }
    }

    public void stopFight(FightScene fightScene, GameController.MyAnimationTimer animationTimer, DuckySprite ducky){
        System.out.println("----> stopFight");
        if(!myGameController.session.getIsRunning()) {
            animationTimer.resetStartingTime();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ducky.resetPlayerTimer();
                    fightScene.endFightScene();
                }
            });
            myGameController.session.toggleIsRunning();
        }
    }
}
