package com.ducky.duckythewizard.model;

import java.util.ArrayList;

public class GameConfig {

    // ??? GameConfig als Singleton implementieren ???

    static Color red = new Color("red", "255,0,0");
    static Color blue = new Color("blue", "0,0,255");
    static Color yellow = new Color("yellow", "0,255,0");
    static Color green = new Color("green", "255,255,0");

    static int card_height = 104;
    static int card_width = 65;

    // Deck
    // Handkarten

    private GameConfig() {

    }
}
