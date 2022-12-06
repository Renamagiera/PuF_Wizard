package com.ducky.duckythewizard.model.colors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LayoutColor {
    private static final ArrayList<GameColor> LAYOUT_COLORS = new ArrayList<>();
    private static final Map<String, String> HEX_CODES_LAYOUT_COLORS = new HashMap<>();
    private static final GameColor WHITE = new GameColor("white", "#FFFFFF");
    private static final GameColor BLACK = new GameColor("black", "#000000");
    private static final GameColor BROWN = new GameColor("brown", "#412B0F");
    private static final GameColor ORANGE = new GameColor("orange", "#D68139");

    public LayoutColor() {
        setLayoutColors();
        setHexCodes();
    }

    private void setLayoutColors() {
        LAYOUT_COLORS.add(WHITE);
        LAYOUT_COLORS.add(BLACK);
        LAYOUT_COLORS.add(BROWN);
        LAYOUT_COLORS.add(ORANGE);
    }

    private void setHexCodes() {
        HEX_CODES_LAYOUT_COLORS.put("white", "#FFFFFF");
        HEX_CODES_LAYOUT_COLORS.put("black", "#000000");
        HEX_CODES_LAYOUT_COLORS.put("brown", "#412B0F");
        HEX_CODES_LAYOUT_COLORS.put("orange", "#D68139");
    }

    public void toStringLayoutColors() {
        for (GameColor color : LAYOUT_COLORS) {
            System.out.println("color name: " + color.getName());
        }
    }
}
