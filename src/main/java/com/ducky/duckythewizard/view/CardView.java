package com.ducky.duckythewizard.view;

import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class CardView extends View {
    private AnchorPane anchorPaneCards;
    private final double spaceBetweenCards = 30.0;

    public CardView(AnchorPane anchorPaneCards) {
        this.anchorPaneCards = anchorPaneCards;
    }

    public void renderImgViews() {
        double xPosition = super.calcSpaceLeftRight(spaceBetweenCards, GameConfig.AMOUNT_HAND_CARDS, GameConfig.WINDOW_WIDTH);
        System.out.println(super.calcSpaceLeftRight(spaceBetweenCards, GameConfig.AMOUNT_HAND_CARDS, GameConfig.WINDOW_WIDTH));
        for (int i = 0; i < GameConfig.AMOUNT_HAND_CARDS; i++) {
            ImageView imageView = new ImageView();
            imageView.setId("handCard" + i);
            imageView.setFitHeight(GameConfig.CARD_HEIGHT);
            imageView.setFitWidth(GameConfig.CARD_WIDTH);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);
            imageView.setLayoutX(xPosition);
            xPosition = xPosition + GameConfig.CARD_WIDTH + spaceBetweenCards;
            super.newImage(GameConfig.EMPTY_CARD_FILENAME, imageView);
            this.anchorPaneCards.getChildren().add(imageView);
        }
    }
}
