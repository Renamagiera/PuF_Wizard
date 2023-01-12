package com.ducky.duckythewizard.model.color;

import com.ducky.duckythewizard.model.config.GameConfig;

public class TrumpColor {
    private static final GameColor RED = new GameColor(GameConfig.COLOR_RED_HEXCODE.getValue0(),GameConfig.COLOR_RED_HEXCODE.getValue1());
    private static final GameColor BLUE = new GameColor(GameConfig.COLOR_BLUE_HEXCODE.getValue0(),GameConfig.COLOR_BLUE_HEXCODE.getValue1());
    private static final GameColor GREEN = new GameColor(GameConfig.COLOR_GREEN_HEXCODE.getValue0(),GameConfig.COLOR_GREEN_HEXCODE.getValue1());
    private static final GameColor YELLOW = new GameColor(GameConfig.COLOR_YELLOW_HEXCODE.getValue0(),GameConfig.COLOR_YELLOW_HEXCODE.getValue1());
    private static final GameColor WIZARD = new GameColor(GameConfig.COLOR_WIZARD_HEXCODE.getValue0(),GameConfig.COLOR_WIZARD_HEXCODE.getValue1());
    private static final GameColor NONE = new GameColor(GameConfig.COLOR_NONE_HEXCODE.getValue0(),GameConfig.COLOR_NONE_HEXCODE.getValue1());
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
