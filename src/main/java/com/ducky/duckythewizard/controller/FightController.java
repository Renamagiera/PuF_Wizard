package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.Card;
import com.ducky.duckythewizard.model.Fight;
import com.ducky.duckythewizard.model.Player;
import com.ducky.duckythewizard.model.Stone;
import com.ducky.duckythewizard.view.FightScene;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.event.MouseListener;

public class FightController extends Controller {

    GameController myGameController;
    CardController cardController;
    private Card playerCard;
    private Card enemyCard;
    public FightController(GameController gameController, CardController cardController){
        myGameController = gameController;
        this.cardController = cardController;
    }

    public void startFight(Stone stone, Player player, FightScene fightScene) { // TODO change DuckySprite for Player
        stone.setInactive();
        myGameController.session.setActiveFight(new Fight());
        // set enemy-card
        myGameController.session.getActiveFight().setEnemyCard(stone.getCard());

        for (Node node : myGameController.session.getAnchorPaneCards().getChildren()) {
            ImageView imgView = (ImageView) node;
            imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                myGameController.stopFight(fightScene);
            });
        }

        // exit button: close fight
        fightScene.getExitButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            myGameController.stopFight(fightScene);
        });
    }

    public Card getPlayerCard() {
        return playerCard;
    }

    public Card getEnemyCard() {
        return enemyCard;
    }
}
