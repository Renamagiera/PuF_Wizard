package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class FightSceneModel {

    public SimpleStringProperty trumpColorTextProperty;
    public SimpleStringProperty trumpColorTextStyleProperty;
    public SimpleStringProperty cardChooseTextProperty;
    public ImageView stoneCardProperty;
    public ImageView duckyCardProperty;
    public SimpleStringProperty winLossLabelProperty;
    public SimpleStringProperty winLossLabelStyleProperty;
    public SimpleStringProperty fightOverlayStyleProperty;

    private static final Image BACK_CARD_IMAGE = new Image(Objects.requireNonNull(FightSceneModel.class.getResourceAsStream(GameConfig.BACK_CARD_FILENAME)));
    private static final Image EMPTY_CARD_IMAGE = new Image(Objects.requireNonNull(FightSceneModel.class.getResourceAsStream(GameConfig.EMPTY_CARD_FILENAME)));

    private AnchorPane anchorPaneFightOverlay;
    private Fight activeFight;
    private GameColorObject gameColorObject;
    private String trumpColorHexCode;
    private String trumpColorName;

    private Label exitLabel;

    public FightSceneModel() {
        this.trumpColorTextProperty = new SimpleStringProperty("");
        this.trumpColorTextStyleProperty = new SimpleStringProperty("");
        this.cardChooseTextProperty = new SimpleStringProperty("");
        this.stoneCardProperty = new ImageView(EMPTY_CARD_IMAGE);
        this.duckyCardProperty = new ImageView(EMPTY_CARD_IMAGE);
        this.winLossLabelProperty = new SimpleStringProperty("");
        this.winLossLabelStyleProperty = new SimpleStringProperty("");
        this.fightOverlayStyleProperty = new SimpleStringProperty("");
    }

    // getter & setter
    public AnchorPane getAnchorPaneFightOverlay() {
        return anchorPaneFightOverlay;
    }
    public Label getExitLabel() {
        return exitLabel;
    }

    public void initLocalVariables(GameColorObject gameColorObject, AnchorPane anchorPaneFight) {
        this.gameColorObject = gameColorObject;
        this.anchorPaneFightOverlay = anchorPaneFight;
    }

    public void showFightScene(Fight activeFight) {
        this.anchorPaneFightOverlay.setVisible(true);
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

    public void updateBorderColor(String trumpColorHexCode) {
        this.fightOverlayStyleProperty.set("-fx-border-color: " + trumpColorHexCode + "; -fx-border-width: 3px;");
    }

    private void updateCardChooseLabel() {
        if (activeFight.getDuckyPlaysFirst()) {
            this.cardChooseTextProperty.set("Choose a card to start");
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
            this.duckyCardProperty.imageProperty().set(newImage(this.activeFight.getDuckyCard().getImgFileName()));
        } else {
            this.stoneCardProperty.imageProperty().set(newImage(this.activeFight.getStoneCard().getImgFileName()));
        }
    }

    // help-methods
    private Image newImage(String fileName) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(fileName)));
    }





    // CONTROLLER
    public void endFightScene() {
        this.anchorPaneFightOverlay.setVisible(false);
        this.anchorPaneFightOverlay.getChildren().remove(this.exitLabel);
    }

    // VIEW !! Added exit-label manually, because it should just appear after card-click
    // TODO add label in fxml and set it visible/not visible, delete event-handler. After this, it should stay in model because of data change
    public void addExitLabel() {
        this.exitLabel = new Label("x");
        this.exitLabel.setId("exitLabel");
        this.exitLabel.setLayoutX(358);
        this.exitLabel.setLayoutY(5);
        this.exitLabel.setStyle("-fx-font-family: 'Alagard'; -fx-text-fill: white; -fx-font-size: 30px;");
        this.anchorPaneFightOverlay.getChildren().add(this.exitLabel);
    }
    // VIEW !! Added exit-label manually, because it should just appear after card-click
}
