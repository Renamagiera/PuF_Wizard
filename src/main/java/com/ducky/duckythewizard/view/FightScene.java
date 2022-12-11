package com.ducky.duckythewizard.view;

import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class FightScene {

    public int ID;
    private AnchorPane parentAnchorPane;
    private AnchorPane newAnchorPane;
    private int fightSceneMidX;
    private int fightSceneMidY;
    private ImageView imgViewCard1;
    private ImageView imgViewCard2;
    private Button exitButton;
    public FightScene(AnchorPane parentAnchorPane) {
        this.parentAnchorPane = parentAnchorPane;
        this.newAnchorPane = new AnchorPane();
        this.fightSceneMidX = GameConfig.WINDOW_WIDTH_FIGHT_SCENE / 2;
        this.fightSceneMidY = GameConfig.WINDOW_HEIGHT_FIGHT_SCENE / 2;
        Random rand = new Random();
        this.ID = rand.nextInt(1000);
    }
    public void renderFightScene() {
        System.out.println("--> renderFightScene, ID: " + ID);
        this.newAnchorPane.setId("fightScene");
        this.newAnchorPane.setLayoutX(midFromParent());
        this.newAnchorPane.setLayoutY(GameConfig.LAYOUT_Y_FIGHT_SCENE);
        this.newAnchorPane.setPrefWidth(GameConfig.WINDOW_WIDTH_FIGHT_SCENE);
        this.newAnchorPane.setPrefHeight(GameConfig.WINDOW_HEIGHT_FIGHT_SCENE);
        URL url = this.getClass().getResource("/com/ducky/duckythewizard/styles/styleMainGameView.css");
        String css = Objects.requireNonNull(url).toExternalForm();
        this.newAnchorPane.getStylesheets().add(css);
        this.imgViewCard1 = new ImageView();
        this.imgViewCard2 = new ImageView();
        this.newAnchorPane.getChildren().add(renderCardImageViews(100, 110, this.imgViewCard1));
        this.newAnchorPane.getChildren().add(renderCardImageViews(230, 110, this.imgViewCard2));
        this.imgViewCard2.setPickOnBounds(true);
        this.imgViewCard2.setPreserveRatio(true);
        this.parentAnchorPane.getChildren().add(this.newAnchorPane);
    }

    // TODO das sind eigentlich Methoden aus der CardDeck-Klasse, evtl. auslagern in eine eigene CardImgView-Klasse !!!
    private ImageView renderCardImageViews(int layoutX, int layoutY, ImageView imgView) {
        Image emptyImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(GameConfig.EMPTY_CARD_FILENAME)));
        imgView.setLayoutX(layoutX);
        imgView.setLayoutY(layoutY);
        imgView.setImage(emptyImage);
        return imgView;
    }

    public void renderCardImgPlayer(String cardFileName) {
        Image cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(cardFileName)));
        this.imgViewCard2.setImage(cardImage);
        this.newAnchorPane.getChildren().add(this.imgViewCard2);
    }
    /***********************************************************************************************************************/

    private int midFromParent() {
        return (GameConfig.WINDOW_WIDTH - GameConfig.WINDOW_WIDTH_FIGHT_SCENE) / 2;
    }

    public void addButton(String buttonText, int positionX, int positionY) {
        this.exitButton = new Button();
        this.exitButton.setLayoutX(positionX);
        this.exitButton.setLayoutY(positionY);
        this.exitButton.setText(buttonText);
        this.newAnchorPane.getChildren().add(this.exitButton);
    }

    public void endFightScene() {
        System.out.println("--> endFightScene, ID: " + ID);
        this.parentAnchorPane.getChildren().remove(this.newAnchorPane);
    }

    public AnchorPane getNewAnchorPane() {
        return newAnchorPane;
    }

    public ImageView getImgViewCard1() {
        return imgViewCard1;
    }

    public ImageView getImgViewCard2() {
        return imgViewCard2;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
