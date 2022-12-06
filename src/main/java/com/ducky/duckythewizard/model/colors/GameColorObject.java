package com.ducky.duckythewizard.model.colors;

import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.awt.Color.RGBtoHSB;
import static javafx.scene.paint.Color.*;

public class GameColorObject {
    private TrumpColor trumpColorObject;
    private LayoutColor layoutColorObject;
    private ArrayList<GameColor> trumpColors;
    private ArrayList<GameColor> layoutColors;
    private static final Map<String, GameColor> SPECIAL_CARDS = new HashMap<>();
    private static final Map<String , String> HEX_CODES = new HashMap<>();
    public Map<String, GameColor> getTrumpColorsSpecialCards() { return SPECIAL_CARDS; }
    private int colorAmount;

    public GameColorObject() {
        this.trumpColorObject = new TrumpColor();
        this.layoutColorObject = new LayoutColor();
        this.trumpColors = new ArrayList<>();
        addTrumpColorsToList();
        addLayoutColorsToList();
        setSpecialCardsMap();
        setHexCodesToMap();
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
        layoutColors.add(this.layoutColorObject.getWhite());
        layoutColors.add(this.layoutColorObject.getBlack());
        layoutColors.add(this.layoutColorObject.getBrown());
        layoutColors.add(this.layoutColorObject.getOrange());
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

    public String getHexCodeFromMap(String color) {
        return HEX_CODES.get(color);
    }

    public String getRandomTrumpColor() {
        GameColor randomColor = trumpColors.get((int) Math.floor(Math.random()*(this.colorAmount)));
        return HEX_CODES.get(randomColor.getName());
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

    public void colorImageView(ImageView imageView, double h, double s, double l) {
        ColorAdjust colorAdjust = new ColorAdjust();

        colorAdjust.setHue(h);

        colorAdjust.setSaturation(s);

        colorAdjust.setBrightness(l);

        colorAdjust.setContrast(0);

        imageView.setEffect(colorAdjust);

  /*      ColorInput colorInput = new ColorInput();
        colorInput.setX(imageView.getLayoutX());
        colorInput.setY(imageView.getLayoutY());
        colorInput.setHeight(imageView.getFitHeight());
        colorInput.setWidth(imageView.getFitWidth());
        colorInput.setPaint(Color.RED);
        imageView.setEffect(colorInput);*/

/*        //Instantiating the Glow class
        Glow glow = new Glow();
        //setting level of the glow effect
        glow.setLevel(0.9);
        imageView.setEffect(glow);*/

/*        Bloom bloom = new Bloom();
        //setting threshold for bloom
        bloom.setThreshold(0.1);
        //Applying bloom effect to text
        imageView.setEffect(bloom);*/
    }
}
