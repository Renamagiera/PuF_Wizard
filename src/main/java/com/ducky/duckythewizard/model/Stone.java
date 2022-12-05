package com.ducky.duckythewizard.model;

public class Stone extends GameObject{

    private GameColor color;
    private Card card;
    private CountDownTimer trumpTimer;
    private boolean isActive;

    public Stone() {
        super(true);
    }
}
