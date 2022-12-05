package com.ducky.duckythewizard.model;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class TextObject {
    private String newText;
    private GameColor fontColor;
    private double fontSize;
    private int x;
    private int y;

    public TextObject(String newText, GameColor fontColor, double fontSize, int x, int y, AnchorPane anchorPane) {
        this.newText = newText;
        this.fontColor = fontColor;
        this.fontSize = fontSize;
        this.x = x;
        this.y = y;
        addTextToNode(anchorPane, this);
    }

    public void setNewText(String newText) {
        this.newText = newText;
    }

    public void setFontColor(GameColor fontColor) {
        this.fontColor = fontColor;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void addTextToNode(AnchorPane anchorPane, TextObject textObject) {

    }

}
