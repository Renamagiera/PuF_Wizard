package com.ducky.duckythewizard.model.config;

public class GameConfigCards {

    public static final int AMOUNT_WIZARDS = 4;
    public static int WIZARD_POINTS = 100;
    public static final int MAX_CARD_VALUE = 12;
    public static final int MIN_CARD_VALUE = 0;
    public static final int AMOUNT_HAND_CARDS = 5;
    public static final String EMPTY_CARD_FILENAME = "/com/ducky/duckythewizard/images/cards/empty.png";
    public static final String WIZARD_FILENAME = "/com/ducky/duckythewizard/images/cards/wizard.png";

    public GameConfigCards() {}

    public String getEmptyCardFilename() {
        return EMPTY_CARD_FILENAME;
    }

    public String getWizardFilename() {
        return WIZARD_FILENAME;
    }

    public int getAmountWizards() {
        return AMOUNT_WIZARDS;
    }

    public int getWizardPoints() {
        return WIZARD_POINTS;
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
