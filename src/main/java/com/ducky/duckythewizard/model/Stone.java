package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.colors.GameColorObject;

public class Stone extends GameObject{

    private GameColorObject gameColorObject;
    private Card card;
    private CountDownTimer trumpTimer;
    private boolean isActive;

    public Stone() {
        super(true);
    }
}
