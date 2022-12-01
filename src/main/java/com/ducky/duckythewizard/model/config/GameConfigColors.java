package com.ducky.duckythewizard.model.config;

import com.ducky.duckythewizard.model._Color;
import java.util.ArrayList;

public class GameConfigColors {
    private static final ArrayList<_Color> COLORS = new ArrayList<>();
    private static final _Color RED = new _Color("red", "255,0,0");
    private static final _Color BLUE = new _Color("blue", "0,0,255");
    private static final _Color YELLOW = new _Color("yellow", "0,255,0");
    private static final _Color GREEN = new _Color("green", "255,255,0");
    private static final _Color NONE = new _Color("none", "0");
    private static final _Color WIZARD = new _Color("wizard", "0,0,0");

    public GameConfigColors() {
        setColorCollection();
    }

    private void setColorCollection() {
        COLORS.add(BLUE);
        COLORS.add(RED);
        COLORS.add(YELLOW);
        COLORS.add(GREEN);
    }
    public ArrayList<_Color> getColors() {
        return COLORS;
    }

    public _Color getWizard() {
        return WIZARD;
    }

    public _Color getNone() {
        return NONE;
    }

}
