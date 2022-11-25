package com.ducky.duckythewizard.model;

public class Card {

    private _Color color;
    private int value;
    private String imgFileName;

    private int slot;

    public Card(_Color color, int value, String imgFileName){
        this.color = color;
        this.value = value;
        this.imgFileName = imgFileName;
    }

    public _Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public String getImgFileName() {
        return imgFileName;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
