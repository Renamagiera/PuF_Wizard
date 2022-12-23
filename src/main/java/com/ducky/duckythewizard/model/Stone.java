package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.card.Card;
import com.ducky.duckythewizard.model.color.GameColor;
import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.image.ImageView;

public class Stone extends GameObject{

    private GameColorObject gameColorObject;
    private Card card;
    private CountDownTimer trumpTimer;

    private static int count;
    private String id;
    private boolean isActive = true;
    private GameColor randomTrumpColorStone;
    private ImageView stoneImgView;


    public Stone() {
        super(true);
        this.id = "stone" + count;
        count++;
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
        // starting timer to set stone active again in new thread
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(GameConfig.STONE_INACTIVE_TIMER);
                isActive = true;
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }
        });
        thread.start();
    }

    public void setRandomTrumpColorStone(GameColor randomTrumpColorStone) {
        this.randomTrumpColorStone = randomTrumpColorStone;
    }
    public void setStoneImgView(ImageView stoneImgView) {
        this.stoneImgView = stoneImgView;
    }

    public GameColor getRandomTrumpColorStone() {
        return randomTrumpColorStone;
    }
    public ImageView getStoneImgView() {
        return stoneImgView;
    }
}
