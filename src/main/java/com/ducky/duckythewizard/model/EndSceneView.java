package com.ducky.duckythewizard.model;

import com.ducky.duckythewizard.controller.ScoresController;
import com.ducky.duckythewizard.model.color.GameColorObject;
import com.ducky.duckythewizard.model.config.GameConfig;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class EndSceneView {
    private AnchorPane anchorPaneEndScene;
    private AnchorPane anchorPaneEndSceneMenu;
    private Label menuLabel;
    private Label restartLabel;
    private Label exitLabel;
    private Label maxLabel;

    private EventHandler<MouseEvent> exitEvent;
    private EventHandler<MouseEvent> maxEvent;

    public SimpleStringProperty endSceneLabelProperty;
    public SimpleStringProperty endSceneLabelStyleProperty;
    public SimpleStringProperty endSceneStyleProperty;
    public SimpleStringProperty scoreProperty;
    private GameColorObject gameColorObject;
    private int score;
    boolean newHighScore;

    public EndSceneView() {
        this.endSceneLabelProperty = new SimpleStringProperty();
        this.endSceneLabelStyleProperty = new SimpleStringProperty();
        this.endSceneStyleProperty = new SimpleStringProperty();
        this.scoreProperty = new SimpleStringProperty();
    }

    public EventHandler<MouseEvent> getExitEvent() {
        return exitEvent;
    }
    public Label getExitLabel() {
        return exitLabel;
    }
    public void setExitEvent(EventHandler<MouseEvent> exitEvent) {
        this.exitEvent = exitEvent;
    }

    public void initLocalVariables(AnchorPane anchorPaneEndScene, GameColorObject gameColorObject) {
        this.anchorPaneEndScene = anchorPaneEndScene;
        this.gameColorObject = gameColorObject;
        this.anchorPaneEndSceneMenu = (AnchorPane) this.anchorPaneEndScene.lookup("#endSceneMenu");
        this.menuLabel = (Label) this.anchorPaneEndScene.lookup("#backToMenu");
        this.restartLabel = (Label) this.anchorPaneEndScene.lookup("#restart");
    }

    private void makeVisible() {
        this.anchorPaneEndScene.setVisible(true);
        this.anchorPaneEndScene.requestFocus();
    }

    public void renderEndScene(boolean playerWin, int score) {
        this.score = score;
        this.setStylesEnd(playerWin);
        this.addExitEventHandler();
        this.makeVisible();
    }

    private void setStylesEnd(boolean playerWin) {
        this.scoreProperty.set("Your score: " + this.score);
        this.addExitLabel();
        this.exitLabel.setText("-");
        if (playerWin) {
            this.setStyles("WIN", "green");
        } else {
            this.setStyles("LOSS", "red");
            this.anchorPaneEndSceneMenu.setPrefHeight(540);

        }
        this.setLabelLayouts(50);
        this.renderScoreTable(this.score);
    }

    private void renderScoreTable(int score) {
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

    public void minimizeEndSceneMenu() {
        this.anchorPaneEndScene.getChildren().add(this.menuLabel);
        this.anchorPaneEndScene.getChildren().add(this.restartLabel);
        this.menuLabel.setLayoutY(60);
        this.menuLabel.setLayoutX(250);
        this.restartLabel.setLayoutY(60);
        this.restartLabel.setLayoutX(435);

        this.maxLabel = new Label("score");
        this.maxLabel.setPrefWidth(115);
        this.maxLabel.setPrefHeight(30);
        this.maxLabel.setLayoutY(120);
        this.maxLabel.setLayoutX(calcMid(GameConfig.WINDOW_WIDTH, this.maxLabel.getPrefWidth()));
        this.maxLabel.getStyleClass().add("textGeneral");
        this.maxLabel.getStyleClass().add("bgLightBrown");
        this.maxLabel.setAlignment(Pos.CENTER);
        this.addMaxEventHandler();
        this.anchorPaneEndScene.getChildren().add(this.maxLabel);

        this.anchorPaneEndSceneMenu.setVisible(false);
    }

    public void createPauseMenu() {
        this.setPauseStyles();
        this.makeVisible();
    }

    public void setPauseStyles() {
        this.addExitLabel();
        this.setStyles("Pause", GameConfig.COLOR_WHITE_STRING);
        this.anchorPaneEndSceneMenu.setPrefHeight(200);
        this.setLabelLayouts(50);
    }

    public void endPause() {
        this.anchorPaneEndScene.setVisible(false);
    }

    public void addExitLabel() {
        this.exitLabel = new Label("x");
        this.exitLabel.setId("exitLabelEndScene");
        this.exitLabel.setLayoutX(508);
        this.exitLabel.setLayoutY(10);
        this.exitLabel.getStyleClass().add("textGeneral");
        this.exitLabel.getStyleClass().add("textSizeNormal");
        this.anchorPaneEndSceneMenu.getChildren().add(this.exitLabel);
    }

    public void addExitEventHandler() {
        this.exitEvent = mouseEvent -> {
            if (this.exitEvent != null) {
                this.exitLabel.removeEventHandler(MouseEvent.MOUSE_CLICKED, this.exitEvent);
            }
            this.minimizeEndSceneMenu();
        };
        this.exitLabel.addEventFilter(MouseEvent.MOUSE_CLICKED, this.exitEvent);
    }

    private void addMaxEventHandler() {
        this.maxEvent = mouseEvent -> {
            if (this.maxEvent != null) {
                this.maxLabel.removeEventHandler(MouseEvent.MOUSE_CLICKED, this.maxEvent);
            }
            this.anchorPaneEndSceneMenu.setVisible(true);
            this.anchorPaneEndSceneMenu.getChildren().add(this.menuLabel);
            this.anchorPaneEndSceneMenu.getChildren().add(this.restartLabel);
            this.setLabelLayouts(40);
            this.anchorPaneEndScene.getChildren().remove(this.menuLabel);
            this.anchorPaneEndScene.getChildren().remove(this.restartLabel);
            this.anchorPaneEndScene.getChildren().remove(this.maxLabel);
        };
        this.maxLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, this.maxEvent);
    }

    private void setStyles(String headline, String color) {
        this.endSceneLabelProperty.set(headline);
        this.endSceneLabelStyleProperty.set("-fx-font-size: 50px; -fx-text-fill: " + gameColorObject.getHexCodeFromMap(color) + ";");
        this.endSceneStyleProperty.set("-fx-border-color: " + gameColorObject.getHexCodeFromMap(color) + "; -fx-border-width: 3px;");
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

    private double calcMid(int size, double nodeWidth) {
        return (size - nodeWidth) / 2;
    }

    private void updateLayoutYNewHighScore(boolean newHighScore) {
        if (newHighScore) {
            this.anchorPaneEndSceneMenu.setPrefHeight(560);
            setLabelLayouts(40);
        }
    }

}
