package com.ducky.duckythewizard.model.colors;

public class GameColorObject {
    private TrumpColor trumpColorObject;

    private LayoutColor layoutColorObject;
    public GameColorObject() {
        this.trumpColorObject = new TrumpColor();
        this.layoutColorObject = new LayoutColor();
    }

    // get Color Objects
    public TrumpColor getTrumpColorObject() {
        return trumpColorObject;
    }

    public LayoutColor getLayoutColorObject() {
        return layoutColorObject;
    }
}
