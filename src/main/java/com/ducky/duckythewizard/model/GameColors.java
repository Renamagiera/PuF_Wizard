package com.ducky.duckythewizard.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameColors {
    private static final ArrayList<TrumpColor> TRUMP_COLORS = new ArrayList<>();
    private static final Map<String, TrumpColor> SPECIAL_CARDS = new HashMap<>();
    private static final TrumpColor RED = new TrumpColor("red", "255,0,0");
    private static final TrumpColor BLUE = new TrumpColor("blue", "0,0,255");
    private static final TrumpColor YELLOW = new TrumpColor("yellow", "0,255,0");
    private static final TrumpColor GREEN = new TrumpColor("green", "255,255,0");
    private static final TrumpColor WIZARD = new TrumpColor("wizard", "0,0,0");
    private static final TrumpColor NONE = new TrumpColor("none", "0");

    public GameColors() {
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

    public ArrayList<TrumpColor> getTrumpColors() { return TRUMP_COLORS; }
    public Map<String, TrumpColor> getTrumpColorsMapNoColor() { return SPECIAL_CARDS; }
}
