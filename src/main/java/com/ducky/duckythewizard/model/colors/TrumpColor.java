package com.ducky.duckythewizard.model.colors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrumpColor {
    private static final ArrayList<GameColor> TRUMP_COLORS = new ArrayList<>();
    private static final Map<String, GameColor> SPECIAL_CARDS = new HashMap<>();
    private static final Map<String , String> HEX_CODES_TRUMP_COLORS = new HashMap<>();
    private static final GameColor RED = new GameColor("red", "#FF6666");
    private static final GameColor BLUE = new GameColor("blue", "#66CCFF");
    private static final GameColor GREEN = new GameColor("green", "#91FF66");
    private static final GameColor YELLOW = new GameColor("yellow", "#FFE366");
    private static final GameColor WIZARD = new GameColor("wizard", "null");
    private static final GameColor NONE = new GameColor("none", "null");
    private int colorAmount;

    public TrumpColor() {
        setTrumpColors();
        setSpecialCardsMap();
        setHexCodes();
    }

    private void setTrumpColors() {
        TRUMP_COLORS.add(RED);
        TRUMP_COLORS.add(BLUE);
        TRUMP_COLORS.add(GREEN);
        TRUMP_COLORS.add(YELLOW);
        TRUMP_COLORS.add(NONE);
        this.colorAmount = TRUMP_COLORS.size();
    }

    private void setSpecialCardsMap() {
        SPECIAL_CARDS.put("wizard", WIZARD);
        SPECIAL_CARDS.put("none", NONE);
    }

    private void setHexCodes() {
        HEX_CODES_TRUMP_COLORS.put("red", "#FF6666");
        HEX_CODES_TRUMP_COLORS.put("blue", "#66CCFF");
        HEX_CODES_TRUMP_COLORS.put("green", "#91FF66");
        HEX_CODES_TRUMP_COLORS.put("yellow", "#FFE366");
    }

    public String getRandomTrumpColor() {
        GameColor randomColor = TRUMP_COLORS.get((int) Math.floor(Math.random()*(this.colorAmount)));
        return HEX_CODES_TRUMP_COLORS.get(randomColor.getName());
    }

    public void toStringTrumpColors() {
        for (GameColor color : TRUMP_COLORS) {
            System.out.println("color name: " + color.getName());
        }
    }

    public static String getHexCode(String color) {
        return HEX_CODES_TRUMP_COLORS.get(color);
    }
    public ArrayList<GameColor> getTrumpColors() { return TRUMP_COLORS; }
    public Map<String, GameColor> getTrumpColorsSpecialCards() { return SPECIAL_CARDS; }
}
