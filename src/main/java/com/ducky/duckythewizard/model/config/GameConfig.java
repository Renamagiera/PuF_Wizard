package com.ducky.duckythewizard.model.config;

import com.ducky.duckythewizard.model.Card;
import com.ducky.duckythewizard.model.CardDeck;
import com.ducky.duckythewizard.model._Color;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Map;

public class GameConfig {
    public static GameConfigColors gameConfigColors = new GameConfigColors();
    public static ArrayList<_Color> trumpColors = gameConfigColors.getColors();
    public static _Color colorWizard = gameConfigColors.getWizard();
    public static _Color colorNone = gameConfigColors.getNone();


    public static GameConfigCards gameConfigCards = new GameConfigCards();
    public static AnchorPane anchorPaneCards;
    public static final String EMPTY_CARD_FILENAME = gameConfigCards.getEmptyCardFilename();
    public static final String WIZARD_FILENAME = gameConfigCards.getWizardFilename();
    public static CardDeck deckObject = gameConfigCards.getDeckObject();
    public static ArrayList<Card> deck = gameConfigCards.getDeck();
    public static ArrayList<Card> handedCards = new ArrayList<>();
    public static final int AMOUNT_WIZARDS = gameConfigCards.getAmountWizards();
    public static int WIZARD_POINTS = gameConfigCards.getWizardPoints();
    public static int playedCards = 0;
    public static final Map<String, Integer> CARD_SLOT_POSITION = gameConfigCards.getCardSlotPosition();
    public static final int MAX_CARD_VALUE = gameConfigCards.getMaxCardValue();
    public static final int MIN_CARD_VALUE = gameConfigCards.getMinCardValue();
    public static final int AMOUNT_HAND_CARDS = gameConfigCards.getAmountHandCards();
}
