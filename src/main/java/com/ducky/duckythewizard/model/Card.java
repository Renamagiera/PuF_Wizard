package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.colors.GameColor;

import java.util.Objects;

public record Card(GameColor color, int value, String imgFileName) {

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


}
