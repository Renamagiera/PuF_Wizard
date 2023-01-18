package com.ducky.duckythewizard.model.color;

import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameColorObject {
    private TrumpColor trumpColorObject;
    private LayoutColor layoutColorObject;
    private ArrayList<GameColor> trumpColors;
    private ArrayList<GameColor> layoutColors;
    private static final Map<String, GameColor> SPECIAL_CARDS = new HashMap<>();
    private static final Map<String , String> HEX_CODES = new HashMap<>();
    private static final Map<String, Color> RGB_MAP = new HashMap<>();
    private int colorAmount;

    public GameColorObject() {
        this.trumpColorObject = new TrumpColor();
        this.layoutColorObject = new LayoutColor();
        this.trumpColors = new ArrayList<>();
        addTrumpColorsToList();
        addLayoutColorsToList();
        setSpecialCardsMap();
        setHexCodesToMap();
        setRgbMap();
    }

    // get Color Objects
    public TrumpColor getTrumpColorObject() {
        return trumpColorObject;
    }

    public ArrayList<GameColor> getTrumpColors() { return trumpColors; }

    private void addTrumpColorsToList() {
        this.trumpColors.add(this.trumpColorObject.getRed());
        this.trumpColors.add(this.trumpColorObject.getBlue());
        this.trumpColors.add(this.trumpColorObject.getGreen());
        this.trumpColors.add(this.trumpColorObject.getYellow());
        this.trumpColors.add(this.trumpColorObject.getNone());
        this.colorAmount = trumpColors.size();
    }

    private void addLayoutColorsToList() {
        this.layoutColors = new ArrayList<>(trumpColors);
        this.layoutColors.remove(this.layoutColors.size() - 1);
        this.layoutColors.add(this.layoutColorObject.getWhite());
        this.layoutColors.add(this.layoutColorObject.getBlack());
        this.layoutColors.add(this.layoutColorObject.getBrown());
        this.layoutColors.add(this.layoutColorObject.getOrange());
    }

    private void setSpecialCardsMap() {
        SPECIAL_CARDS.put("wizard", this.trumpColorObject.getWizard());
        SPECIAL_CARDS.put("none", this.trumpColorObject.getNone());
    }

    private void setHexCodesToMap() {
        HEX_CODES.put(GameConfig.COLOR_RED_HEXCODE.getValue0(),GameConfig.COLOR_RED_HEXCODE.getValue1());
        HEX_CODES.put(GameConfig.COLOR_BLUE_HEXCODE.getValue0(),GameConfig.COLOR_BLUE_HEXCODE.getValue1());
        HEX_CODES.put(GameConfig.COLOR_GREEN_HEXCODE.getValue0(),GameConfig.COLOR_GREEN_HEXCODE.getValue1());
        HEX_CODES.put(GameConfig.COLOR_YELLOW_HEXCODE.getValue0(),GameConfig.COLOR_YELLOW_HEXCODE.getValue1());
        HEX_CODES.put(GameConfig.COLOR_WHITE_HEXCODE.getValue0(),GameConfig.COLOR_WHITE_HEXCODE.getValue1());
        HEX_CODES.put(GameConfig.COLOR_BLACK_HEXCODE.getValue0(), GameConfig.COLOR_BLACK_HEXCODE.getValue1());
        HEX_CODES.put(GameConfig.COLOR_BLACK_HEXCODE.getValue0(), GameConfig.COLOR_BLACK_HEXCODE.getValue1());
        HEX_CODES.put(GameConfig.COLOR_ORANGE_HEXCODE.getValue0(), GameConfig.COLOR_ORANGE_HEXCODE.getValue1());
    }

    private void setRgbMap() {
        RGB_MAP.put(GameConfig.COLOR_RED_HEXCODE.getValue0(), Color.rgb(GameConfig.COLOR_RED_RGB.getValue0(),GameConfig.COLOR_RED_RGB.getValue1(),GameConfig.COLOR_RED_RGB.getValue2()));

        RGB_MAP.put(GameConfig.COLOR_BLUE_HEXCODE.getValue0(), Color.rgb(GameConfig.COLOR_BLUE_RGB.getValue0(),GameConfig.COLOR_BLUE_RGB.getValue1(),GameConfig.COLOR_BLUE_RGB.getValue2()));

        RGB_MAP.put(GameConfig.COLOR_GREEN_HEXCODE.getValue0(), Color.rgb(GameConfig.COLOR_GREEN_RGB.getValue0(),GameConfig.COLOR_GREEN_RGB.getValue1(),GameConfig.COLOR_GREEN_RGB.getValue2()));

        RGB_MAP.put(GameConfig.COLOR_YELLOW_HEXCODE.getValue0(), Color.rgb(GameConfig.COLOR_YELLOW_RGB.getValue0(),GameConfig.COLOR_YELLOW_RGB.getValue1(),GameConfig.COLOR_YELLOW_RGB.getValue2()));

        RGB_MAP.put(GameConfig.COLOR_NONE_HEXCODE.getValue0(), Color.rgb(GameConfig.COLOR_NONE_RGB.getValue0(),GameConfig.COLOR_NONE_RGB.getValue1(),GameConfig.COLOR_NONE_RGB.getValue2()));
    }

    public String getHexCodeFromMap(String color) {
        return HEX_CODES.get(color);
    }

    public GameColor generateRandomTrump() {
        return this.trumpColors.get((int) Math.floor(Math.random()*(this.colorAmount)));
    }

    public void tintObject(ImageView imageView, String colorName, boolean glow) {
        Color targetColor = RGB_MAP.get("none");

        for (int i = 0; i < GameConfig.TRUMP_COLORS_STRING.length - 1; i++) {
            if(colorName.equals(GameConfig.TRUMP_COLORS_STRING[i])) {
                targetColor = RGB_MAP.get(GameConfig.TRUMP_COLORS_STRING[i]);
                break;
            }
        }

        ColorAdjust colorAdjust = new ColorAdjust();

        double hue = map((targetColor.getHue() + 180) % 360, 0, 360, -1, 1);
        double saturation = targetColor.getSaturation();
        double brightness = map(targetColor.getBrightness(), 0, 1, -1,0);

        colorAdjust.setHue(hue);
        colorAdjust.setSaturation(saturation);
        colorAdjust.setBrightness(brightness);

        if (glow) {
            Glow glowEffect = new Glow();
            glowEffect.setLevel(0.9);
            glowEffect.setInput(colorAdjust);
            imageView.setEffect(glowEffect);
        } else {
            imageView.setEffect(colorAdjust);
        }
    }

    public void setOpacityImageView(ImageView imageView, double opacity) {
        imageView.setOpacity(opacity);
    }

    public static double map(double value, double start, double stop, double targetStart, double targetStop) {
        return targetStart + (targetStop - targetStart) * ((value - start) / (stop - start));
    }
}
