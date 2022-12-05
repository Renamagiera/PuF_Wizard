package com.ducky.duckythewizard.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrumpColor {
    private static final ArrayList<GameColor> TRUMP_COLORS = new ArrayList<>();
    private static final Map<String, GameColor> SPECIAL_CARDS = new HashMap<>();
    private static final GameColor RED = new GameColor("red", "255,0,0");
    private static final GameColor BLUE = new GameColor("blue", "0,0,255");
    private static final GameColor YELLOW = new GameColor("yellow", "0,255,0");
    private static final GameColor GREEN = new GameColor("green", "255,255,0");
    private static final GameColor WIZARD = new GameColor("wizard", "0,0,0");
    private static final GameColor NONE = new GameColor("none", "0");

    public TrumpColor() {
        setTrumpColors();
        setSpecialCardsMap();
    }

    private void setTrumpColors() {
        TRUMP_COLORS.add(BLUE);
        TRUMP_COLORS.add(RED);
        TRUMP_COLORS.add(YELLOW);
        TRUMP_COLORS.add(GREEN);
        TRUMP_COLORS.add(NONE);
    }

    private void setSpecialCardsMap() {
        SPECIAL_CARDS.put("wizard", WIZARD);
        SPECIAL_CARDS.put("none", NONE);
    }

    public ArrayList<GameColor> getTrumpColors() { return TRUMP_COLORS; }
    public Map<String, GameColor> getTrumpColorsMapNoColor() { return SPECIAL_CARDS; }
}
