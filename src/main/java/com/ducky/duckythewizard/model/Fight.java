package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.card.Card;

public class Fight {

    // TODO vielleicht doch eine Fight-Klasse, in der die Berechnungen etc. durchgeführt werden? Sodass der Fight-Ctrl nur noch die Methoden aufrufen muss für jeden Fight
    private Card playerCard;
    private Card stoneCard;

    public Card getPlayerCard() {
        return playerCard;
    }

    public Card getStoneCard() {
        return stoneCard;
    }

    public void setStoneCard(Card stoneCard) {
        this.stoneCard = stoneCard;
    }

    public void setPlayerCard(Card playerCard) {
        this.playerCard = playerCard;
    }
}
