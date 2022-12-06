package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.colors.GameColorObject;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Stone extends GameObject{

    private GameColorObject gameColorObject;
    private Card card;
    private CountDownTimer trumpTimer;
    private static int count;
    private String id;
    private boolean isActive;

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
}
