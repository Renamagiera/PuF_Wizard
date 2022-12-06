package com.ducky.duckythewizard.model;

public class Stone extends GameObject{

    private TrumpColor color;
    private Card card;
    private CountDownTimer trumpTimer;
    private boolean isActive = true;

    public Stone() {
        super(true);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setInactive() {
        this.isActive = false;
        // TODO start Timer to set active again automatically
    }
}
