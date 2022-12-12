package com.ducky.duckythewizard.model.color;

public class GameColor {

    private String name;
    private String hexCode;

    public GameColor(String name, String hexCode){
        this.name = name;
        this.hexCode = hexCode;
    }

    public String getName() {
        return name;
    }

    public String getHexCode() {
        return hexCode;
    }

    public String toString() {
        return "name: " + name + ", " + "value: " + hexCode;
    }
}
