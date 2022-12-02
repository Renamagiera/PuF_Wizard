package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.TrumpColor;
import java.util.ArrayList;

public class GameColors {
    private static final ArrayList<TrumpColor> COLORS = new ArrayList<>();
    private static final TrumpColor RED = new TrumpColor("red", "255,0,0");
    private static final TrumpColor BLUE = new TrumpColor("blue", "0,0,255");
    private static final TrumpColor YELLOW = new TrumpColor("yellow", "0,255,0");
    private static final TrumpColor GREEN = new TrumpColor("green", "255,255,0");
    private static final TrumpColor NONE = new TrumpColor("none", "0");
    private static final TrumpColor WIZARD = new TrumpColor("wizard", "0,0,0");

    public GameColors() {
        setColorCollection();
    }

    private void setColorCollection() {
        COLORS.add(BLUE);
        COLORS.add(RED);
        COLORS.add(YELLOW);
        COLORS.add(GREEN);
    }
    public ArrayList<TrumpColor> getColors() {
        return COLORS;
    }

    public TrumpColor getWizard() {
        return WIZARD;
    }

    public TrumpColor getNone() {
        return NONE;
    }

}
