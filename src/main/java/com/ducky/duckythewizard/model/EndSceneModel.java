package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.controller.ScoresController;
import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class EndSceneModel {
    private AnchorPane anchorPaneEndScene;
    private AnchorPane anchorPaneEndSceneMenu;
    private Label menuLabel;
    private Label restartLabel;
    private Label exitLabel;
    private Label maxLabel;

    private EventHandler<MouseEvent> exitEvent;

    public SimpleStringProperty endSceneLabelProperty;
    public SimpleStringProperty endSceneLabelStyleProperty;
    public SimpleStringProperty endSceneStyleProperty;
    public SimpleStringProperty scoreProperty;
    public SimpleStringProperty exitLabelEndViewProperty;
    private GameColorObject gameColorObject;
    private int score;
    boolean newHighScore;

    public EndSceneModel() {
        this.endSceneLabelProperty = new SimpleStringProperty();
        this.endSceneLabelStyleProperty = new SimpleStringProperty();
        this.endSceneStyleProperty = new SimpleStringProperty();
        this.scoreProperty = new SimpleStringProperty();
        this.exitLabelEndViewProperty = new SimpleStringProperty();
    }

    // getter & setter
    public EventHandler<MouseEvent> getExitEvent() {
        return exitEvent;
    }
    public Label getExitLabel() {
        return exitLabel;
    }
    public void setExitEvent(EventHandler<MouseEvent> exitEvent) {
        this.exitEvent = exitEvent;
    }

    public void initLocalVariables(AnchorPane anchorPaneRoot, AnchorPane anchorPaneEndScene, GameColorObject gameColorObject) {
        this.anchorPaneEndScene = anchorPaneEndScene;
        this.gameColorObject = gameColorObject;
        this.anchorPaneEndSceneMenu = (AnchorPane) anchorPaneRoot.lookup("#endSceneMenu");
        this.menuLabel = (Label) anchorPaneRoot.lookup("#backToMenu");
        this.restartLabel = (Label) anchorPaneRoot.lookup("#restart");
        this.maxLabel = (Label) anchorPaneRoot.lookup("#maximizeLabelEndView");
        this.exitLabel = (Label) anchorPaneRoot.lookup("#exitLabelEndView");
    }

    public void showEndScene(boolean playerWin, int score) {
        this.score = score;
        this.updateStylesEndScene(playerWin);
        this.makeVisible();
    }

    private void updateStylesEndScene(boolean playerWin) {
        this.scoreProperty.set("Your score: " + this.score);
        this.updateExitLabel(false);
        this.exitLabelEndViewProperty.set("-");
        if (playerWin) {
            this.setStyles("WIN", "green");
        } else {
            this.setStyles("LOSS", "red");
            this.anchorPaneEndSceneMenu.setPrefHeight(540);

        }
        this.setLabelLayouts(50);
        this.showScoreTable(this.score);
    }

    private void showScoreTable(int score) {
        // TODO view methods should not be here
        // creating the scores table
        HBox hbox = new HBox();
        ScoresController scoresTable = new ScoresController();
        this.newHighScore = scoresTable.setGameScore(score);
        this.updateLayoutYNewHighScore(this.newHighScore);
        hbox.getChildren().add(scoresTable);
        hbox.setLayoutX(52);
        hbox.setLayoutY(120);
        this.anchorPaneEndSceneMenu.getChildren().add(hbox);
    }

    public void createPauseMenu() {
        this.setPauseStyles();
        this.makeVisible();
    }

    public void endPause() {
        this.anchorPaneEndScene.setVisible(false);
        this.exitLabel.setVisible(false);
    }

    public void setPauseStyles() {
        this.updateExitLabel(true);
        this.setStyles("Pause", GameConfig.COLOR_WHITE_STRING);
        this.anchorPaneEndSceneMenu.setPrefHeight(200);
        this.setLabelLayouts(50);
    }

    public void updateExitLabel(boolean pause) {
        if (pause) {
            this.exitLabelEndViewProperty.set("x");
        } else {
            this.exitLabelEndViewProperty.set("-");
        }
    }

    // help-methods
    private void makeVisible() {
        this.anchorPaneEndScene.setVisible(true);
        this.anchorPaneEndScene.requestFocus();
        this.exitLabel.setVisible(true);
    }

    private void updateLayoutYNewHighScore(boolean newHighScore) {
        if (newHighScore) {
            this.anchorPaneEndSceneMenu.setPrefHeight(560);
            setLabelLayouts(40);
        }
    }

    public void setLabelLayouts(int spaceY) {
        int spaceX = 120;
        this.menuLabel.setLayoutX(spaceX);
        this.restartLabel.setLayoutX(this.anchorPaneEndSceneMenu.getPrefWidth() - spaceX - this.restartLabel.getPrefWidth());
        this.menuLabel.setLayoutY(this.anchorPaneEndSceneMenu.getPrefHeight() - this.menuLabel.getPrefHeight() - spaceY);
        this.restartLabel.setLayoutY(this.anchorPaneEndSceneMenu.getPrefHeight() - this.menuLabel.getPrefHeight() - spaceY);
        this.anchorPaneEndSceneMenu.setLayoutX(calcMid(GameConfig.WINDOW_WIDTH, this.anchorPaneEndSceneMenu.getWidth()));
        this.anchorPaneEndSceneMenu.setLayoutY(calcMid(GameConfig.WINDOW_HEIGHT, this.anchorPaneEndSceneMenu.getPrefHeight()));
    }

    public void setMaxLabelLayouts(Label maxLabel) {
        maxLabel.setLayoutY(120);
        maxLabel.setLayoutX(calcMid(GameConfig.WINDOW_WIDTH, this.maxLabel.getPrefWidth()));
    }

    private void setStyles(String headline, String color) {
        this.endSceneLabelProperty.set(headline);
        this.endSceneLabelStyleProperty.set("-fx-font-size: 50px; -fx-text-fill: " + gameColorObject.getHexCodeFromMap(color) + ";");
        this.endSceneStyleProperty.set("-fx-border-color: " + gameColorObject.getHexCodeFromMap(color) + "; -fx-border-width: 3px;");
    }

    public void minimizeEndSceneMenu() {
        // add labels to scene-anchor-pane, remove from menu
        this.menuLabel.setLayoutY(60);
        this.menuLabel.setLayoutX(250);
        this.restartLabel.setLayoutY(60);
        this.restartLabel.setLayoutX(435);
        this.maxLabel.setLayoutY(120);
        this.maxLabel.setLayoutX(calcMid(GameConfig.WINDOW_WIDTH, this.maxLabel.getPrefWidth()));
        this.anchorPaneEndSceneMenu.setVisible(false);
    }

    public void maximizeEndSceneMenu() {
        // add labels back to menu-anchor-pane, remove from scene
        this.anchorPaneEndSceneMenu.setVisible(true);
    }

    private double calcMid(int size, double nodeWidth) {
        return (size - nodeWidth) / 2;
    }

}
