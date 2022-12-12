package com.ducky.duckythewizard.model.color;

public class TrumpColor {
    private static final GameColor RED = new GameColor("red", "#FF6666");
    private static final GameColor BLUE = new GameColor("blue", "#66CCFF");
    private static final GameColor GREEN = new GameColor("green", "#91FF66");
    private static final GameColor YELLOW = new GameColor("yellow", "#FFE366");
    private static final GameColor WIZARD = new GameColor("wizard", "null");
    private static final GameColor NONE = new GameColor("none", "null");
    private int colorAmount;

    public TrumpColor() {}


    public GameColor getRed() {
        return RED;
    }
    public GameColor getBlue() {
        return BLUE;
    }
    public GameColor getGreen() {
        return GREEN;
    }
    public GameColor getYellow() {
        return YELLOW;
    }
    public GameColor getNone() {
        return NONE;
    }
    public GameColor getWizard() {
        return WIZARD;
    }
}
