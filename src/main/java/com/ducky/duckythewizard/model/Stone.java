package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.card.Card;
import com.ducky.duckythewizard.model.color.GameColor;
import javafx.scene.image.ImageView;

public class Stone extends GameObject{

    private Card card;

    private static int count;
    private String id;
    private boolean isActive = true;
    private boolean isChangingColor = false;
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
        return this.id;
    }
    public Card getCard() {
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
    public void setIsChangingColor(boolean changingColor) {
        this.isChangingColor = changingColor;
    }
    public boolean getIsChangingColor() {
        return this.isChangingColor;
    }


}
