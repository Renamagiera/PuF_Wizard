package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.card.CardModel;
import com.ducky.duckythewizard.model.color.GameColor;
import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Stone extends GameObject{

    private CardModel card;
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

    public void setCard(CardModel card) {
        this.card = card;
    }
    public String getId() {
        return id;
    }
    public CardModel getCard() {
        return card;
    }
    public boolean isActive() {
        return isActive;
    }

    // getter & setter
    public void setActive(boolean active) {
        isActive = active;
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

    public boolean getActive() {
        return this.isActive;
    }


}
