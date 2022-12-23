package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class FightView {

    public SimpleStringProperty trumpColorTextProperty;
    public SimpleStringProperty trumpColorTextStyleProperty;
    public SimpleStringProperty cardChooseTextProperty;
    public ImageView stoneCardProperty;
    public ImageView duckyCardProperty;
    public SimpleStringProperty exitLabelTextProperty;
    public SimpleStringProperty winLossLabelProperty;
    public SimpleStringProperty winLossLabelStyleProperty;
    public SimpleStringProperty fightOverlayStyleProperty;

    private static final Image BACK_CARD_IMAGE = new Image(Objects.requireNonNull(FightView.class.getResourceAsStream(GameConfig.BACK_CARD_FILENAME)));
    private static final Image EMPTY_CARD_IMAGE = new Image(Objects.requireNonNull(FightView.class.getResourceAsStream(GameConfig.EMPTY_CARD_FILENAME)));

    private AnchorPane anchorPaneFightOverlay;
    private Fight activeFight;
    private GameColorObject gameColorObject;
    private String trumpColorHexCode;
    private String trumpColorName;

    public FightView() {
        this.trumpColorTextProperty = new SimpleStringProperty("");
        this.trumpColorTextStyleProperty = new SimpleStringProperty("");
        this.cardChooseTextProperty = new SimpleStringProperty("");
        this.stoneCardProperty = new ImageView(EMPTY_CARD_IMAGE);
        this.duckyCardProperty = new ImageView(EMPTY_CARD_IMAGE);
        this.exitLabelTextProperty = new SimpleStringProperty("");
        this.winLossLabelProperty = new SimpleStringProperty("");
        this.winLossLabelStyleProperty = new SimpleStringProperty("");
        this.fightOverlayStyleProperty = new SimpleStringProperty("");
    }

    public void renderFightScene(Fight activeFight, GameColorObject gameColorObject) {
        this.gameColorObject = gameColorObject;
        this.anchorPaneFightOverlay.setVisible(true);
        this.activeFight = activeFight;
        this.trumpColorName = activeFight.getStoneInFight().getTrumpColorStone().getName();
        this.trumpColorHexCode = activeFight.getStoneInFight().getTrumpColorStone().getHexCode();
        this.updateImageViews();
        this.updateLabelTrumpColor();
        this.updateBorderColor(this.trumpColorHexCode);
        this.updateCardChooseLabel();
        this.clearWinLossLabel();
    }

    public void endFightScene() {
        this.anchorPaneFightOverlay.setVisible(false);
    }

    private void updateImageViews() {
        this.duckyCardProperty.imageProperty().set(EMPTY_CARD_IMAGE);
        this.stoneCardProperty.imageProperty().set(this.activeFight.getDuckyPlaysFirst() ? BACK_CARD_IMAGE : newImage(this.activeFight.getStoneCard().getImgFileName()));
    }

    private void updateLabelTrumpColor() {
        if (!this.trumpColorName.equals("none")) {
            this.trumpColorTextProperty.set("Trump color is " + this.trumpColorName);
        } else {
            this.trumpColorTextProperty.set("No trump color");
        }
        this.trumpColorTextStyleProperty.set("-fx-text-fill: " + this.trumpColorHexCode + ";");
    }

    private void updateCardChooseLabel() {
        if (activeFight.getDuckyPlaysFirst()) {
            this.cardChooseTextProperty.set("Choose a card to start");
        } else {
            this.cardChooseTextProperty.set("Try to beat this card!");
        }
    }

    private void clearWinLossLabel() {
        this.winLossLabelProperty.set("");
    }

    private Image newImage(String fileName) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(fileName)));
    }

    public void updateBorderColor(String trumpColorHexCode) {
        this.fightOverlayStyleProperty.set("-fx-border-color: " + trumpColorHexCode + "; -fx-border-width: 3px;");
    }

    public void updateWinLossLabel(boolean duckyWin) {
        if (duckyWin) {
            this.winLossLabelProperty.set("Win!");
            this.winLossLabelStyleProperty.set("-fx-text-fill: " + this.gameColorObject.getHexCodeFromMap("green") + ";");
        } else {
            this.winLossLabelProperty.set("Loss!");
            this.winLossLabelStyleProperty.set("-fx-text-fill: " + this.gameColorObject.getHexCodeFromMap("red") + ";");
        }
    }

    public void renderFightViewCard(boolean ducky) {
        if (ducky) {
            this.duckyCardProperty.imageProperty().set(newImage(this.activeFight.getDuckyCard().getImgFileName()));
        } else {
            this.stoneCardProperty.imageProperty().set(newImage(this.activeFight.getStoneCard().getImgFileName()));
        }
    }

    public void createExitLabel() {
        this.exitLabelTextProperty.set("x");
    }

    public AnchorPane getAnchorPaneFightOverlay() {
        return anchorPaneFightOverlay;
    }

    public void setAnchorPaneFightOverlay(AnchorPane overlay) {
        this.anchorPaneFightOverlay = overlay;
    }
}
