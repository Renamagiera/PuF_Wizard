package com.ducky.duckythewizard.model;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Card {

    private Color color;
    private int value;
    private Image img;

    public Card(Color color, int value, Image img){
        this.color = color;
        this.value = value;
        this.img = img;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }
}
