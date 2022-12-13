package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.card.Card;
import com.ducky.duckythewizard.model.color.GameColor;
import com.ducky.duckythewizard.model.color.GameColorObject;

public class Fight {
    private Stone stoneInFight;
    private Card playerCard;
    private Card stoneCard;
    private GameColor trumpColor;

    public Fight(GameColorObject gameColorObject) {
        // set random color as trump-color
        this.trumpColor = gameColorObject.getRandomTrumpColor();
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
}
