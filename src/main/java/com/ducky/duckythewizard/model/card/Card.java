package com.ducky.duckythewizard.model.card;

import com.ducky.duckythewizard.model.color.GameColor;

import java.util.Objects;

public class Card {
    private GameColor color;
    private int value;
    private String imgFileName;

    public Card() {}

    public Card(GameColor color, int value, String imgFileName) {
        this.color = color;
        this.value = value;
        this.imgFileName = imgFileName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Card) obj;
        return Objects.equals(this.color, that.color) &&
                this.value == that.value &&
                Objects.equals(this.imgFileName, that.imgFileName);
    }

    @Override
    public String toString() {
        return "Card[" +
                "color=" + color.getName() + ", " +
                "value=" + value + ", " +
                "imgFileName=" + imgFileName + ']';
    }

    public GameColor getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public String getImgFileName() {
        return imgFileName;
    }
}
