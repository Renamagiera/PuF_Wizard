package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.card.CardModel;
import com.ducky.duckythewizard.model.color.GameColor;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Stone extends GameObject{

    private CardModel card;

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
        return this.id;
    }
    public CardModel getCard() {
        return this.card;
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
        return this.randomTrumpColorStone;
    }
    public ImageView getStoneImgView() {
        return this.stoneImgView;
    }

    public boolean getActive() {
        return this.isActive;
    }


}
