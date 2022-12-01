package com.ducky.duckythewizard.model.config;

import com.ducky.duckythewizard.model.Card;
import com.ducky.duckythewizard.model.CardDeck;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameConfigCards {
    public static final String EMPTY_CARD_FILENAME = "/com/ducky/duckythewizard/images/cards/empty.png";
    public static CardDeck deckObject = new CardDeck();
    public static ArrayList<Card> deck = deckObject.getCardDeck();
    public static final Map<String, Integer> CARD_SLOT_POSITION = new HashMap<>();

    public static final int AMOUNT_WIZARDS = 4;
    public static final String WIZARD_FILENAME = "/com/ducky/duckythewizard/images/cards/wizard.png";
    public static int WIZARD_POINTS = 100;
    public static final int MAX_CARD_VALUE = 12;
    public static final int MIN_CARD_VALUE = 0;
    public static final int AMOUNT_HAND_CARDS = 5;
    public static final int AMOUNT_CARDS = 52;
    public static final int CARD_HEIGHT = 104;
    public static final int CARD_WIDTH = 65;

    public GameConfigCards() {
        setCardSlotNumbers();
    }

    private void setCardSlotNumbers() {
        CARD_SLOT_POSITION.put("firstCard", 0);
        CARD_SLOT_POSITION.put("secondCard", 1);
        CARD_SLOT_POSITION.put("thirdCard", 2);
        CARD_SLOT_POSITION.put("fourthCard", 3);
        CARD_SLOT_POSITION.put("fifthCard", 4);
    }

    public String getEmptyCardFilename() {
        return EMPTY_CARD_FILENAME;
    }

    public String getWizardFilename() {
        return WIZARD_FILENAME;
    }

    public CardDeck getDeckObject() {
        return deckObject;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public int getAmountWizards() {
        return AMOUNT_WIZARDS;
    }

    public int getWizardPoints() {
        return WIZARD_POINTS;
    }

    public Map<String, Integer> getCardSlotPosition() {
        return CARD_SLOT_POSITION;
    }

    public int getMaxCardValue() {
        return MAX_CARD_VALUE;
    }

    public int getMinCardValue() {
        return MIN_CARD_VALUE;
    }

    public int getAmountHandCards() {
        return AMOUNT_HAND_CARDS;
    }
}
