package com.ducky.duckythewizard.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {

    // private int deckSize = GameConfig.getDeckSize(); ???
    private ArrayList<Card> cardDeck = new ArrayList<>();
    private ArrayList<Color> colors = new ArrayList<>();
    private ArrayList<Card> handedCards = new ArrayList<>();

    public CardDeck() {

        colors.add(GameConfig.blue);
        colors.add(GameConfig.red);
        colors.add(GameConfig.yellow);
        colors.add(GameConfig.green);
        // generate CardDeck
        for (Color color : colors) {
            for (int i = 0; i <= 12; i++) {
                Image cardImg = new Image("C:\\Users\\reuba\\IdeaProjects\\PuF_Wizard\\src\\main\\resources\\com\\ducky\\duckythewizard\\images\\forest\\rock_50px.png");
                cardDeck.add(new Card(color, i+1, cardImg));
            }
        }
        shuffleCards();
    }

    public void shuffleCards() {
        Collections.shuffle(cardDeck);
    }

    public void takeCardsFromDeck(int amount) {
        shuffleCards();
        for (int i = 0; i < amount; i++) {
            handedCards.add(cardDeck.remove(i));
        }
    }

    public void showAllCardDeckInfo() {
        for (Card card : cardDeck) {
            System.out.println(card.getValue() + ", " + card.getColor().getName());
        }
        System.out.println("Cards: " + cardDeck.size());
        System.out.println("");
        for (Card card : handedCards) {
            System.out.println(card.getValue() + ", " + card.getColor().getName());
        }
        System.out.println("Handed Cards: " + handedCards.size());
    }
}
