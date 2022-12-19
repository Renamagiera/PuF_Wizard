package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.controller.CardController;
import com.ducky.duckythewizard.model.color.GameColor;
import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class FightView {

    //public int ID;
    private AnchorPane parentAnchorPane;
    private AnchorPane anchorPaneFightOverlay;
    private CardController cardCtrl;
    private Fight activeFight;
    private ImageView imgViewCardStone;
    private ImageView imgViewCardDucky;
    private Label exitLbl;
    private GameColorObject gameColorObject;
    private GameColor trumpColorObject;
    private String trumpColorHexCode;
    private String trumpColorName;
    private Label trumpColorLabel;
    private Label cardChooseTextLabel;
    private static final String FONT_SIZE_LABEL_TRUMP_COLOR = "20px";
    public FightView() {
    }
    public void renderFightScene(AnchorPane anchorPaneFightOverlay, AnchorPane anchorPaneRoot, Fight activeFight) {
        this.anchorPaneFightOverlay = anchorPaneFightOverlay;
        this.parentAnchorPane = anchorPaneRoot;
        this.anchorPaneFightOverlay.setVisible(true);
        this.activeFight = activeFight;
        this.trumpColorName = this.activeFight.getStoneInFight().getRandomTrumpColor().getName();
        this.trumpColorHexCode = this.activeFight.getStoneInFight().getRandomTrumpColor().getHexCode();
        this.setLayouts();
        this.setImageViews();
        this.setLabelTrumpColor();
        this.setBorderColor();
        this.setCardChooseLabel();
    }

    public void endFightScene() {
        this.anchorPaneFightOverlay.setVisible(false);
        this.anchorPaneFightOverlay.getChildren().remove(this.trumpColorLabel);
        this.anchorPaneFightOverlay.getChildren().remove(this.exitLbl);
        this.anchorPaneFightOverlay.getChildren().remove(this.cardChooseTextLabel);
    }

    private void setLayouts() {
        this.anchorPaneFightOverlay.setLayoutX((double) (GameConfig.WINDOW_WIDTH - GameConfig.WINDOW_WIDTH_FIGHT_SCENE) / 2);
        this.anchorPaneFightOverlay.setLayoutY(GameConfig.LAYOUT_Y_FIGHT_SCENE);
    }

    private void setBorderColor() {
        this.anchorPaneFightOverlay.setStyle("-fx-border-color: " + this.trumpColorHexCode + "; -fx-border-width: 3px;");
    }

    private void setImageViews() {
        double spaceBetweenCards = 50.0;
        double xPosition = this.calcSpaceLeftRight(spaceBetweenCards, 2, GameConfig.WINDOW_WIDTH_FIGHT_SCENE);
        this.imgViewCardStone = (ImageView) this.anchorPaneFightOverlay.getChildren().get(0);
        this.imgViewCardDucky = (ImageView) this.anchorPaneFightOverlay.getChildren().get(1);
        this.imgViewCardStone.setFitHeight(GameConfig.CARD_HEIGHT);
        this.imgViewCardDucky.setFitHeight(GameConfig.CARD_HEIGHT);
        this.imgViewCardStone.setFitWidth(GameConfig.CARD_WIDTH);
        this.imgViewCardDucky.setFitWidth(GameConfig.CARD_WIDTH);
        this.imgViewCardStone.setLayoutX(xPosition);
        xPosition = xPosition + GameConfig.CARD_WIDTH + spaceBetweenCards;
        this.imgViewCardDucky.setLayoutX(xPosition);
        this.renderCardImageViews(this.imgViewCardStone, true);
        this.renderCardImageViews(this.imgViewCardDucky, false);
    }

    private void renderCardImageViews(ImageView imgView, boolean stone) {
        if (stone) {
            newImage(this.activeFight.getDuckyPlaysFirst() ? this.activeFight.getStoneCard().getImgFileName() : GameConfig.BACK_CARD_FILENAME, imgView);
        } else {
            newImage(GameConfig.EMPTY_CARD_FILENAME, imgView);
        }
    }

    private void setLabelTrumpColor() {
        TextObject textObject = new TextObject();
        textObject.getTextLabel().setId("trumpColorText");
        this.trumpColorLabel = textObject.getTextLabel();
        if (!this.trumpColorName.equals("none")) {
            textObject.getTextLabel().setText("Trump color is " + this.trumpColorName);
            textObject.setStyle(FONT_SIZE_LABEL_TRUMP_COLOR, this.trumpColorHexCode);
        } else {
            textObject.getTextLabel().setText("No trump color");
            textObject.setStyleDefaultWhite(FONT_SIZE_LABEL_TRUMP_COLOR);
        }
        textObject.centerLabelFightScene(20);
        this.anchorPaneFightOverlay.getChildren().add(textObject.getTextLabel());
    }

    private void setCardChooseLabel() {
        TextObject textObject = new TextObject();
        textObject.getTextLabel().setId("cardChooseText");
        this.cardChooseTextLabel = textObject.getTextLabel();
        if (!activeFight.getDuckyPlaysFirst()) {
            textObject.getTextLabel().setText("Choose a card to start");
        } else {
            textObject.getTextLabel().setText("Try to beat this card!");
        }
        textObject.setStyleDefaultWhite("25px");
        textObject.centerLabelFightScene(50);
        this.anchorPaneFightOverlay.getChildren().add(textObject.getTextLabel());
    }

    private void addWinLossLabel() {
        TextObject textObject = new TextObject();
        textObject.getTextLabel().setId("winLossLabel");
    }

    public void addExitLabel() {
        this.exitLbl = new Label();
        this.exitLbl.setText("x");
        this.exitLbl.setLayoutX(368.0);
        this.exitLbl.setLayoutY(5);
        this.exitLbl.setId("exitLbl");
        this.anchorPaneFightOverlay.getChildren().add(this.exitLbl);
    }

    public void newImage(String fileName, ImageView imgView) {
        Image newImage =  new Image(Objects.requireNonNull(getClass().getResourceAsStream(fileName)));
        imgView.setImage(newImage);
    }

    public double calcSpaceLeftRight(double spaceBetweenCards, int amountCards, double nodeWidth) {
        double handCardsWithoutSpace = GameConfig.CARD_WIDTH * amountCards;
        double handCardsWithSpace = handCardsWithoutSpace + ((amountCards - 1) * spaceBetweenCards);
        return (nodeWidth - handCardsWithSpace) / 2;
    }

    public AnchorPane getAnchorPaneFightOverlay() {
        return anchorPaneFightOverlay;
    }

    public ImageView getImgViewCardStone() {
        return imgViewCardStone;
    }

    public ImageView getImgViewCardDucky() {
        return imgViewCardDucky;
    }

    public Label getExitLbl() {
        return exitLbl;
    }

    public void setTrumpColorObject(GameColor trumpColorObject) {
        this.trumpColorObject = trumpColorObject;
    }
}
