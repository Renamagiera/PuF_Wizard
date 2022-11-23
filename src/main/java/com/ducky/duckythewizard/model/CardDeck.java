package com.ducky.duckythewizard.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class CardDeck {

    private static final ArrayList<Card> CARD_DECK = new ArrayList<>();

    public CardDeck() {
        ArrayList<_Color> colors = new ArrayList<>();
        colors.add(GameConfig.BLUE);
        colors.add(GameConfig.RED);
        colors.add(GameConfig.YELLOW);
        colors.add(GameConfig.GREEN);
        // generate CardDeck
        for (_Color color : colors) {
            for (int i = GameConfig.MIN_CARD_VALUE; i <= GameConfig.MAX_CARD_VALUE; i++) {
                String colorName = color.getName();
                String imgFileName = "/com/ducky/duckythewizard/images/cards/"+colorName+"/"+colorName+i+".png";
                CARD_DECK.add(new Card(color, i+1, imgFileName));
            }
        }
        shuffleCards();
    }

    public void shuffleCards() {
        Collections.shuffle(CARD_DECK);
    }

    public ArrayList<Card> takeCardsFromDeck(int amount) {
        ArrayList<Card> takenCards = new ArrayList<>();
        if (!(amount == 1)){
            for (int i = GameConfig.MIN_CARD_VALUE; i < amount; i++) {
                Card takenCard = CARD_DECK.remove(i);
                takenCard.setSlot(i);
                takenCards.add(takenCard);
            }
        } else {
            for (int i = GameConfig.MIN_CARD_VALUE; i < amount; i++) {
                takenCards.add(CARD_DECK.remove(i));
            }
        }
        return takenCards;
    }

    public Card findCard(String colorName, int value) {
        for (Card card : CARD_DECK) {
            if (card.getColor().getName().equals(colorName) && card.getValue() == value) {
                System.out.println("Card found: "+card.getColor().getName()+", "+card.getValue());
                return card;
            }
        }
        return null;
    }

    // DELETE ME LATER
    public static void showAllCardDeckInfo() {
        for (Card card : CARD_DECK) {
            System.out.println(card.getValue() + ", " + card.getColor().getName());
        }
        System.out.println("Cards: " + CARD_DECK.size());
    }

    public static void showHandCards(ArrayList<Card> handCards) {
        for (Card card : handCards) {
            System.out.println(card.getValue() + ", " + card.getColor().getName());
        }
    }
    // DELETE ME LATER
}
