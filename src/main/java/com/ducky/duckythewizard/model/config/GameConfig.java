package com.ducky.duckythewizard.model.config;

public class GameConfig {

    /* ** WINDOW CONFIGURATION ** */
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 650;
    public static final int WINDOW_WIDTH_FIGHT_SCENE = 400;
    public static final int WINDOW_HEIGHT_FIGHT_SCENE = 300;
    public static final int LAYOUT_Y_FIGHT_SCENE = 130;

    /* ** LEVEL CONFIGURATION ** */
    public static final double LEVEL_CELL_WIDTH = 50.0;
    public static final double LEVEL_CELL_HEIGHT = 50.0;

    /* ** CARD CONFIGURATION ** */
    public static GameConfigCards gameConfigCards = new GameConfigCards();
    public static final String EMPTY_CARD_FILENAME = gameConfigCards.getEmptyCardFilename();
    public static final String BACK_CARD_FILENAME = gameConfigCards.getBackCardFilename();
    public static final String WIZARD_FILENAME = gameConfigCards.getWizardFilename();
    public static final double CARD_HEIGHT = gameConfigCards.getCardHeight();
    public static final double CARD_WIDTH = gameConfigCards.getCardWidth();
    public static final int AMOUNT_CARDS = gameConfigCards.getAmountCards();
    public static final int AMOUNT_WIZARDS = gameConfigCards.getAmountWizards();
    public static final int AMOUNT_HAND_CARDS = gameConfigCards.getAmountHandCards();
    public static int WIZARD_POINTS = gameConfigCards.getWizardPoints();
    public static final int MAX_CARD_VALUE = gameConfigCards.getMaxCardValue();
    public static final int MIN_CARD_VALUE = gameConfigCards.getMinCardValue();

    /* ** SPRITE CONFIGURATION ** */
    public static final double SPRITE_SCALE_FACTOR = 1.75;

    /* ** STONE CONFIGURATION ** */
    public static final String STONE_IMAGE_FILE_NAME = "/com/ducky/duckythewizard/images/forest/rock_50px.png";
    public static final String STONE_IMAGE_FILE_NAME_CROPPED = "/com/ducky/duckythewizard/images/forest/rock_50px_cropped.png";
    public static final int STONE_INACTIVE_TIMER = 10000;
    public static final int STONE_CHANGE_COLOR_RATE_MIN = 5;
    public static final int STONE_CHANGE_COLOR_RATE_MAX = 10;
    public static final int TRUMP_TIMER = 2000;

    /* ** COLOR CONFIGURATION ** */


    public int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public int getWindowHeight() {

        return WINDOW_HEIGHT;
    }

    public double getLevel_cellWidth() {
        return LEVEL_CELL_WIDTH;
    }
    public double getLevel_cellHeight() {
        return LEVEL_CELL_HEIGHT;
    }
}
