package com.ducky.duckythewizard.view;

import com.ducky.duckythewizard.controller.CardController;
import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class View {
    public View() {}

    public void newImage(String fileName, ImageView imgView) {
        Image newImage =  new Image(Objects.requireNonNull(getClass().getResourceAsStream(fileName)));
        imgView.setImage(newImage);
    }

    public double calcSpaceLeftRight(double spaceBetweenCards, int amountCards, double nodeWidth) {
        double handCardsWithoutSpace = GameConfig.CARD_WIDTH * amountCards;
        double handCardsWithSpace = handCardsWithoutSpace + ((amountCards - 1) * spaceBetweenCards);
        return (nodeWidth - handCardsWithSpace) / 2;
    }
}
