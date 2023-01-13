package com.ducky.duckythewizard.model.color;

import com.ducky.duckythewizard.model.config.GameConfig;

public class LayoutColor {
    private static final GameColor WHITE = new GameColor(GameConfig.COLOR_YELLOW_HEXCODE.getValue0(),GameConfig.COLOR_YELLOW_HEXCODE.getValue1());
    private static final GameColor BLACK = new GameColor(GameConfig.COLOR_BLACK_HEXCODE.getValue0(), GameConfig.COLOR_BLACK_HEXCODE.getValue1());
    private static final GameColor BROWN = new GameColor(GameConfig.COLOR_BLACK_HEXCODE.getValue0(), GameConfig.COLOR_BLACK_HEXCODE.getValue1());
    private static final GameColor ORANGE = new GameColor(GameConfig.COLOR_ORANGE_HEXCODE.getValue0(), GameConfig.COLOR_ORANGE_HEXCODE.getValue1());

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
