package com.ducky.duckythewizard.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameConfig {

    // Game configurations
    // Colors
    public static final _Color RED = new _Color("red", "255,0,0");
    public static final _Color BLUE = new _Color("blue", "0,0,255");
    public static final _Color YELLOW = new _Color("yellow", "0,255,0");
    public static final _Color GREEN = new _Color("green", "255,255,0");
    public static final ArrayList<_Color> COLORS = new ArrayList<>();

    // Cards
    public static CardDeck deck = new CardDeck();
    public static ArrayList<Card> handedCards = new ArrayList<>();
    public static Map<String, Integer> CARD_SLOT_POSITION = new HashMap<>();
    public static final int MAX_CARD_VALUE = 12;
    public static final int MIN_CARD_VALUE = 0;
    public static final int AMOUNT_HAND_CARDS = 5;
    public static final int AMOUNT_CARDS = 52;
    public static final int CARD_HEIGHT = 104;
    public static final int CARD_WIDTH = 65;

    public static void initialize() {
        setCardSlotNumbers();
        setColorCollection();
    }

    public static void setCardSlotNumbers() {
        GameConfig.CARD_SLOT_POSITION.put("firstCard", 0);
        GameConfig.CARD_SLOT_POSITION.put("secondCard", 1);
        GameConfig.CARD_SLOT_POSITION.put("thirdCard", 2);
        GameConfig.CARD_SLOT_POSITION.put("fourthCard", 3);
        GameConfig.CARD_SLOT_POSITION.put("fifthCard", 4);
    }

    private static void setColorCollection() {
        GameConfig.COLORS.add(GameConfig.BLUE);
        GameConfig.COLORS.add(GameConfig.RED);
        GameConfig.COLORS.add(GameConfig.YELLOW);
        GameConfig.COLORS.add(GameConfig.GREEN);
    }

    /*
    // Singleton configuration class
    private static final GameConfig GAME_CONFIG = new GameConfig();

    static final Color RED = new Color("red", "255,0,0");
    static final Color BLUE = new Color("blue", "0,0,255");
    static final Color YELLOW = new Color("yellow", "0,255,0");
    static final Color GREEN = new Color("green", "255,255,0");

    private GameConfig() {}
    public static GameConfig getInstance() {
        return GAME_CONFIG;
    }
     */
}
