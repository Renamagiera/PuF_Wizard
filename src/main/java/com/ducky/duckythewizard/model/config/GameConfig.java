package com.ducky.duckythewizard.model.config;

import com.ducky.duckythewizard.model.FightScene;
import javafx.scene.image.Image;
import org.javatuples.*;

import java.util.Objects;

public class GameConfig {

    /* ** WINDOW CONFIGURATION ** */
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 650;
    public static final int WINDOW_WIDTH_FIGHT_SCENE = 400;

    /* ** LEVEL CONFIGURATION ** */
    public static final double LEVEL_CELL_WIDTH = 50.0;
    public static final double LEVEL_CELL_HEIGHT = 50.0;

    /* ** CARD CONFIGURATION ** */
    public static final String CARDS_EMPTY_CARD_FILENAME = "/com/ducky/duckythewizard/images/cards/empty.png";
    public static final String CARDS_BACK_CARD_FILENAME = "/com/ducky/duckythewizard/images/cards/back.png";
    public static final String CARDS_WIZARD_FILENAME = "/com/ducky/duckythewizard/images/cards/wizard.png";

    public static final int CARDS_AMOUNT_TOTAL = 56;
    public static final int CARDS_AMOUNT_WIZARDS = 4;
    public static final int CARDS_AMOUNT_HANDCARDS = 5;
    public static int CARDS_VALUE_WIZARD = 199;
    public static final int CARDS_VALUE_MAX = 12;
    public static final int CARDS_VALUE_MIN = 0;
    public static final Image CARDS_EMPTY_CARD_IMAGE = new Image(Objects.requireNonNull(FightScene.class.getResourceAsStream(GameConfig.CARDS_EMPTY_CARD_FILENAME)));
    public static final Image CARDS_BACK_CARD_IMAGE = new Image(Objects.requireNonNull(FightScene.class.getResourceAsStream(GameConfig.CARDS_BACK_CARD_FILENAME)));

    /* ** PLAYER CONFIGURATION ** */
    public static final int PLAYER_MAX_HEALTH_POINTS = 3;
    public static final int PLAYER_ACTION_TIMER = 10;
    public static final int PLAYER_ACTION_TIMER_CRITICAL = 3;
    public static final int PLAYER_ACTION_TIMER_INITIAL_DELAY = 0;
    public static final int PLAYER_ACTION_TIMER_PERIOD = 1;

    /* ** SPRITE CONFIGURATION ** */
    public static final double SPRITE_SCALE_FACTOR = 2;

    /* ** STONE CONFIGURATION ** */
    public static final String STONE_IMAGE_FILE_NAME = "/com/ducky/duckythewizard/images/forest/rock_50px.png";
    public static final int STONE_INACTIVE_TIMER = 10000;
    public static final int STONE_CHANGE_COLOR_RATE_MIN = 2;
    public static final int STONE_CHANGE_COLOR_RATE_MAX = 4;
    public static final int STONE_TRUMP_INITIAL_DELAY = 3;
    public static final int STONE_TRUMP_PERIOD = 3;

    /* ** COLOR CONFIGURATION: TRUMP COLORS - STRING ** */
    public static final String[] TRUMP_COLORS_STRING = {"red", "blue", "green", "yellow", "none"};
    public static final String COLOR_RED_STRING = "red";
    public static final String COLOR_BLUE_STRING = "blue";
    public static final String COLOR_GREEN_STRING = "green";
    public static final String COLOR_YELLOW_STRING = "yellow";
    public static final String COLOR_NONE_STRING = "none";

    /* ** COLOR CONFIGURATION: TRUMP COLORS - STRING & HEXCODES ** */
    public static final Pair<String, String> COLOR_RED_HEXCODE = new Pair<>(COLOR_RED_STRING, "#ff6666");
    public static final Pair<String, String> COLOR_BLUE_HEXCODE = new Pair<>(COLOR_BLUE_STRING, "#66ccff");
    public static final Pair<String, String> COLOR_GREEN_HEXCODE = new Pair<>(COLOR_GREEN_STRING, "#91ff66");
    public static final Pair<String, String> COLOR_YELLOW_HEXCODE = new Pair<>(COLOR_YELLOW_STRING, "#ffe366");
    public static final Pair<String, String> COLOR_WIZARD_HEXCODE = new Pair<>("wizard", "null");
    public static final Pair<String, String> COLOR_NONE_HEXCODE = new Pair<>(COLOR_NONE_STRING, "#ffffff");

    /* ** COLOR CONFIGURATION: TRUMP COLORS - RGB ** */
    public static final Triplet<Integer,Integer,Integer> COLOR_RED_RGB = new Triplet<>(208,32,144);
    public static final Triplet<Integer,Integer,Integer> COLOR_BLUE_RGB = new Triplet<>(100,205,124);
    public static final Triplet<Integer,Integer,Integer> COLOR_GREEN_RGB = new Triplet<>(255,255,150);
    public static final Triplet<Integer,Integer,Integer> COLOR_YELLOW_RGB = new Triplet<>(255,102,102);
    public static final Triplet<Integer,Integer,Integer> COLOR_NONE_RGB = new Triplet<>(255,255,255);

    /* ** COLOR CONFIGURATION: LAYOUT COLORS - HEXCODES ** */
    public static final Pair<String, String> COLOR_WHITE_HEXCODE = new Pair<>("white", "#ffffff");
    public static final Pair<String, String> COLOR_BLACK_HEXCODE = new Pair<>("black", "#000000");
    public static final Pair<String, String> COLOR_BROWN_HEXCODE = new Pair<>("brown", "#412b0f");
    public static final Pair<String, String> COLOR_ORANGE_HEXCODE = new Pair<>("orange", "#d68139");

    /* ** COLOR CONFIGURATION: LAYOUT COLORS - STRING ** */
    public static final String COLOR_WHITE_STRING = "white";

}
