package com.ducky.duckythewizard.model.colors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LayoutColor {
    private static final GameColor WHITE = new GameColor("white", "#FFFFFF");
    private static final GameColor BLACK = new GameColor("black", "#000000");
    private static final GameColor BROWN = new GameColor("brown", "#412B0F");
    private static final GameColor ORANGE = new GameColor("orange", "#D68139");

    public LayoutColor() {};

    public GameColor getWhite() {
        return WHITE;
    }
    public GameColor getBlack() {
        return BLACK;
    }
    public GameColor getBrown() {
        return BROWN;
    }
    public GameColor getOrange() {
        return ORANGE;
    }
}
