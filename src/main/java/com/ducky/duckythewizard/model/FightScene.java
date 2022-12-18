package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.controller.CardController;
import com.ducky.duckythewizard.controller.GameController;
import com.ducky.duckythewizard.model.color.GameColor;
import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Objects;

public class FightScene {

    //public int ID;
    private AnchorPane parentAnchorPane;
    private AnchorPane newAnchorPane;
    private CardController cardCtrl;
    private Fight activeFight;
    private int fightSceneMidX;
    private int fightSceneMidY;
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
    public FightScene(GameColorObject gameColorObject, CardController cardCtrl) {
        // new AnchorPane, set visibility false
        this.newAnchorPane = new AnchorPane();
        this.newAnchorPane.setVisible(false);
        this.gameColorObject = gameColorObject;
        this.cardCtrl = cardCtrl;
        this.fightSceneMidX = GameConfig.WINDOW_WIDTH_FIGHT_SCENE / 2;
        this.fightSceneMidY = GameConfig.WINDOW_HEIGHT_FIGHT_SCENE / 2;
    }
    public void renderFightScene(AnchorPane anchorPane, GameColor trumpColorObject, Fight fight) {
        this.parentAnchorPane = anchorPane;
        this.activeFight = fight;
        this.trumpColorObject = trumpColorObject;
        this.trumpColorHexCode = trumpColorObject.getHexCode();
        this.trumpColorName = trumpColorObject.getName();
        this.trumpColorLabel = labelTrumpColor();
        this.cardChooseTextLabel = addCardChooseLabel();
        this.newAnchorPane.getChildren().add(this.trumpColorLabel);
        this.newAnchorPane.getChildren().add(this.cardChooseTextLabel);
        this.setBorderColor();
        if (!(this.parentAnchorPane.getChildren().get(this.parentAnchorPane.getChildren().size() - 1).equals(this.newAnchorPane))) {
            renderNewScene();
        } else {
            startFightScene();
        }
    }

    private void renderNewScene() {
        this.newAnchorPane.setVisible(true);
        this.newAnchorPane.setId("fightScene");
        this.setLayouts();
        this.setImageViews();
        this.parentAnchorPane.getChildren().add(this.newAnchorPane);
    }

    private void startFightScene() {
        this.newAnchorPane.setVisible(true);
    }
    public void endFightScene() {
        this.newAnchorPane.getChildren().remove(this.cardChooseTextLabel);
        this.newAnchorPane.getChildren().remove(this.trumpColorLabel);
        this.newAnchorPane.getChildren().remove(this.exitLbl);
        this.newAnchorPane.setVisible(false);
    }

    // TODO das sind eigentlich Methoden aus der CardDeck-Klasse, evtl. auslagern in eine eigene CardImgView-Klasse ???
    private ImageView renderCardImageViews(int layoutX, ImageView imgView, boolean stone) {
        //Image emptyImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(GameConfig.EMPTY_CARD_FILENAME)));
        if (stone) {
            Image emptyImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(this.activeFight.getDuckyPlaysFirst() ? GameConfig.EMPTY_CARD_FILENAME : GameConfig.BACK_CARD_FILENAME)));
            imgView.setLayoutX(layoutX);
            imgView.setLayoutY(110);
            imgView.setImage(emptyImage);
            return imgView;
        } else {
            Image emptyImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(GameConfig.EMPTY_CARD_FILENAME)));
            imgView.setLayoutX(layoutX);
            imgView.setLayoutY(110);
            imgView.setImage(emptyImage);
            return imgView;
        }
    }
    /***********************************************************************************************************************/

    private int midFromParent() {
        return (GameConfig.WINDOW_WIDTH - GameConfig.WINDOW_WIDTH_FIGHT_SCENE) / 2;
    }

    private void setLayouts() {
        this.newAnchorPane.setLayoutX(midFromParent());
        this.newAnchorPane.setLayoutY(GameConfig.LAYOUT_Y_FIGHT_SCENE);
        this.newAnchorPane.setPrefWidth(GameConfig.WINDOW_WIDTH_FIGHT_SCENE);
        this.newAnchorPane.setPrefHeight(GameConfig.WINDOW_HEIGHT_FIGHT_SCENE);
        URL url = this.getClass().getResource("/com/ducky/duckythewizard/styles/styleMainGameView.css");
        String css = Objects.requireNonNull(url).toExternalForm();
        this.newAnchorPane.getStylesheets().add(css);
    }

    private void setImageViews() {
        this.imgViewCardStone = new ImageView();
        this.imgViewCardDucky = new ImageView();
        this.newAnchorPane.getChildren().add(renderCardImageViews(100, this.imgViewCardStone, true));
        this.newAnchorPane.getChildren().add(renderCardImageViews(230, this.imgViewCardDucky, false));
        this.imgViewCardDucky.setPickOnBounds(true);
        this.imgViewCardDucky.setPreserveRatio(true);
    }

    private Label addCardChooseLabel() {
        TextObject textObject = new TextObject();
        if (activeFight.getDuckyPlaysFirst()) {
            textObject.getTextLabel().setText("Choose a card to start");
        } else {
            textObject.getTextLabel().setText("Try to beat this card!");
        }
        textObject.setStyleDefaultWhite("32px");
        textObject.centerLabelFightScene(50);
        return textObject.getTextLabel();
    }

    private void setBorderColor() {
        this.newAnchorPane.setStyle("-fx-border-color: " + this.trumpColorHexCode + "; -fx-border-width: 3px;");
    }

    private Label labelTrumpColor() {
        TextObject textObject = new TextObject();
        if (!trumpColorName.equals("none")) {
            textObject.getTextLabel().setText("Trump color is " + this.trumpColorName);
            textObject.setStyle(FONT_SIZE_LABEL_TRUMP_COLOR, this.trumpColorHexCode);
        } else {
            textObject.getTextLabel().setText("No trump color");
            textObject.setStyleDefaultWhite(FONT_SIZE_LABEL_TRUMP_COLOR);
        }
        textObject.centerLabelFightScene(20);
        return textObject.getTextLabel();
    }

    public void addExitLabel() {
        this.exitLbl = new Label();
        this.exitLbl.setText("x");
        this.exitLbl.setLayoutX(368.0);
        this.exitLbl.setLayoutY(5);
        this.exitLbl.setId("exitLbl");
        this.newAnchorPane.getChildren().add(this.exitLbl);
    }

    public AnchorPane getNewAnchorPane() {
        return newAnchorPane;
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
