package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.card.Card;
import com.ducky.duckythewizard.model.card.CardDeck;
import com.ducky.duckythewizard.model.config.GameConfig;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> handCards;
    private int playableCards;

    public Player(CardDeck deck) {
        this.handCards = deck.dealHandCards(deck.getCardDeck());
        this.playableCards = GameConfig.AMOUNT_HAND_CARDS;
    }

    public boolean checkAvailableCards() {
        int cardsAvailable = GameConfig.AMOUNT_HAND_CARDS;
        for (Card handCard : this.handCards) {
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

    public ArrayList<Card> getHandCards() {
        return handCards;
    }

    public int getPlayableCards() {
        return playableCards;
    }

    public void setPlayableCards(int playableCards) {
        this.playableCards = playableCards;
    }
}
