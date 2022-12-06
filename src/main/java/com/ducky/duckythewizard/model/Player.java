package com.ducky.duckythewizard.model;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> handCards;

    public Player(CardDeck deck) {
        this.handCards = deck.dealHandCards(deck.getCardDeck());
    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }
}
