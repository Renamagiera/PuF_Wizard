package com.ducky.duckythewizard.model;

public class Stone extends GameObject{

    private _Color color;
    private Card card;
    private CountDownTimer trumpTimer;
    private boolean isActive;

    public Stone() {
        super(true);
    }
}
