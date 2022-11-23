package com.ducky.duckythewizard.model;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class CardDeck {

    private static final ArrayList<Card> CARD_DECK = new ArrayList<>();

    public CardDeck() {
        createEmptyCardSlots();
    }

    public static ArrayList<Card> getCardDeck() {
        return CARD_DECK;
    }

    public void addAndRenderALlCards(AnchorPane emptyCardSlots) {
        GameConfig.deck.addCardsToDeck();
        GameConfig.handedCards = GameConfig.deck.dealHandCards();
        renderCardImages(emptyCardSlots);
    }

    public void addCardsToDeck() {
        for (_Color color : GameConfig.COLORS) {
            for (int i = GameConfig.MIN_CARD_VALUE; i <= GameConfig.MAX_CARD_VALUE; i++) {
                String colorName = color.getName();
                String imgFileName = "/com/ducky/duckythewizard/images/cards/"+colorName+"/"+colorName+i+".png";
                CARD_DECK.add(new Card(color, i+1, imgFileName));
            }
        }
        shuffleCards();
    }

    private void createEmptyCardSlots() {
        for (int i = GameConfig.MIN_CARD_VALUE; i <= GameConfig.AMOUNT_HAND_CARDS; i++) {

        }
    }

    public void shuffleCards() {
        Collections.shuffle(CARD_DECK);
    }

    public ArrayList<Card> dealHandCards() {
        ArrayList<Card> takenCards = new ArrayList<>();
        for (int i = GameConfig.MIN_CARD_VALUE; i < GameConfig.AMOUNT_HAND_CARDS; i++) {
            System.out.println();
            Card takenCard = CARD_DECK.remove(i);
            takenCard.setSlot(i);
            takenCards.add(takenCard);
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

    public void renderCardImages(AnchorPane emptyCardSlots) {
        // every Node in AnchorPane: every empty ImageView, as a child from AnchorPane
        int index = 0;
        for (Node imageView : emptyCardSlots.getChildren()) {
            ImageView castedImgView = (ImageView) imageView;
            Image handCardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(GameConfig.handedCards.get(index).getImgFileName())));
            castedImgView.setImage(handCardImage);
            index++;
        }
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
