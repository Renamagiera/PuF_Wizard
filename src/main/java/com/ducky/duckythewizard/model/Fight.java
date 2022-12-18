package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.card.Card;
import com.ducky.duckythewizard.model.color.GameColor;
import com.ducky.duckythewizard.model.color.GameColorObject;

public class Fight {
    private Stone stoneInFight;
    private Card playerCard;
    private Card stoneCard;
    private GameColor trumpColor;
    private boolean duckyPlaysFirst;
    private static int count;

    public Fight(GameColorObject gameColorObject) {
        count++;
        System.out.println("new Fight");
        System.out.println("new Fight nr: " + count);
        // set random color as trump-color
        this.trumpColor = gameColorObject.getRandomTrumpColor();
        // who starts
        this.duckyPlaysFirst = duckyPlaysFirst();
    }

    public Card getPlayerCard() {
        return playerCard;
    }

    public Card getStoneCard() {
        return stoneCard;
    }

    public GameColor getTrumpColor() {
        return trumpColor;
    }

    public void setStoneCard(Card stoneCard) {
        this.stoneCard = stoneCard;
    }

    public void setPlayerCard(Card playerCard) {
        this.playerCard = playerCard;
    }

    public void setTrumpColor(GameColor trumpColor) {
        this.trumpColor = trumpColor;
    }

    public void setStoneInFight(Stone stoneInFight) {
        this.stoneInFight = stoneInFight;
    }

    public Stone getStoneInFight() {
        return stoneInFight;
    }

    public boolean getDuckyPlaysFirst() {
        return this.duckyPlaysFirst;
    }

    private boolean duckyPlaysFirst() {
        int x = (int) Math.floor(Math.random()*(2-1+1)+1);
        if (x == 1) {
            System.out.println("Ducky plays first");
            return true;
        } else if (x == 2) {
            System.out.println("Enemy plays first");
            return false;
        } else {
            return false;
        }
    }
}
