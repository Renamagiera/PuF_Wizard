package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.card.CardModel;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Player {
    private String name = "testname";
    private ArrayList<CardModel> handCards;
    public SimpleIntegerProperty score;
    private int healthPoints;
    private int maxHealthPoints = GameConfig.PLAYER_MAX_HEALTHPOINTS;
    private int playableCards;

    public SimpleStringProperty timerProperty;
    private long resetTime;

    private AnimatedSprite playerSprite;

    public Player() {
        this.healthPoints = maxHealthPoints;
        this.score = new SimpleIntegerProperty(0);
        this.resetTime = System.nanoTime();
        this.timerProperty = new SimpleStringProperty(Integer.toString(GameConfig.PLAYER_ACTION_TIMER));
        this.playableCards = GameConfig.AMOUNT_HAND_CARDS;
    }

    public void setHandCards(ArrayList<CardModel> handCards) {
        this.handCards = handCards;
    }

    public String getPlayerName() {
        return this.name;
    }
    public AnimatedSprite getPlayerSprite() {
        return this.playerSprite;
    }

    public void setPlayerSprite(AnimatedSprite sprite) {
        this.playerSprite = sprite;
    }
    public int getHealthPoints() {
        return this.healthPoints;
    }

    public int getScore() {
        return this.score.getValue();
    }

    public void addToScore (int points) {
        this.score.set(score.getValue() + points);
        System.out.println("==> adding " + points + " to score, score is now: " + score.getValue());
    }

    public void reducePlayerTimer() {
        int time = (int)((System.nanoTime() - this.resetTime) / 1000000000.0);
        this.timerProperty.set(Integer.toString(GameConfig.PLAYER_ACTION_TIMER - time <= 0 ? 0 : GameConfig.PLAYER_ACTION_TIMER - time));
        if(this.timerProperty.getValue().equals("0")) {
            reducePlayerLife();
        }
    }

    public void resetPlayerTimer() {
        this.timerProperty.set(Integer.toString(GameConfig.PLAYER_ACTION_TIMER));
        this.resetTime = System.nanoTime();
    }

    private void reducePlayerLife(){
        this.healthPoints = this.healthPoints <= 0 ? 0 : this.healthPoints - 1;
        resetPlayerTimer();
    }

    public boolean checkAvailableCards() {
        int cardsAvailable = GameConfig.AMOUNT_HAND_CARDS;
        for (CardModel handCard : this.handCards) {
            if (handCard.getColor().getName().equals("none")) {
                cardsAvailable--;
            }
        }
        /*System.out.println("check-method: available cards: " + cardsAvailable);
        System.out.println("");*/
        return cardsAvailable >= 1;
    }

    public void decrementHandCards() {
        this.playableCards--;
    }

    public ArrayList<CardModel> getHandCards() {
        return this.handCards;
    }

    public int getPlayableCards() {
        return this.playableCards;
    }

    public void setPlayableCards(int playableCards) {
        this.playableCards = playableCards;
    }
}
