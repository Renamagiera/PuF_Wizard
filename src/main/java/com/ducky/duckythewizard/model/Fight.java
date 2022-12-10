package com.ducky.duckythewizard.model;

public class Fight {

    // TODO vielleicht doch eine Fight-Klasse, in der die Berechnungen etc. durchgeführt werden? Sodass der Fight-Ctrl nur noch die Methoden aufrufen muss für jeden Fight
    private Card playerCard;
    private Card enemyCard;

    public Card getPlayerCard() {
        return playerCard;
    }

    public Card getEnemyCard() {
        return enemyCard;
    }

    public void setEnemyCard(Card enemyCard) {
        this.enemyCard = enemyCard;
    }

    public void setPlayerCard(Card playerCard) {
        this.playerCard = playerCard;
    }
}
