package com.ducky.duckythewizard.model.color;

import javafx.scene.effect.ColorAdjust;

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

    public LayoutColor getLayoutColorObject() {
        return layoutColorObject;
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
        HEX_CODES.put("red", "#FF6666");
        HEX_CODES.put("blue", "#66CCFF");
        HEX_CODES.put("green", "#91FF66");
        HEX_CODES.put("yellow", "#FFE366");
        HEX_CODES.put("white", "#FFFFFF");
        HEX_CODES.put("black", "#000000");
        HEX_CODES.put("brown", "#412B0F");
        HEX_CODES.put("orange", "#D68139");
    }

    private void setRgbMap() {
        //
        RGB_MAP.put("yellow", Color.rgb(255,102,102));
        //
        RGB_MAP.put("blue", Color.rgb(100,205,124));
        //
        RGB_MAP.put("green", Color.rgb(255,255,150));
        //
        RGB_MAP.put("red", Color.rgb(208,32,144));
        //
        RGB_MAP.put("none", Color.rgb(255,255,255));
        //
        RGB_MAP.put("grey", Color.rgb(150,150,150));
        //
        RGB_MAP.put("test", Color.rgb(40,30,255));
        //
        RGB_MAP.put("test2", Color.rgb(255,255,0));
        //
        RGB_MAP.put("test3", Color.rgb(150,150,255));
    }

    public String getHexCodeFromMap(String color) {
        return HEX_CODES.get(color);
    }

    public GameColor generateRandomTrump() {
        return trumpColors.get((int) Math.floor(Math.random()*(this.colorAmount)));
    }

    public void toStringTrumpColors() {
        for (GameColor color : trumpColors) {
            System.out.println("color name: " + color.getName());
        }
    }

    public void toStringLayoutColors() {
        for (GameColor color : layoutColors) {
            System.out.println("color name: " + color.getName());
        }
    }

    public void tintStone(ImageView imageView, String stoneColorName) {
        RGB_MAP.get("none");
        Color targetStoneColor = switch (stoneColorName) {
            case "red" -> RGB_MAP.get("red");
            case "blue" -> RGB_MAP.get("blue");
            case "green" -> RGB_MAP.get("green");
            case "yellow" -> RGB_MAP.get("yellow");
            case "grey" -> RGB_MAP.get("grey");
            case "test" -> RGB_MAP.get("test");
            case "test2" -> RGB_MAP.get("test2");
            case "test3" -> RGB_MAP.get("test3");
            default -> RGB_MAP.get("none");
        };

        ColorAdjust colorAdjust = new ColorAdjust();

        double hue = map((targetStoneColor.getHue() + 180) % 360, 0, 360, -1, 1);
        double saturation = targetStoneColor.getSaturation();
        double brightness = map(targetStoneColor.getBrightness(), 0, 1, -1,0);

        colorAdjust.setHue(hue);
        colorAdjust.setSaturation(saturation);
        colorAdjust.setBrightness(brightness);

        imageView.setEffect(colorAdjust);
    }

    public void setOpacityImageView(ImageView imageView, double opacity) {
        imageView.setOpacity(opacity);
    }

    public static double map(double value, double start, double stop, double targetStart, double targetStop) {
        return targetStart + (targetStop - targetStart) * ((value - start) / (stop - start));
    }
}
