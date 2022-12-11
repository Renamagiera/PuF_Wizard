package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Fight;
import com.ducky.duckythewizard.model.Player;
import com.ducky.duckythewizard.model.Stone;
import com.ducky.duckythewizard.view.FightScene;

public class FightController extends Controller {

    GameController myGameController;
    CardController cardController;
    public FightController(GameController gameController, CardController cardController){
        myGameController = gameController;
        this.cardController = cardController;
    }

    public void startFight(Stone stone, Player player, FightScene fightScene) {
        stone.setInactive();
        myGameController.session.setActiveFight(new Fight());
        // set enemy-card
        myGameController.session.getActiveFight().setEnemyCard(stone.getCard());
        this.cardController.addMouseEventHandler(myGameController, fightScene);
    }
}
