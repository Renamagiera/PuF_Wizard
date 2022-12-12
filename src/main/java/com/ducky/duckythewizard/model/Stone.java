package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.card.Card;
import com.ducky.duckythewizard.model.color.GameColorObject;

public class Stone extends GameObject{

    private GameColorObject gameColorObject;
    private Card card;
    private CountDownTimer trumpTimer;

    private static int count;
    private String id;
    private boolean isActive = true;


    public Stone() {
        super(true);
        count++;
        this.id = "rock" + count;
    }

    public void setCard(Card card) {
        this.card = card;
    }
    public String getId() {
        return id;
    }
    public Card getCard() {
        return card;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setInactive() {
        this.isActive = false;
        // TODO start Timer to set active again automatically
    }
}
