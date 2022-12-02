package com.ducky.duckythewizard.model.config;

import com.ducky.duckythewizard.model.Card;
import com.ducky.duckythewizard.model.CardDeck;
import com.ducky.duckythewizard.model.GameColors;
import com.ducky.duckythewizard.model.TrumpColor;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Map;

public class GameConfig {
    public static final int windowWidth = 600;
    public static final int windowHeight = 800;

    public static final double level_cellWidth = 50.0;
    public static final double level_cellHeight = 50.0;
    public static GameColors gameConfigColors = new GameColors();
    public static ArrayList<TrumpColor> trumpColors = gameConfigColors.getColors();
    public static TrumpColor colorWizard = gameConfigColors.getWizard();
    public static TrumpColor colorNone = gameConfigColors.getNone();


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

    public int getWindowWidth() {

        return windowWidth;
    }

    public int getWindowHeight() {

        return windowHeight;
    }

    public double getLevel_cellWidth() {
        return level_cellWidth;
    }
    public double getLevel_cellHeight() {
        return level_cellHeight;
    }
}
