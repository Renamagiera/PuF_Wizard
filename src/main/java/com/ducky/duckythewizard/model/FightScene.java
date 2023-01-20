package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class FightScene {

    public SimpleStringProperty trumpColorTextProperty;
    public SimpleStringProperty trumpColorTextStyleProperty;
    public SimpleStringProperty cardChooseTextProperty;
    public ImageView stoneCardProperty;
    public ImageView duckyCardProperty;
    public SimpleStringProperty winLossLabelProperty;
    public SimpleStringProperty winLossLabelStyleProperty;
    public SimpleStringProperty fightOverlayStyleProperty;
    public BooleanProperty fightOverlayVisible;
    public BooleanProperty exitFightViewVisible;

    private AnchorPane anchorPaneFightOverlay;
    private Fight activeFight;
    private GameColorObject gameColorObject;
    private String trumpColorHexCode;
    private String trumpColorName;

    private static final Image CARDS_BACK_CARD_IMAGE = new Image(Objects.requireNonNull(FightSceneModel.class.getResourceAsStream(GameConfig.CARDS_BACK_CARD_FILENAME)));
    private static final Image CARDS_EMPTY_CARD_IMAGE = new Image(Objects.requireNonNull(FightSceneModel.class.getResourceAsStream(GameConfig.CARDS_EMPTY_CARD_FILENAME)));


    public FightScene() {
        this.trumpColorTextProperty = new SimpleStringProperty();
        this.trumpColorTextStyleProperty = new SimpleStringProperty();
        this.cardChooseTextProperty = new SimpleStringProperty();
        this.stoneCardProperty = new ImageView(CARDS_EMPTY_CARD_IMAGE);
        this.duckyCardProperty = new ImageView(CARDS_EMPTY_CARD_IMAGE);
        this.winLossLabelProperty = new SimpleStringProperty();
        this.winLossLabelStyleProperty = new SimpleStringProperty();
        this.fightOverlayStyleProperty = new SimpleStringProperty();
        this.fightOverlayVisible = new SimpleBooleanProperty(false);
        this.exitFightViewVisible = new SimpleBooleanProperty(false);
    }

    // getter & setter
    public AnchorPane getAnchorPaneFightOverlay() {
        return anchorPaneFightOverlay;
    }

    public void initLocalVariables(GameColorObject gameColorObject, AnchorPane anchorPaneFight) {
        this.gameColorObject = gameColorObject;
        this.anchorPaneFightOverlay = anchorPaneFight;
    }

    public void showFightScene(Fight activeFight) {
        this.fightOverlayVisible.set(true);
        this.activeFight = activeFight;
        this.trumpColorName = activeFight.getStoneInFight().getRandomTrumpColorStone().getName();
        this.trumpColorHexCode = activeFight.getStoneInFight().getRandomTrumpColorStone().getHexCode();

        // property change, changing data in some way -> model
        this.updateImageViews();
        this.updateLabelTrumpColor();
        this.updateBorderColor(this.trumpColorHexCode);
        this.updateCardChooseLabel();
        this.clear();
    }

    private void updateImageViews() {
        this.duckyCardProperty.imageProperty().set(CARDS_EMPTY_CARD_IMAGE);
        this.stoneCardProperty.imageProperty().set(this.activeFight.getDuckyPlaysFirst() ? CARDS_BACK_CARD_IMAGE : loadNewImage(this.activeFight.getStoneCard().getImgFileName()));
    }

    private void updateLabelTrumpColor() {
        if (!this.trumpColorName.equals("none")) {
            this.trumpColorTextProperty.set("Trump color is " + this.trumpColorName + ".");
        } else {
            this.trumpColorTextProperty.set("No trump color!");
        }
        this.trumpColorTextStyleProperty.set("-fx-text-fill: " + this.trumpColorHexCode + ";");
    }

    public void updateBorderColor(String trumpColorHexCode) {
        this.fightOverlayStyleProperty.set("-fx-border-color: " + trumpColorHexCode + "; -fx-border-width: 3px;");
    }

    private void updateCardChooseLabel() {
        if (activeFight.getDuckyPlaysFirst()) {
            this.cardChooseTextProperty.set("Choose a card to start.");
        } else {
            this.cardChooseTextProperty.set("Try to beat this card!");
        }
    }

    private void clear() {
        this.winLossLabelProperty.set("");
    }

    public void updateWinLossLabelProp(boolean duckyWin) {
        if (duckyWin) {
            this.winLossLabelProperty.set("Win!");
            this.winLossLabelStyleProperty.set("-fx-text-fill: " + this.gameColorObject.getHexCodeFromMap("green") + ";");
        } else {
            this.winLossLabelProperty.set("Loss!");
            this.winLossLabelStyleProperty.set("-fx-text-fill: " + this.gameColorObject.getHexCodeFromMap("red") + ";");
        }
    }

    public void updateFightViewCardProp(boolean ducky) {
        if (ducky) {
            this.duckyCardProperty.imageProperty().set(loadNewImage(this.activeFight.getDuckyCard().getImgFileName()));
        } else {
            this.stoneCardProperty.imageProperty().set(loadNewImage(this.activeFight.getStoneCard().getImgFileName()));
        }
    }

    private Image loadNewImage(String fileName) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(fileName)));
    }

    public void endFightScene() {
        this.fightOverlayVisible.set(false);
        this.exitFightViewVisible.set(false);
    }
}
